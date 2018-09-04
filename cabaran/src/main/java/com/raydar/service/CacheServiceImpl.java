package com.raydar.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.raydar.domain.Employee;
import com.raydar.dto.EmployeeDTO;
import com.raydar.repository.EmployeeJpaRepository;
import com.raydar.repository.IEmployeeRepository;

@Service
public class CacheServiceImpl implements ICacheService {
	
	@Autowired
	private IEmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeJpaRepository employeeJpaRepository;

	@Override
	@Cacheable(value = "employeeMap", key = "#root.methodName")
	public Map<Long, EmployeeDTO> getEmployeeMap() throws Exception {

		Set<Employee> employeeList = employeeRepository.get();
		
		Map<Long, EmployeeDTO> employeeMap = new HashMap<>();
		if (null != employeeList) {
			employeeList.forEach(employee -> employeeMap.put(employee.getId(), new EmployeeDTO(employee.getId(), employee.getFullName(),
					employee.getAge(), employee.getSalary())));

			employeeJpaRepository.saveAll(employeeList);
			return employeeMap;
		}
		return null;
	}
}
