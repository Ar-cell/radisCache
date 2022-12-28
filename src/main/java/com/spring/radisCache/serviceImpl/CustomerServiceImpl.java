package com.spring.radisCache.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.hibernate.sql.ast.tree.cte.CteColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.spring.radisCache.config.Response;
import com.spring.radisCache.dto.CustomerDto;
import com.spring.radisCache.entity.Customer;
import com.spring.radisCache.repository.CustomerRepository;
import com.spring.radisCache.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	    
	// old code working fine!
	@Autowired
	private CustomerRepository customerRepository;
	
	//public static final String Hash_Key = "customer";

	@Autowired
	private RedisTemplate<String, Object> template;
	
	@Cacheable(value="customers")
	@Override
	public List<Customer> getAll(){
//		//waitSomeTime();
//		List<Customer> customers;
//		customers =template.opsForHash().values(Hash_Key);
//		return customers;
		return this.customerRepository.findAll();
	}
	
	//@CacheEvict(cacheNames="customers", allEntries=true)
	@Override
	public Customer add(Customer customer) {
		return this.customerRepository.save(customer);
	}
	
	@CachePut(value = "customers", key="#customer.id")
	//@Cacheable(value="customers", key="#customers.id")
	@Override
	public Customer update(Customer customer) {
		Optional<Customer> optCustomer = this.customerRepository.findById(customer.getId());
		if (!optCustomer.isPresent())
			return null;
		Customer repCustomer = optCustomer.get();
		repCustomer.setId(customer.getId());
		repCustomer.setName(customer.getName());
		repCustomer.setContactName(customer.getContactName());
		repCustomer.setAddress(customer.getAddress());
		repCustomer.setCity(customer.getCity());
		repCustomer.setPostalCode(customer.getPostalCode());
		repCustomer.setCountry(customer.getCountry());

		return this.customerRepository.save(repCustomer);
	} 

	
	// caching and cacheEvict use delete data from cacahe also like the db. 
//	@Caching(evict = { @CacheEvict(cacheNames = "customers", key = "#id"),
//			@CacheEvict(cacheNames = "customers", allEntries = true) })
	@Override
	public void delete(Integer id) {
		this.customerRepository.deleteById(id);
	}
	
	@Cacheable(value = "customers", key = "#id", unless = "#result == null")
	@Override
	public Customer getCustomerById(Integer id) {
		//waitSomeTime();
//		Customer customer;
//		customer =(Customer) template.opsForHash().get(Hash_Key, id.toString());
//		return customer;
		return this.customerRepository.findById(id).orElse(null);
	}
	
	private void waitSomeTime() {
		System.out.println("Long Wait Begin");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Long Wait End");
	}
	
	@Cacheable(value = "customers")
	@Override
	public List<CustomerDto> fetchCustomer() {
		List<CustomerDto> dtoCustomer = new ArrayList<>();
		List<Customer> customerList = customerRepository.findAll();
		for(Customer customer: customerList) {
			CustomerDto dto = new CustomerDto();
			dto.setName(customer.getName());
			dto.setAddress(customer.getAddress());
			dto.setCity(customer.getCity());
			dto.setCountry(customer.getCountry());
			dto.setId(customer.getId());
			dto.setPostalCode(customer.getPostalCode());
			dtoCustomer.add(dto);
			//dtoCustomer=template.opsForHash().values(Hash_Key);
			
		}
		return dtoCustomer;
	}
	
	
	//find by name
	@Cacheable(value="customers")
	@Override
	public Response fetchCustomer(String name) {
		Optional<Customer> optionalCustomer = customerRepository.findByName(name);
		if(optionalCustomer.isPresent()) {
			Customer user = optionalCustomer.get();
			Customer usr = new Customer();
			usr.setName(user.getName());
			usr.setAddress(user.getAddress());
			usr.setId(user.getId());
			return new Response("User find successfully", usr, HttpStatus.OK);
		}
		
		return new Response("user not found for name= "+ name, HttpStatus.BAD_REQUEST);
	}

	//old code till here!

}
