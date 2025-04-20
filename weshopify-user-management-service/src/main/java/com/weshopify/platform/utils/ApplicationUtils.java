package com.weshopify.platform.utils;

import java.lang.reflect.Type;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weshopify.platform.beans.RoleBean;
import com.weshopify.platform.model.Wso2User;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Data
@Slf4j
public class ApplicationUtils {

	@Autowired
	ObjectMapper objectMapper;

	@Value("${iamserver.base-url}")
	public String iam_server_api_base_url;

	@Value("${iamserver.username}")
	public String iam_user_name;

	@Value("${iamserver.password}")
	public String iam_password;

	@Value("${iamserver.role-api-schema}")
	public String role_api_schema;

	@Value("${iamserver.role-api}")
	public String role_api_context;

	@Value("${iamserver.user-api}")
	public String user_api_context;
	
	@Value("${iamserver.role-api}")
	public String role_api_url;

	
	public HttpEntity<String> prepareRequestBody(String roleBeanPayload) {
		HttpEntity<String> requestBody = null;
		String combinedCreds = iam_user_name + ":" + iam_password;

		String encodedAdminCreds = Base64.getEncoder().encodeToString(combinedCreds.getBytes());
//		log.info(encodedAdminCreds);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + encodedAdminCreds);

		if (StringUtils.isBlank(roleBeanPayload) || StringUtils.isEmpty(roleBeanPayload)) {
			requestBody = new HttpEntity<>(headers);
		} else {
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			requestBody = new HttpEntity<>(roleBeanPayload, headers);
		}

		return requestBody;
	}

	
	public List<Wso2User> parseUserResponse(Object responseBody) {
		List<Wso2User> resourcesList = null;
		try {
			String response = objectMapper.writeValueAsString(responseBody);
//			log.info("response body:" + response);

			JSONObject jsonResponseObject = new JSONObject(response);

			JSONArray jsonArray = (JSONArray) Optional.ofNullable(jsonResponseObject)
					.filter(condition -> jsonResponseObject.has("Resources")).get().get("Resources");

			Gson gson = new Gson();
			Type type = new TypeToken<List<Wso2User>>() {
			}.getType();
			resourcesList = gson.fromJson(jsonArray.toString(), type);
//			log.info(resourcesList.toString());

		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return Optional.ofNullable(resourcesList).get();
	}
	
	
	public List<RoleBean> parseRoleResponse(Object responseBody) {
		List<RoleBean> resourcesList = null;
		try {
			String response = objectMapper.writeValueAsString(responseBody);
//			log.info("response body:" + response);

			JSONObject jsonResponseObject = new JSONObject(response);

			JSONArray jsonArray = (JSONArray) Optional.ofNullable(jsonResponseObject)
					.filter(condition -> jsonResponseObject.has("Resources")).get().get("Resources");

			Gson gson = new Gson();
			Type type = new TypeToken<List<RoleBean>>() {
			}.getType();
			resourcesList = gson.fromJson(jsonArray.toString(), type);
			log.info(resourcesList.toString());

		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return Optional.ofNullable(resourcesList).get();
	}


	public Wso2User parseSingleUserResponse(Object responseBody) {
//		String response=responseBody.toString();
//		log.info(response);
//		Optional.of(responseBody).stream().forEach(field->{
//			Wso2User.builder().emails().build();
//		});
		Wso2User user=objectMapper.convertValue(responseBody, Wso2User.class);
		return user;
	}
}
