package com.boot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PersistenceConfiguration {
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	@Primary
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	   public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	   }
}
