package com.spring.radisCache.controller;

import java.util.List;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.radisCache.config.Response;
import com.spring.radisCache.dto.CustomerDto;
import com.spring.radisCache.entity.Customer;
//import com.spring.radisCache.repository.CacheDataRepository;
import com.spring.radisCache.service.CustomerService;

@RestController
@RequestMapping("/api")
@EnableCaching
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	
	
//	@Autowired
// 	private  CacheDataRepository cacheDataRepository;
//	
//	private  ObjectMapper ObjectMapper;
//	
//	public CustomerController(CustomerService productService, CacheDataRepository cacheDataRepository) {
//        this.customerService = customerService;
//        this.cacheDataRepository = cacheDataRepository;
//        this.ObjectMapper = new ObjectMapper();
//    }
	
	@PostMapping("/save")
	public Customer add(@RequestBody Customer customer) {
		return customerService.add(customer);
	} 
	
	@GetMapping("/getAll")
	public List<Customer> getAll(){
		return customerService.getAll();				
	}
	
	@PutMapping("/update/{id}")
	Customer update(@RequestBody Customer customer) {
		return customerService.update(customer);
	}
	
	@GetMapping("/delete/{id}")
	void delete(@PathVariable Integer id) {
		 customerService.delete(id);
	}
	

	@GetMapping("getById/{id}")
	Customer getCustomerById(@PathVariable Integer id) {
		return customerService.getCustomerById(id);
	}
	
	@GetMapping("/fetchAll")
	List<CustomerDto> fetchCustomer(){
		return customerService.fetchCustomer();
	}
	
	@GetMapping("/search/{name}")
	public Response fetchCustomer(@PathVariable String name) {
		return customerService.fetchCustomer(name);
				
	}
}
