package com.spring.radisCache.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
	private String data;
	
	private String author;

	@Override
	public String toString() {
		return "Message [data=" + data + ", author=" + author + "]";
	}

	
	
}
