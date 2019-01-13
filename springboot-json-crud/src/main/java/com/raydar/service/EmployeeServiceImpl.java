package com.raydar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.raydar.domain.Employee;
import com.raydar.dto.EmployeeDTO;
import com.raydar.dto.EmployeeDataResponseDTO;
import com.raydar.exception.EmployeeNotFoundException;
import com.raydar.filter.EmployeeSpecificationBuilder;
import com.raydar.filter.SearchCriteria;
import com.raydar.repository.EmployeeJpaRepository;
import com.raydar.repository.IEmployeeJsonRepository;
import com.raydar.util.StringUtils;

/**
 * @author Rameez.shikalgar
 * Service APIs which connects to multiple data repositories
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService{
	
	private static Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class.getName());
	
	@Autowired
	private ICacheService cacheService;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private IEmployeeJsonRepository employeeRepository;
	
	@Autowired
	private EmployeeJpaRepository jpaRepository;
	
	/* (non-Javadoc)
	 * @see com.raydar.service.IEmployeeService#get(com.raydar.filter.SearchCriteria)
	 * Service API to fetch employees based on SerachCriteria
	 */
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
		logger.info("Saving " + employee);
		employeeRepository.save(new Employee(employee.getId(), employee.getFullName(), employee.getAge(), employee.getSalary()));
		
		String successMessage = messageSource.getMessage("success.data.saved",
				new String[] { null }, LocaleContextHolder.getLocale());
		return new EmployeeDataResponseDTO(true, null, successMessage);
	}

	@Override
	public EmployeeDataResponseDTO update(EmployeeDTO employee) throws Exception {
		
		Map<Long, EmployeeDTO> employeeMap = cacheService.getEmployeeMap();
		if(employeeMap == null || employeeMap.isEmpty()){
			String errorMessage = messageSource.getMessage("error.employee.notfound",
					new String[] { employee.getId().toString() }, LocaleContextHolder.getLocale());
			throw new EmployeeNotFoundException(errorMessage);
		}
		EmployeeDTO dbEmployee = employeeMap.get(employee.getId());
		
		if(null == dbEmployee){
			String errorMessage = messageSource.getMessage("error.employee.notfound",
					new String[] { employee.getId().toString() }, LocaleContextHolder.getLocale());
			throw new EmployeeNotFoundException(errorMessage);
		}
		
		String[] nullPropertyNames = StringUtils.getNullPropertyNames(employee);
		BeanUtils.copyProperties(employee, dbEmployee, nullPropertyNames);
		
		employeeRepository.update(new Employee(dbEmployee.getId(), dbEmployee.getFullName(), dbEmployee.getAge(), dbEmployee.getSalary()));
		
		String successMessage = messageSource.getMessage("success.data.updated",
				new String[] { null }, LocaleContextHolder.getLocale());
		return new EmployeeDataResponseDTO(true, null, successMessage);
	}
	
	@Override
	public EmployeeDataResponseDTO delete(Long id) throws Exception {
		logger.info("Deleting employee: " +id);
		employeeRepository.delete(id);
		String successMessage = messageSource.getMessage("success.data.deleted",
				new String[] { null }, LocaleContextHolder.getLocale());
		return new EmployeeDataResponseDTO(true, null, successMessage);
	}

	@Override
	public EmployeeDataResponseDTO filterByAge(SearchCriteria criteria) throws Exception {
		criteria.setKey("age");
		
		logger.info("Aplying: " +criteria);
		Iterable<Employee> employees = jpaRepository.findAll(buildEmployeeSpecification(criteria));

		List<EmployeeDTO> data = new ArrayList<>();
		employees.forEach(employee -> data.add(
				new EmployeeDTO(employee.getId(), employee.getFullName(), employee.getAge(), employee.getSalary())));
		return new EmployeeDataResponseDTO(true, data);
	}
	
	@Override
	public EmployeeDataResponseDTO refreshCache() throws Exception {
		logger.info("Refreshing cache");
		employeeRepository.refreshCache();
	    Set<Employee> employees = employeeRepository.get();
		jpaRepository.saveAll(employees);
		
		String successMessage = messageSource.getMessage("success.data.refreshed",
				new String[] { null }, LocaleContextHolder.getLocale());
		return new EmployeeDataResponseDTO(true, null, successMessage);
	}
	
	private static Specification<Employee> buildEmployeeSpecification(SearchCriteria criteria){
		EmployeeSpecificationBuilder builder = new EmployeeSpecificationBuilder();
		builder.with(criteria);
		return builder.build();
	}
}
