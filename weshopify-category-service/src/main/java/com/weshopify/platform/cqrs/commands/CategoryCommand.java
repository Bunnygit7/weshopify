package com.weshopify.platform.cqrs.commands;

import java.io.Serializable;

import org.axonframework.modelling.command.AggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCommand implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -4270205794697186L;
	@AggregateIdentifier
	private String eventId;
	private int id;
	private String name;
	private String alias;
	private int parentCategory;
	private boolean enabled;
	

}
