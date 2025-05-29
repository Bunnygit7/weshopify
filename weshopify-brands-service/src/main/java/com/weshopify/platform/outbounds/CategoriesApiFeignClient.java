package com.weshopify.platform.outbounds;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

//@FeignClient(url = "${service.categories.uri}",value = "CategoriesApiFeignClient")
//@FeignClient(name = "weshopify-category-service")
@FeignClient(name = "weshopify-api-gateway")
public interface CategoriesApiFeignClient {

	@GetMapping(value = "/categories/{categoryId}")
	public ResponseEntity<String> findCategoryById(@PathVariable int categoryId, @RequestHeader final Map<String, String> headers);
}
