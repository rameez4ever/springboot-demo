package com.raydar.repository;

import java.util.Set;

import com.raydar.domain.Employee;

public interface IEmployeeJsonRepository {

	public Set<Employee> get() throws Exception;
	public void save(Employee employee) throws Exception;
	public void update(Employee employee) throws Exception;
	public void delete(Long id) throws Exception;
	public void refreshCache();
}
