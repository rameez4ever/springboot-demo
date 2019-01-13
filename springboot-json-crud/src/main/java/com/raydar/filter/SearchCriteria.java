package com.raydar.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Rameez.shikalgar
 * 
 */
@Data
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private FilterOperation operator;
    private Object value;
    private SortOperation sort;
    
    @Override
	public String toString() {
		return "SearchCriteria{" + "key=" + key + ", operator='" + operator + '\'' + ", value='" + value + '\'' + ", sort='"
				+ sort+ '\'' + '}';
	}
}
