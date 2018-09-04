package com.raydar.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Rameez.shikalgar
 * BaseResponseDTO.java - This will add generic data like status, error codes, messages to
 */
@Data
@AllArgsConstructor
public class BaseResponseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Boolean status;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer error_code;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;
	
	public BaseResponseDTO(){
		
	}
	
	public BaseResponseDTO(Boolean status){
		this.status = status;
	}
}
