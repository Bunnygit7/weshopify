package com.weshopify.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class WeshopifyUserManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeshopifyUserManagementServiceApplication.class, args);
	}

    @Bean
    RestTemplate restTemplate() {
		return new RestTemplate();
	}

    @Bean
    ObjectMapper getObjectMapper() {
    	return new ObjectMapper();
    }
    
//    @Bean
//    public RestTemplate developementRestTemplate() throws Exception {
//        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
//
//        SSLContext sslContext = SSLContexts.custom()
//            .loadTrustMaterial(null, acceptingTrustStrategy)
//            .build();
//
//        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
//
//        CloseableHttpClient httpClient = HttpClients.custom()
//            .setSSLSocketFactory(csf)
//            .build();
//
//        HttpComponentsClientHttpRequestFactory factory =
//            new HttpComponentsClientHttpRequestFactory(httpClient);
//
//        return new RestTemplate(factory);
//    }



}
