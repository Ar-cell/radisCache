package com.spring.radisCache.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.spring.radisCache.config.Response;
import com.spring.radisCache.dto.CustomerDto;
import com.spring.radisCache.entity.Customer;

public interface CustomerService {

	List<Customer> getAll();
	
	Customer add(Customer customer);

	Customer update(Customer customer);

	void delete(Integer id);

	Customer getCustomerById(Integer id);

	List<CustomerDto> fetchCustomer();

	Response fetchCustomer(String name);

}
