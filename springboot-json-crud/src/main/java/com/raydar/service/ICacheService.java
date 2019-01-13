package com.raydar.service;

import java.util.Map;

import com.raydar.dto.EmployeeDTO;

public interface ICacheService {

	Map<Long, EmployeeDTO> getEmployeeMap() throws Exception;
}
