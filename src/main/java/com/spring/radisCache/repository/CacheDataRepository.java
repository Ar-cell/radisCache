package com.spring.radisCache.repository;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spring.radisCache.entity.CacheData;

@Repository
@EnableRedisRepositories(value = "com.tericcabrel.springbootcaching.repositories")
public interface CacheDataRepository extends CrudRepository<CacheData, String> {
	

}
