package com.weshopify.platform.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weshopify.platform.beans.UserAuthnBean;
import com.weshopify.platform.model.WSO2UserAuthnBean;
import com.weshopify.platform.outbound.IamAuthnCommunicator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserAuthnServiceImpl implements UserAuthnService {

	@Autowired
	IamAuthnCommunicator authnCommunicator;
	@Autowired
	ObjectMapper objectMapper;

	private final RedisTemplate<String, String> redisTemplate;
	private final HashOperations<String, String, String> hashOps;

	public UserAuthnServiceImpl(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.hashOps = this.redisTemplate.opsForHash(); 
	}

	@Override
	public String authenticate(UserAuthnBean authnBean) {

		WSO2UserAuthnBean userAuthnBean = WSO2UserAuthnBean.builder().username(authnBean.getUserName())
				.password(authnBean.getPassword()).build();
		String authnResponse = authnCommunicator.authenticate(userAuthnBean);
		if (StringUtils.isNoneEmpty(authnResponse)) {
			JSONObject json = new JSONObject(authnResponse);
			String access_token = json.getString("access_token");
			int expiry=json.getInt("expires_in");
			String randomHash = authnBean.getUserName() + "_" + RandomStringUtils.random(512);
			hashOps.put(authnBean.getUserName(), randomHash, access_token);
			hashOps.put("SUBJECT", access_token, authnBean.getUserName());
			hashOps.put("tokenExpiry", access_token, String.valueOf(expiry));
			
			String userDetails=authnCommunicator.getUserInfo(access_token);
			JSONObject jsonUserDetails=new JSONObject(userDetails);
			JSONArray userRolesArray=jsonUserDetails.getJSONArray("roles");
			log.info(userRolesArray.toString());
			List<String> rolesList=new ArrayList<>();
			for(int i=0;i<userRolesArray.length();i++) {
				if(!"Internal/everyone".equals(userRolesArray.getString(i))){
					rolesList.add(userRolesArray.getString(i).replaceAll("Application/", ""));
				}
			}
//			String roles = userRolesArray.join(",").replaceAll("Application/", "");  // e.g., "USER,ADMIN"
			hashOps.put("USER_ROLES", access_token, rolesList.toString());
		}
		return authnResponse;
	}

	@Override
	public String logout(String token) {
		
		String response = authnCommunicator.logout(token);
		JSONObject jsonResponse=new JSONObject();

		if (StringUtils.isNotBlank(response)) {
			String userName = hashOps.get("SUBJECT", token);
			Set<String> randomHkSet = hashOps.keys(userName);
			randomHkSet.forEach(randomHash -> {
//				String userName = hashOps.get("SUBJECT", token);
				hashOps.delete(userName, randomHash);
				hashOps.delete("SUBJECT", token);
				hashOps.delete("tokenExpiry", token);
				hashOps.delete("USER_ROLES", token);
				String logoutMessage="User "+userName+" has logged out Successfully!";
				jsonResponse.put("message", logoutMessage);
			});
		}
		
		return jsonResponse.toString();

	}

}
