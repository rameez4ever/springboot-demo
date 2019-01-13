package com.raydar.repository;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raydar.domain.Employee;
import com.raydar.exception.EmployeeNotFoundException;
import com.raydar.util.JsonUtil;
import com.raydar.util.StringUtils;

/**
 * @author Rameez.shikalgar
 * JSON Repository- Responsible for JSON CRUD operations
 */
@Repository
public class EmployeeJsonRepositoryImpl implements IEmployeeJsonRepository {
	
	private static Logger logger = LoggerFactory.getLogger(EmployeeJsonRepositoryImpl.class.getName());

	@Autowired
	MessageSource messageSource;
	
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
		
		logger.debug("Employee data json:" + existingEmployeesStr);
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
		
		logger.debug("Employee data json:" + existingEmployeesStr);
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
			String errorMessage = messageSource.getMessage("error.employee.duplicate",
					new String[] { employee.getId().toString() }, LocaleContextHolder.getLocale());
			throw new Exception(errorMessage);
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
		
		checkAndRemoveExistingEmplyee(employee.getId(), array);
		
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

		checkAndRemoveExistingEmplyee(id, array);

		JsonUtil.writeJsonFile(file, array.toString());
		jpaRepository.delete(new Employee(id));
	}

	private void checkAndRemoveExistingEmplyee(Long id, JSONArray array) throws JSONException {
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
			String errorMessage = messageSource.getMessage("error.employee.notfound",
					new String[] { id.toString() }, LocaleContextHolder.getLocale());
			throw new EmployeeNotFoundException(errorMessage);
		}
	}

	@Override
	@CacheEvict(value="employeeMap", allEntries=true)
	public void refreshCache() {
		// This method just refreshes employee cache if anything added externally. 
	}
}
