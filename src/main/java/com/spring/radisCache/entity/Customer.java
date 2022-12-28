package com.spring.radisCache.entity;

import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cust_cache")
@AllArgsConstructor
@NoArgsConstructor
@Data
//@RedisHash("customers")
public class Customer implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="name")
	private  String name;
	
	@Column(name="contactName")
	private String contactName;
	
	@Column(name="address")
	private String address;
	
	@Column(name="city")
	private String city;
	
	@Column(name="postalCode")
	private int PostalCode;
	 
	@Column(name="country")
	private String country;

}
