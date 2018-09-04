package com.raydar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.raydar.service.ICacheService;

@Component
public class SpringEventsListener {

    @Autowired
    ICacheService cacheService;

    @EventListener
    public void onApplicationReady(ContextRefreshedEvent event) throws Exception {
    	cacheService.getEmployeeMap();
    }
}
