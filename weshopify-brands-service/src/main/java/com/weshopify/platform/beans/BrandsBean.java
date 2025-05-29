package com.weshopify.platform.beans;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class BrandsBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2211080978319267614L;
	private String id;
	private String name;
	private String logoPath;
	private Set<CategoryBean> categories;
	

}
