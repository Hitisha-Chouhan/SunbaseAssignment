package com.sunbase.assignment.service;

import java.util.List;

import com.sunbase.assignment.model.Customer;

public interface CustomerService {
	
	public Customer createCustomer(Customer customer);
	public List<Customer> getCustomers();
	public void deleteCustomer(Long uuid);
	public Customer updateCustomer(Customer customer, Long uuid);

}
