package com.raydar.util;

import java.beans.FeatureDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.stream.Stream;

import javax.persistence.Id;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class StringUtils {
	
	public static boolean isNullOrEmpty(String string) { 
        return string == null || string.trim().length() == 0;
    }
    
    public static boolean isNotNullAndNotEmpty(String string) { 
        return !isNullOrEmpty(string);
    }
    
    public static boolean isNotNull(String string) { 
        return null != string;
    }
    
    public static boolean isNumeric(String s) {
		return s != null ? s.matches("[-+]?\\d*\\.?\\d+") : false;
	}
    
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
    
    public static String findIdField(Class cls) {
        for(Field field : cls.getDeclaredFields()){
            Class type = field.getType();
            String name = field.getName();
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (int i = 0; i < annotations.length; i++) {
                if (annotations[i].annotationType().equals(Id.class)) {
                    return name;
                }
            }
        }
        return null;
    }
}
