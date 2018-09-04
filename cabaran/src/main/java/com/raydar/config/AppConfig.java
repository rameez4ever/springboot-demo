package com.raydar.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan
@EnableCaching
@Configuration
public class AppConfig {

	@Autowired
	private Environment environment;
	
	@PostConstruct
	public void init() throws IOException{
		File file = file();
		file.getParentFile().mkdirs();
		file.createNewFile();
	}
	
	@Bean
	public File file() throws IOException{
		File file = new File(environment.getProperty("app.filedir") + "/" + environment.getProperty("app.filename"));
		return file;
	}
	
	@Bean
    public CacheManager cacheManager() {
        // configure and return an implementation of Spring's CacheManager SPI
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        
        ConcurrentMapCache employeeMap = new ConcurrentMapCache("employeeMap");
        List<ConcurrentMapCache> cacheList = new ArrayList<ConcurrentMapCache>();
        cacheList.add(employeeMap);
        cacheManager.setCaches(cacheList);
        return cacheManager;
	}
}
