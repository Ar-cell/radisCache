package com.spring.radisCache.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.radisCache.config.RedisMessagePublisher;
import com.spring.radisCache.entity.Customer;
import com.spring.radisCache.serviceImpl.RedisMessageSubscriberImpl;

@RestController
@RequestMapping("/api")
public class RedisController {

	@Autowired
	private RedisMessagePublisher messagePublisher;
	
	@Autowired
	RedisTemplate<String, Object> redisRemplate;
	

	// message publish API
//	@PostMapping("/published")
//	public void publish(@RequestBody Message message) {
//		messagePublisher.publis(message.toString());
//	}
	
	
	@GetMapping("/subscribe")
	public List<String> getMessage(){
		return RedisMessageSubscriberImpl.messageList;
	}

	
	@PostMapping("/publish")
	public String publish(@RequestBody Customer customer) {
		messagePublisher.publish(customer);
		return "Event published !!";
	}
	
	
	
	//extra code for data as message
//	@PostMapping("/publish")
//	public String publish(@RequestBody Customer customer) {
//		redisRemplate.convertAndSend(topic.getTopic(), customer);
//		return "Event published !!";
//	}
}
