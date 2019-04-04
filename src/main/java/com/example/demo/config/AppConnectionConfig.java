package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;
import org.springframework.cloud.task.configuration.TaskConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * Configuration class to connect to application database.
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = { "com.example.demo.repository" })
public class AppConnectionConfig {

	@Primary
	@Bean(name="appDataSource")
	@ConfigurationProperties("spring.app.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean
	public TaskConfigurer taskConfigurer() {
		return new DefaultTaskConfigurer(dataSource());
	}
}
