package com.sunbase.assignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbase.assignment.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
