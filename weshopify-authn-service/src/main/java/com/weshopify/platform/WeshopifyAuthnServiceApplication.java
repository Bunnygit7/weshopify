package com.weshopify.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class WeshopifyAuthnServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeshopifyAuthnServiceApplication.class, args);
	}

	  @Bean
	    RestTemplate restTemplate() {
			return new RestTemplate();
		}

	    @Bean
	    ObjectMapper getObjectMapper() {
	    	return new ObjectMapper();
	    }
}
