package com.weshopify.platform.validations;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6692586482273073390L;
	private String message;
	private int statusCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	private Date timeStamp;

}
