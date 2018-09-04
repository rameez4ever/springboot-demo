package com.raydar.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.raydar.domain.Employee;

@Repository
public interface EmployeeJpaRepository extends CrudRepository<Employee, Long>, JpaSpecificationExecutor<Employee>{

}
