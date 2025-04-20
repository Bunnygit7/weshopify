package com.weshopify.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weshopify.platform.beans.UserAuthnBean;
import com.weshopify.platform.service.UserAuthnService;

@RestController
public class UserAuthnController {
	@Autowired
	UserAuthnService userAuthnService;
	
	@PostMapping("/users/token")
	public ResponseEntity<String> authenticate(@RequestBody UserAuthnBean authnBean) {
		
		String response=userAuthnService.authenticate(authnBean);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/user/logout/{accessToken}")
	public ResponseEntity<String> logout(@RequestParam String accessToken){
		String response=userAuthnService.logout(accessToken);
		return ResponseEntity.ok(response);
		
	}
	

}
