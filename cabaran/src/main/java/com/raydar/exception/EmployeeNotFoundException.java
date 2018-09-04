package com.raydar.exception;

public class EmployeeNotFoundException extends RuntimeException{

	public EmployeeNotFoundException(String exception){
		super(exception);
	}
}
