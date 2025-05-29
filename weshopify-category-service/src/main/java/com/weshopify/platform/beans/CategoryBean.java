package com.weshopify.platform.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBean implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -4270205794697186L;
	private int id;
	private String name;
	private String alias;
	private int parentCategory;
	private boolean enabled;
	

}
