package com.weshopify.platform.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.weshopify.platform.beans.BrandsBean;
import com.weshopify.platform.service.BrandsService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class BrandsController {
	
	@Autowired
	BrandsService brandsService;
	
	@Operation(summary = "findAllBrands", security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping("/brands")
	public ResponseEntity<List<BrandsBean>> findAllBrands(){
		return ResponseEntity.ok(brandsService.findAllBrands());
	}
	
	@Operation(summary = "findBrand", security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping("/brands/{brandId}")
	public ResponseEntity<BrandsBean> findBrand(@PathVariable String brandId){
		return ResponseEntity.ok(brandsService.findBrandById(brandId));
	}
	
//	@CircuitBreaker(name = "brands-service-circuit", fallbackMethod = "createBrandFallBackMethod")
	@Retry(name = "brands-service-retry")
	@Operation(summary = "createBrand", security = @SecurityRequirement(name = "bearerAuth"))
	@PostMapping("/brands")
	public ResponseEntity<BrandsBean> createBrand(@RequestBody BrandsBean brandBean) {
		return ResponseEntity.ok(brandsService.createBrand(brandBean));
	}
	
	@Operation(summary = "updateBrand", security = @SecurityRequirement(name = "bearerAuth"))
	@PutMapping("/brands")
	public ResponseEntity<BrandsBean> updateBrand( @RequestBody BrandsBean brandBean) {
		return ResponseEntity.ok(brandsService.updateBrand(brandBean));
	}
	
	@Operation(summary = "deleteBrand", security = @SecurityRequirement(name = "bearerAuth"))
	@DeleteMapping("/brands/{brandId}")
	public ResponseEntity<List<BrandsBean>> deleteBrand(@PathVariable String brandId){
		return ResponseEntity.ok(brandsService.deleteBrand(brandId));
	}
	
	@Operation(summary = "deleteAllBrands", security = @SecurityRequirement(name = "bearerAuth"))
	@DeleteMapping("/brands/clean")
	public ResponseEntity<Map<String, String>> cleanDB(String brandId){
		brandsService.cleanDB();
		Map<String, String> response=new HashMap<>();
		response.put("message", "All Brands Are Deleted");
		return ResponseEntity.ok(response);
	}

	private ResponseEntity<Map<String, String>> createBrandFallBackMethod(BrandsBean brandBean, Throwable throwable) {
	    Map<String, String> response = new HashMap<>();
	    response.put("key", "Failed to create brand, please try again!");
	    response.put("Exception", throwable.getMessage());
	    return ResponseEntity.internalServerError().body(response);
	}
}
