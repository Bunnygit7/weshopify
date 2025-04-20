package com.weshopify.platform.validations;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserValidations implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4789491247720329508L;
	private List<UserMessage> messages;

}
