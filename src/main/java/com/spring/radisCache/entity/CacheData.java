package com.spring.radisCache.entity;

import org.springframework.data.redis.core.index.Indexed;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Data
@Accessors(chain=true)
//@RedisHash("cacheData")
public class CacheData {
	
	@Id
	private String key;

	@Indexed
	private String value;
}
