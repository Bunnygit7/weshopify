package com.weshopify.platform.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullName implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -2672723916232353278L;

	private String givenName;
	private String familyName;
}
