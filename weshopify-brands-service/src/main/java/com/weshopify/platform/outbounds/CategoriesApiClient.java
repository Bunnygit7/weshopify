package com.weshopify.platform.outbounds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CategoriesApiClient {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${service.categories.uri}")
	private String categoriesUri;
	
	private HttpServletRequest request;
	
	public String findCategoriesById(int id) {
		String uri=categoriesUri+id;
		log.info(uri);
		String headerWithBearer=request.getHeader("Authorization");
		log.info(headerWithBearer);
		HttpHeaders headers=new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, headerWithBearer);
		HttpEntity<String> requestBody=new HttpEntity<String>(headers);
		ResponseEntity<String> apiResponse=restTemplate.exchange(uri, HttpMethod.GET, requestBody, String.class);
		if(HttpStatus.OK.value()==apiResponse.getStatusCode().value()) {
			log.info(apiResponse.getBody());
			return apiResponse.getBody();
			
		}else {
			throw new RuntimeException("No category found with category id "+id);
		}
	}

}
