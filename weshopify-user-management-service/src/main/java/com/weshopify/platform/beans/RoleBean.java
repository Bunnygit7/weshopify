package com.weshopify.platform.beans;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class RoleBean implements Serializable{
	
	private static final long serialVersionUID = -4532937578385108874L;
	
	private String id;
	private String displayName;
	private String[] schemas;
	private List<String> permissions;

}
