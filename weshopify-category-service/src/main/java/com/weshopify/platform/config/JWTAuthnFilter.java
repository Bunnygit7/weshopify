package com.weshopify.platform.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JWTAuthnFilter extends GenericFilterBean {
	
	private JwtAuthenticationService jwtAuthnService;
	
	JWTAuthnFilter(JwtAuthenticationService jwtAuthnService){
		this.jwtAuthnService=jwtAuthnService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {
	    try {
	        Authentication authn = jwtAuthnService.authenticateUser((HttpServletRequest) request);
	        if (authn != null) {
	            SecurityContextHolder.getContext().setAuthentication(authn);
	        }
	    } catch (RuntimeException e) {
	        // Optionally log the error or let it fall through unauthenticated
	        logger.warn("JWT authentication failed: " + e.getMessage());
	    }
	    chain.doFilter(request, response);
	}


}
