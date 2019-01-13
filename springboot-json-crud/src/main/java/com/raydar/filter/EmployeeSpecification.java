package com.raydar.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.raydar.domain.Employee;

/**
 * @author Rameez.shikalgar 
 * EmployeeSpecification- This class will transform
 * FilterOperations and SortOperations to actual Hibernate criteria queries to filter and sort records
 */
public class EmployeeSpecification implements Specification<Employee> {

	private SearchCriteria criteria;
	
	public EmployeeSpecification(SearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		
		if(criteria.getSort() != null){
			if(criteria.getSort().equals(SortOperation.asc)){
				query.orderBy(builder.asc(root.get(criteria.getKey())));
			} else if(criteria.getSort().equals(SortOperation.desc)){
				query.orderBy(builder.desc(root.get(criteria.getKey())));
			}
		}
		if(criteria.getOperator() != null){
			if (criteria.getOperator().equals(FilterOperation.gt)) {
				return builder.greaterThan(root.<String>get(criteria.getKey()), criteria.getValue().toString());
			} else if (criteria.getOperator().equals(FilterOperation.gte)) {
				return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
			} else if (criteria.getOperator().equals(FilterOperation.lt)) {
				return builder.lessThan(root.<String>get(criteria.getKey()), criteria.getValue().toString());
			} else if (criteria.getOperator().equals(FilterOperation.lte)) {
				return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
			} else if (criteria.getOperator().equals(FilterOperation.eq)) {
				if (root.get(criteria.getKey()).getJavaType() == String.class) {
					return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
				} else {
					return builder.equal(root.get(criteria.getKey()), criteria.getValue());
				}
			} else if (criteria.getOperator().equals(FilterOperation.ne)) {
				return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
			}
		}
		return null;
	}
}
