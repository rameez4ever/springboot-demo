package com.raydar.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Rameez.shikalgar
 * EmployeeDataResponseDTO.java - DTO for employee type response.
 */
@Data
@AllArgsConstructor
public class EmployeeDataResponseDTO extends BaseResponseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<EmployeeDTO> data;
	
	public EmployeeDataResponseDTO(){
		
	}
	
	public EmployeeDataResponseDTO(Boolean status, List<EmployeeDTO> data){
		super(status);
		this.data = data;
	}
	
	public EmployeeDataResponseDTO(Boolean status, Integer error_code, String error_message){
		super(status, error_code, error_message);
	}
}
