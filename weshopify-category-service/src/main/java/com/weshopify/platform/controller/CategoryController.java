package com.weshopify.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.weshopify.platform.beans.CategoryBean;
import com.weshopify.platform.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;



@RestController
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@Operation(summary = "findAllCategories", security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryBean>> findAllCategories(){
		return ResponseEntity.ok(categoryService.findAllCategories());
	}
	
	@Operation(summary = "findCategory", security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping("/categories/{categoryId}")
	public ResponseEntity<CategoryBean> findCategory(@PathVariable int categoryId){
		return ResponseEntity.ok(categoryService.findCategoryById(categoryId));
	}
	
	@Operation(summary = "findChildCategories", security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping("/categories/child/{parentId}")
	public ResponseEntity<List<CategoryBean>> findChildCategories(@PathVariable int parentId) {
		return ResponseEntity.ok(categoryService.findAllChildCategories(parentId));
	}
	
	@Operation(summary = "createCategory", security = @SecurityRequirement(name = "bearerAuth"))
	@PostMapping("/categories")
	public ResponseEntity<CategoryBean> createCategory(@RequestBody CategoryBean categoryBean) {
		return ResponseEntity.ok(categoryService.createCategory(categoryBean));
	}
	
	@Operation(summary = "updateCategory", security = @SecurityRequirement(name = "bearerAuth"))
	@PutMapping("categories")
	public ResponseEntity<CategoryBean> updateCategory( @RequestBody CategoryBean categoryBean) {
		return ResponseEntity.ok(categoryService.updateCategory(categoryBean));
	}
	
	@Operation(summary = "deleteCategory", security = @SecurityRequirement(name = "bearerAuth"))
	@DeleteMapping("/categories/{categoryId}")
	public ResponseEntity<List<CategoryBean>> deleteCategory(@PathVariable int categoryId){
		return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
	}
	
	

}
