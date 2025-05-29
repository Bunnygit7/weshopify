package com.weshopify.platform.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertFalse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6674937149851038361L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable = false, unique = true, updatable = true)
	private String name;
	@Column(updatable = true, nullable = false)
	private String alias;
	private String imagePath;
	@Column(nullable = false, updatable = true)
	private boolean enabled;
	@ManyToOne
	private Category parent;

}
