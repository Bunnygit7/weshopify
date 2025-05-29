package com.weshopify.platform.model;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.weshopify.platform.beans.CategoryBean;

import lombok.Data;

@Document("Brands")
@Data
public class Brands implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3434737899079662222L;
	
	@Id
	private String id;
	@Indexed(unique = true)
	private String name;
	private String logoPath;
	private Set<CategoryBean> categories;
	
	

}
