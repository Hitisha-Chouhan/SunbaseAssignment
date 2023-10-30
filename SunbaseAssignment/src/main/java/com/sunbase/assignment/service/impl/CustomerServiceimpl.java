package com.sunbase.assignment.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sunbase.assignment.dao.CustomerRepository;
import com.sunbase.assignment.model.Customer;
import com.sunbase.assignment.service.CustomerService;

@Service
public class CustomerServiceimpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	@Override
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public List<Customer> getCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public void deleteCustomer(Long uuid) {
		try {
			Optional<Customer> gotCustomer = customerRepository.findById(uuid);
			if(!gotCustomer.isPresent()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"UUID Not Found ");
			}else {
				customerRepository.deleteById(uuid);
			}
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error Not Found ");
		}
	}

	@Override
	public Customer updateCustomer(Customer customer, Long uuid) {
		try {
		Optional<Customer> gotCustomer = customerRepository.findById(uuid);
		if(!gotCustomer.isPresent()) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"UUID Not Found ");
		}else {
			customer.setFirst_name(customer.getFirst_name());
			customer.setLast_name(customer.getLast_name());
			customer.setStreet(customer.getStreet());
			customer.setAddress(customer.getAddress());
			customer.setCity(customer.getCity());
			customer.setState(customer.getState());
			customer.setEmail(customer.getEmail());
			customer.setPhone(customer.getPhone());
			return customerRepository.save(customer);
		}
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Some Internal server issue "+e);
		}
	}

}
