package com.sunbase.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sunbase.assignment.model.Customer;
import com.sunbase.assignment.service.CustomerService;

@RestController
@RequestMapping("/sunbase/portal/api/")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/assignment")
	public ResponseEntity<String> saveCustomer(@RequestBody Customer customer, @RequestParam("cmd") String create) {
		try {
			if(StringUtils.isEmpty(create)) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Invalid Command ");
			}
		if(customer !=null && !StringUtils.isEmpty(customer.getFirst_name()) && !StringUtils.isEmpty(customer.getLast_name()))
		{
			customerService.createCustomer(customer);
			return ResponseEntity.ok(HttpStatus.CREATED.value()+", Successfully Created");
		}else {
			return ResponseEntity.ok(HttpStatus.BAD_REQUEST.value()+", FirstName or Lastname is missing");
		}
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Some Internal server issue "+e);
		}
		
	}
	
	@GetMapping("/assignment")
	public ResponseEntity<List<Customer>> getCustomers(@RequestParam("cmd") String get_customer_list) {
		try {
			if(StringUtils.isEmpty(get_customer_list)) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Invalid Command ");
			}
			return new ResponseEntity<>(customerService.getCustomers(),HttpStatus.OK);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Some Internal server issue "+e);
		}
	}
	
	@DeleteMapping("/assignment")
	public ResponseEntity<?> deleteCustomer(@RequestParam("cmd") String delete, @RequestParam("uuid") Long uuid) {
		try {
			if((uuid !=null && uuid <1) || StringUtils.isEmpty(delete)) {
				return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR.value()+", Invalid Command");
			}
			customerService.deleteCustomer(uuid);
			return ResponseEntity.ok(HttpStatus.OK.value()+", Successfully Deleted");
			
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error Not Deleted "+e);
		}
	}
	
	@PutMapping("/assignment")
	public ResponseEntity<String> updateCustomer(@RequestBody Customer customer, @RequestParam("cmd") String update, @RequestParam("uuid") Long uuid) {
		try {
			if((uuid !=null && uuid <1) || StringUtils.isEmpty(update)) {
				return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR.value()+", Invalid Command");
			}
			else if(customer == null) {
			return ResponseEntity.ok(HttpStatus.BAD_REQUEST.value()+", Body is Empty");
		}else if(customer !=null && !StringUtils.isEmpty(customer.getFirst_name()) && !StringUtils.isEmpty(customer.getLast_name()))
		{
			customerService.updateCustomer(customer,uuid);
			return ResponseEntity.ok(HttpStatus.OK.value()+", Successfully Updated");
		}else {
			return ResponseEntity.ok(HttpStatus.BAD_REQUEST.value()+", FirstName or Lastname is missing");
		}
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Some Internal server issue "+e);
		}
	}
	

}
