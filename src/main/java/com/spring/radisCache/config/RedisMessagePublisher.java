package com.spring.radisCache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;
import com.spring.radisCache.entity.Customer;
import com.spring.radisCache.repository.CustomerRepository;
import com.spring.radisCache.service.MessagePublisher;

@Component
public class RedisMessagePublisher implements MessagePublisher {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private ChannelTopic topic;
	
	@Autowired
	CustomerRepository custtomerRepository;
	
	
	
	public RedisMessagePublisher(RedisTemplate<String, Object> redisTemplate, ChannelTopic topic) {
		this.redisTemplate = redisTemplate;
		this.topic = topic;
	}


	// this method is for only message send
	@Override
	public void publis(String message) {
		redisTemplate.convertAndSend(topic.getTopic(), message);
		
	}
	
	
	// this method is for data send as message! main method
	public void publish(Customer customer) {
		redisTemplate.convertAndSend(topic.getTopic(), customer);
	}
	
	
	
	//same ass the main method
	//public void publish(Customer customer) {
//		Optional<Customer> custOptional = custtomerRepository.findById(customer.getId());
//		if(custOptional.isPresent()) {
//			Customer cust = custOptional.get();
//			
////			cust.setAddress(customer.getAddress());
////			cust.setCity(customer.getCity());
////			cust.setContactName(customer.getContactName());
////			cust.setCountry(customer.getCountry());
////			cust.setId(customer.getId());
////			cust.setName(customer.getName());
////			cust.setPostalCode(customer.getPostalCode());
////			custtomerRepository.save(cust);
//			redisTemplate.convertAndSend(topic.getTopic(), customer);
//		}
//		//redisTemplate.convertAndSend(topic.getTopic(), customer);
//	}
	

}
