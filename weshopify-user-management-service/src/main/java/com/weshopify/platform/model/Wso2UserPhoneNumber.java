package com.weshopify.platform.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wso2UserPhoneNumber implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 5904941301406237160L;
	
	private String type;
	private String value;
	
//	@JsonCreator
//	public static Wso2UserPhoneNumber fromString(String value) {
//		return Wso2UserPhoneNumber.builder()
//				.type("work") 
//				.value(value)
//				.build();
//	}

}
