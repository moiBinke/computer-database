package configuration;

import javax.sql.DataSource;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
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
public class SpringConfiguration extends AbstractContextLoaderInitializer {
	
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
	 protected WebApplicationContext createRootApplicationContext() {
	 AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
	 rootContext.register(SpringConfiguration.class);
	 return rootContext;
	 }
}

