package com.raydar.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.raydar.domain.Employee;

/**
 * @author Rameez.shikalgar
 * Employee Specification Builder- Responsible to build specification for Employee Entity as per Search Criteria
 */
public class EmployeeSpecificationBuilder {
	private final List<SearchCriteria> params;

	public EmployeeSpecificationBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

	public EmployeeSpecificationBuilder with(SearchCriteria criteria) {
		params.add(criteria);
		return this;
	}

	public Specification<Employee> build() {
		if (params.size() == 0) {
			return null;
		}

		List<Specification<Employee>> specs = new ArrayList<Specification<Employee>>();
		for (SearchCriteria param : params) {
			specs.add(new EmployeeSpecification(param));
		}

		Specification<Employee> result = specs.get(0);
		for (int i = 1; i < specs.size(); i++) {
			result = Specification.where(result).and(specs.get(i));
		}
		return result;
	}
}
