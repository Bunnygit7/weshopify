package com.weshopify.platform.service;

import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
		this.hashOps = this.redisTemplate.opsForHash(); // this is now safe
	}

	@Override
	public String authenticate(UserAuthnBean authnBean) {

		WSO2UserAuthnBean userAuthnBean = WSO2UserAuthnBean.builder().username(authnBean.getUserName())
				.password(authnBean.getPassword()).build();
		String authnResponse = authnCommunicator.authenticate(userAuthnBean);
		if (StringUtils.isNoneEmpty(authnResponse)) {
			JSONObject json = new JSONObject(authnResponse);
			String access_token = json.getString("access_token");
			String randomHash = authnBean.getUserName() + "_" + RandomStringUtils.random(512);
			hashOps.put(authnBean.getUserName(), randomHash, access_token);
			hashOps.put(access_token, randomHash, authnBean.getUserName());
		}
		return authnResponse;
	}

	@Override
	public String logout(String token) {
		
		String response = authnCommunicator.logout(token);
		JSONObject jsonResponse=new JSONObject();

		if (StringUtils.isNotBlank(response)) {
			Set<String> randomHkSet = hashOps.keys(token);
			randomHkSet.forEach(randomHash -> {
				String userName = hashOps.get(token, randomHash);
				hashOps.delete(userName, randomHash);
				hashOps.delete(token, randomHash);
				String logoutMessage="User "+userName+" has logged out Successfully!";
				jsonResponse.put("message", logoutMessage);
			});
		}
		
		return jsonResponse.toString();

	}

}
