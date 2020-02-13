package com.excilys.computerDatabase.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
 
@Configuration
@ComponentScan(basePackages = {"com.excilys.computerDatabase.daos","com.excilys.computerDatabase.services",
								"com.excilys.computerDatabase.servlets",})
@PropertySource(value = "classpath:datasource.properties")
public class SpringConfiguration implements  WebApplicationInitializer {
	
    @Autowired
    private Environment environnement;
 
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environnement.getRequiredProperty("dataSource.driverClassName"));
        dataSource.setUrl(environnement.getRequiredProperty("dataSource.jdbcUrl"));
        dataSource.setUsername(environnement.getRequiredProperty("dataSource.username"));
        dataSource.setPassword(environnement.getRequiredProperty("dataSource.password"));
        return dataSource;
    }
 
    @Bean
    public NamedParameterJdbcTemplate namedParameterjdbcTemplate(DataSource dataSource) {
    	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return namedParameterJdbcTemplate;
    }
  
 


    @Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.register(SpringConfiguration.class,SpringMVCConfiguration.class);
		webContext.setServletContext(servletContext);
		ServletRegistration.Dynamic servlet = servletContext.addServlet("dynamicServlet", new DispatcherServlet(webContext));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	}
}