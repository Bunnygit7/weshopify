package com.weshopify.platform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {
	
	@Autowired
	private JwtAuthenticationService authnService;
	/*@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
		userDetailsManager
				.createUser(User.withUsername("bunny").password(encoder.encode("admin")).roles("USER").build());
		return userDetailsManager;
	
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/
	
	
	/*
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			return http.csrf(csrf -> csrf.disable())
					.authorizeHttpRequests(auth -> auth.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs.yaml",
							"/webjars/**", "/swagger-resources/**").permitAll()
		
					// Secure /cat endpoint
					.requestMatchers("/category").authenticated()
		
					// Secure all other endpoints
					.anyRequest().authenticated()).formLogin(Customizer.withDefaults()).build();
		}*/
	
	/*@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs.yaml",
						"/webjars/**", "/swagger-resources/**").permitAll()
	
				// Secure /cat endpoint
				.requestMatchers("/category").authenticated()
	
				// Secure all other endpoints
				.anyRequest().authenticated()).addFilterBefore(new JWTAuthnFilter(authnService), BasicAuthenticationFilter.class).build();
	}*/
		
	@Bean
	SecurityFilterChain filter(HttpSecurity http) throws Exception {
	    return http.csrf(csrf -> csrf.disable())
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(
	                "/swagger-ui.html", 
	                "/swagger-ui/**", 
	                "/v3/api-docs/**", 
	                "/v3/api-docs.yaml", 
	                "/webjars/**", 
	                "/swagger-resources/**",
	                "/actuator/**",
	                "/actuator/prometheus"
	            ).permitAll()
	            .anyRequest().authenticated()
	        )
	        .addFilterBefore(new JWTAuthnFilter(authnService), BasicAuthenticationFilter.class)
	        .build();
	}


}