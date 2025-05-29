package com.weshopify.platform.cqrs.domainevent;

import java.io.Serializable;

import org.axonframework.spring.stereotype.Aggregate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryEvent implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -4270205794697186L;
	private String eventId;
	private int id;
	private String name;
	private String alias;
	private int parentCategory;
	private boolean enabled;
	

}
