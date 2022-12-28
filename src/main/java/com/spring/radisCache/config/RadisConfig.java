package com.spring.radisCache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.spring.radisCache.service.MessagePublisher;
import com.spring.radisCache.serviceImpl.RedisMessageSubscriberImpl;

//@EnableRedisRepositories(value = "com.tericcabrel.springbootcaching.repositories")
//@EnableRedisRepositories
@Configuration
public class RadisConfig {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private String port;

//	    @Autowired
//		RedisConnectionFactory redisConnectionFactory;

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host,
				Integer.parseInt(port));
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory2) {// new parameter
																										// redis
		RedisTemplate<String, Object> template = new RedisTemplate<>();

		template.setConnectionFactory(jedisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new GenericToStringSerializer<>(Object.class));
		template.setDefaultSerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
		template.setHashKeySerializer(new StringRedisSerializer());//
		template.setHashKeySerializer(new JdkSerializationRedisSerializer());//
		template.setEnableTransactionSupport(true);//
		template.setValueSerializer(new JdkSerializationRedisSerializer());
		template.afterPropertiesSet();//
		return template;
	}

	@Bean
	public ChannelTopic topic() {
		return new ChannelTopic("pubsub:arun-channel");// host name
	}

	@Bean
	public MessageListenerAdapter messageListenerAdapter() {
		return new MessageListenerAdapter(new RedisMessageSubscriberImpl(), "onMessage");
	}

	@Bean
	public RedisMessageListenerContainer redishMessageListenerContainetr() {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(jedisConnectionFactory());
		container.addMessageListener(messageListenerAdapter(), topic());
		return container;
	}

	// new
	@Bean 
	MessagePublisher messagePublisher() {
		return new RedisMessagePublisher(redisTemplate(jedisConnectionFactory()), topic());
	}

}
