package com.weshopify.platform.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtAuthenticationService {
	

	private final RedisTemplate<String, String> redisTemplate;
	private final HashOperations<String, String, String> hashOps;
	

	public JwtAuthenticationService(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.hashOps = this.redisTemplate.opsForHash(); 
	}

	
	private static final String JWT_TOKEN_HEADER_NAME="Authorization";
	private static final String JWT_TOKEN_EXPIRY_TIME="tokenExpiry";
	private static final String JWT_TOKEN_TYPE="Bearer ";
	private static final String USER_ROLES_KEY="USER_ROLES";
	private static final String USER_SUBJECT_NAME="SUBJECT";
	
	public Authentication authenticateUser(HttpServletRequest request) {
		Authentication authn=null;
		String token=resolveToken(request);
		boolean isTokenValid=validateToken(token);
		if(isTokenValid) {
			
			String userRoles=hashOps.get(USER_ROLES_KEY, token);
			List<GrantedAuthority> roles=new ArrayList<>();
			roles.add(new SimpleGrantedAuthority(userRoles));
			String userName=hashOps.get(USER_SUBJECT_NAME, token);
			
			authn=new UsernamePasswordAuthenticationToken(userName, null, roles);
			
		}
		return authn;
	}
	
	private boolean validateToken(String token) {
		if(hashOps.hasKey(JWT_TOKEN_EXPIRY_TIME, token)) {
			String expiryInSeconds=hashOps.get(JWT_TOKEN_EXPIRY_TIME, token);
			Long tokenExpiryInSeconds=Long.valueOf(expiryInSeconds);
			if(expiryDate(tokenExpiryInSeconds).before(new Date())){
				return false;
			}
			
			return true;
		}else {
			throw new RuntimeException("Token is Invalid! please login again.");
		}
		
		
	}
	
	private Date expiryDate(long tokenExpiry) {
		Date date=new Date();
		long time=date.getTime()+tokenExpiry*1000;
		Date updatedTime=new Date(time);
		return updatedTime;
		
	}
	private String resolveToken(HttpServletRequest request) {
	    String headerValue = request.getHeader(JWT_TOKEN_HEADER_NAME);
	    
	    if (headerValue != null && headerValue.startsWith(JWT_TOKEN_TYPE)) {
	        return headerValue.replace(JWT_TOKEN_TYPE, "");
	    } else {
	        throw new RuntimeException("Invalid or missing Authorization header");
	    }
	}


}
