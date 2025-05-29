package com.weshopify.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WeshopifyCategoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeshopifyCategoryServiceApplication.class, args);
	}

}
