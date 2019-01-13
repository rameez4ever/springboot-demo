package com.raydar.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
    private String fullName;
    private Integer age;
    private Double salary;
    
    public EmployeeDTO(){
    	
    }
    
    @Override
	public String toString() {
		return "Employee{" + "id=" + id + ", fullName='" + fullName + '\'' + ", age='" + age + '\'' + ", salary='"
				+ salary + '\'' + '}';
	}
}
