package com.weshopify.platform.beans;

import java.io.File;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeshopifyplatformUserBean implements Serializable{

	private static final long serialVersionUID = 9081138530140060147L;
	
	private int userId;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String role;
	private Boolean status;
	private File photo;
}
