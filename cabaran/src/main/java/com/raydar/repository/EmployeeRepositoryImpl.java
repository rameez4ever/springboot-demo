package com.raydar.repository;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raydar.domain.Employee;
import com.raydar.exception.EmployeeNotFoundException;
import com.raydar.util.JsonUtil;
import com.raydar.util.StringUtils;

@Repository
public class EmployeeRepositoryImpl implements IEmployeeRepository {

	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private File file;
	
	@Autowired
	private EmployeeJpaRepository jpaRepository;
	
	@Override
	public Set<Employee> get() throws Exception {
		
		TypeReference<Set<Employee>> typeReference = new TypeReference<Set<Employee>>(){};
		
		String existingEmployeesStr = JsonUtil.getStringFromFile(file);
		if(StringUtils.isNullOrEmpty(existingEmployeesStr)){
			return null;
		}
		Set<Employee> employees = mapper.readValue(existingEmployeesStr,typeReference);
		return employees;
	}

	@Override
	@CacheEvict(value="employeeMap", allEntries=true)
	public void save(Employee employee) throws Exception {
		Set<Employee> employees = new HashSet<>();
		JSONArray array = new JSONArray();
		TypeReference<Set<Employee>> typeReference = new TypeReference<Set<Employee>>(){};
		
		String existingEmployeesStr = JsonUtil.getStringFromFile(file);
		if(existingEmployeesStr != null && !existingEmployeesStr.isEmpty()){
			employees = mapper.readValue(existingEmployeesStr,typeReference);
			array = new JSONArray(existingEmployeesStr);
		}
		
		if(employees.add(employee)){
			JSONObject object = new JSONObject(mapper.writeValueAsString(employee));
			array.put(object);
			
			JsonUtil.writeJsonFile(file, array.toString());
			
			jpaRepository.save(employee);
		} else {
			throw new Exception("Employee with same id present");
		}
	}

	@Override
	@CacheEvict(value="employeeMap", allEntries=true)
	public void update(Employee employee) throws Exception {
		JSONArray array = new JSONArray();
		
		String existingEmployeesStr = JsonUtil.getStringFromFile(file);
		if(existingEmployeesStr != null && !existingEmployeesStr.isEmpty()){
			array = new JSONArray(existingEmployeesStr);
		}
		
		for(int i = 0; i < array.length(); i++){
			JSONObject jsonObject = array.getJSONObject(i);
			Object idObject = jsonObject.get(StringUtils.findIdField(Employee.class));
			Long employeeId = Long.valueOf(idObject.toString()) ;
			if(employeeId.equals(employee.getId())){
				array.remove(i);
				break;
			}
		}
		JSONObject object = new JSONObject(mapper.writeValueAsString(employee));
		array.put(object);

		JsonUtil.writeJsonFile(file, array.toString());
		jpaRepository.save(employee);
	}

	@Override
	@CacheEvict(value="employeeMap", allEntries=true)
	public void delete(Long id) throws Exception {
		JSONArray array = new JSONArray();

		String existingEmployeesStr = JsonUtil.getStringFromFile(file);
		if (existingEmployeesStr != null && !existingEmployeesStr.isEmpty()) {
			array = new JSONArray(existingEmployeesStr);
		}

		boolean isExist = false;
		for (int i = 0; i < array.length(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Object idObject = jsonObject.get(StringUtils.findIdField(Employee.class));
			Long employeeId = Long.valueOf(idObject.toString());
			if (employeeId.equals(id)) {
				array.remove(i);
				isExist = true;
				break;
			}
		}
		
		if(!isExist){
			throw new EmployeeNotFoundException("No employee found with id: " + id);
		}

		JsonUtil.writeJsonFile(file, array.toString());
		jpaRepository.delete(new Employee(id));
	}

	@Override
	@CacheEvict(value="employeeMap", allEntries=true)
	public void refreshCache() {
		// This method just refreshes employee cache if anything added externally. 
	}
}
