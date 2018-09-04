package com.raydar.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private FilterOperation operator;
    private Object value;
    private SortOperation sort;
}
