package com.raydar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.raydar.domain.Employee;
import com.raydar.dto.EmployeeDTO;
import com.raydar.dto.EmployeeDataResponseDTO;
import com.raydar.exception.EmployeeNotFoundException;
import com.raydar.filter.EmployeeSpecificationBuilder;
import com.raydar.filter.SearchCriteria;
import com.raydar.repository.EmployeeJpaRepository;
import com.raydar.repository.IEmployeeRepository;
import com.raydar.util.StringUtils;

@Service
public class EmployeeServiceImpl implements IEmployeeService{
	
	@Autowired
	private ICacheService cacheService;
	
	@Autowired
	private IEmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeJpaRepository jpaRepository;
	
	@Override
	public EmployeeDataResponseDTO get(SearchCriteria criteria) throws Exception {
		
		Iterable<Employee> employees = jpaRepository.findAll(buildEmployeeSpecification(criteria));
		
		List<EmployeeDTO> data = new ArrayList<>();
		employees.forEach(employee -> data.add(
				new EmployeeDTO(employee.getId(), employee.getFullName(), employee.getAge(), employee.getSalary())));
		return new EmployeeDataResponseDTO(true, data);
	}

	@Override
	public EmployeeDataResponseDTO save(EmployeeDTO employee) throws Exception {
		employeeRepository.save(new Employee(employee.getId(), employee.getFullName(), employee.getAge(), employee.getSalary()));
		return new EmployeeDataResponseDTO(true, null, "Data saved successfully");
	}

	@Override
	public EmployeeDataResponseDTO update(EmployeeDTO employee) throws Exception {
		
		Map<Long, EmployeeDTO> employeeMap = cacheService.getEmployeeMap();
		EmployeeDTO dbEmployee = employeeMap.get(employee.getId());
		
		if(null == dbEmployee){
			throw new EmployeeNotFoundException("No employee found with id: " + employee.getId());
		}
		
		String[] nullPropertyNames = StringUtils.getNullPropertyNames(employee);
		BeanUtils.copyProperties(employee, dbEmployee, nullPropertyNames);
		
		employeeRepository.update(new Employee(dbEmployee.getId(), dbEmployee.getFullName(), dbEmployee.getAge(), dbEmployee.getSalary()));
		return new EmployeeDataResponseDTO(true, null, "Data updated successfully");
	}
	
	@Override
	public EmployeeDataResponseDTO delete(Long id) throws Exception {
		employeeRepository.delete(id);
		return new EmployeeDataResponseDTO(true, null, "Data deleted successfully");
	}

	@Override
	public EmployeeDataResponseDTO filterByAge(SearchCriteria criteria) throws Exception {
		criteria.setKey("age");
		
		Iterable<Employee> employees = jpaRepository.findAll(buildEmployeeSpecification(criteria));

		List<EmployeeDTO> data = new ArrayList<>();
		employees.forEach(employee -> data.add(
				new EmployeeDTO(employee.getId(), employee.getFullName(), employee.getAge(), employee.getSalary())));
		return new EmployeeDataResponseDTO(true, data);
	}
	
	@Override
	public void refreshCache() throws Exception {
		employeeRepository.refreshCache();
	    Set<Employee> employees = employeeRepository.get();
		jpaRepository.saveAll(employees);
	}
	
	private static Specification<Employee> buildEmployeeSpecification(SearchCriteria criteria){
		EmployeeSpecificationBuilder builder = new EmployeeSpecificationBuilder();
		builder.with(criteria);
		return builder.build();
	}
}
