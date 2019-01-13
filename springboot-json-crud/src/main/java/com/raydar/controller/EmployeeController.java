package com.raydar.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.raydar.dto.EmployeeDTO;
import com.raydar.dto.EmployeeDataResponseDTO;
import com.raydar.filter.SearchCriteria;
import com.raydar.service.IEmployeeService;
import com.raydar.validator.EmployeeValidator;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
	
	@Autowired
	private IEmployeeService employeeService;
	
	@InitBinder("employeeDTO")
	protected void initEmployeeBinder(WebDataBinder binder) {
	    binder.addValidators(new EmployeeValidator()); 
	}

	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String index() throws Exception {
		return "Hello from Cabaran Microservice!";
	}
	
	@RequestMapping(value = {"/get"}, method = RequestMethod.GET)
	public EmployeeDataResponseDTO get(SearchCriteria criteria) throws Exception{
		return employeeService.get(criteria);
	}
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.POST)
	public EmployeeDataResponseDTO save(@Valid @RequestBody EmployeeDTO employeeDTO) throws Exception{
		return employeeService.save(employeeDTO);
	}
	
	@RequestMapping(value = {"/update"}, method = RequestMethod.PATCH)
	public EmployeeDataResponseDTO update(@Valid @RequestBody EmployeeDTO employeeDTO) throws Exception{
		return employeeService.update(employeeDTO);
	}
	
	@RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.DELETE)
	public EmployeeDataResponseDTO delete(@PathVariable Long id) throws Exception{
		return employeeService.delete(id);
	}
	
	@RequestMapping(value = {"/filterByAge"}, method = RequestMethod.GET)
	public EmployeeDataResponseDTO filterByAge(SearchCriteria criteria) throws Exception{
		return employeeService.filterByAge(criteria);
	}
	
	@RequestMapping(value = {"/refresh"}, method = RequestMethod.GET)
	public void refreshCache() throws Exception{
		employeeService.refreshCache();
	}
}
