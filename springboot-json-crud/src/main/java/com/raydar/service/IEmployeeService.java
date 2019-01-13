package com.raydar.service;

import com.raydar.dto.EmployeeDTO;
import com.raydar.dto.EmployeeDataResponseDTO;
import com.raydar.filter.SearchCriteria;

public interface IEmployeeService {

	EmployeeDataResponseDTO get(SearchCriteria criteria) throws Exception;
	EmployeeDataResponseDTO save(EmployeeDTO employee) throws Exception;
	EmployeeDataResponseDTO update(EmployeeDTO employee) throws Exception;
	EmployeeDataResponseDTO delete(Long id) throws Exception;
	EmployeeDataResponseDTO filterByAge(SearchCriteria criteria) throws Exception;
	EmployeeDataResponseDTO refreshCache() throws Exception;
}
