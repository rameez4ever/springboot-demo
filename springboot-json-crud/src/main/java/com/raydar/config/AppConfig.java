package com.raydar.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * @author Rameez.shikalgar
 *
 */
@ComponentScan
@EnableCaching
@Configuration
public class AppConfig implements WebMvcConfigurer{

	@Autowired
	private Environment environment;
	
	
	/**
	 * @throws IOException
	 * This will make sure to create JSON database as configured in properties to be present.
	 */
	@PostConstruct
	public void init() throws IOException{
		File file = file();
		file.getParentFile().mkdirs();
		file.createNewFile();
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	@Bean
	public File file() throws IOException{
		File file = new File(environment.getProperty("app.filedir") + "/" + environment.getProperty("app.filename"));
		return file;
	}
	
	/**
	 * @return
	 * Holds cache for application
	 */
	@Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        
        ConcurrentMapCache employeeMap = new ConcurrentMapCache("employeeMap");
        List<ConcurrentMapCache> cacheList = new ArrayList<ConcurrentMapCache>();
        cacheList.add(employeeMap);
        cacheManager.setCaches(cacheList);
        return cacheManager;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(Locale.US);
	    return slr;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("lang");
	    return lci;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(localeChangeInterceptor());
	}
}
