package com.weshopify.platform.beans;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBean implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 8410977622848138308L;

	private String id;
	@NotEmpty(message = "{firstName.notNull.msg}")
	private String firstName;
	@NotEmpty(message = "{lastName.notNull.msg}")
	private String lastName;
	@NotEmpty(message = "{password.notNull.msg}")
	private String password;
	@NotEmpty(message = "{email.notNull.msg}")
//	@Email(message = "{email.invalid.msg}")
	private String[] emails;
	@NotEmpty(message = "{userId.notNull.msg}")
	private String userId;
	private boolean status;
	@NotEmpty(message = "{mobile.notNull.msg}")
	@Digits(integer = 10, fraction = 0, message = "{mobile.notNumber.msg}")
	@Min(value = 10, message = "{moblie.length.msg}")
	private String mobile;
}
