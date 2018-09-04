package com.raydar.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.raydar.dto.EmployeeDTO;

public class EmployeeValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return EmployeeDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors e) {
		ValidationUtils.rejectIfEmpty(e, "id", "id.empty");
	}

}
