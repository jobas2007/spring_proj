package com.example.demo.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * Configuration class to connect to application database.
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.example.demo.repository" })
public class AppConnectionConfig {

	@Autowired
	JpaVendorAdapter jpaVendorAdapter;

	@Bean(name = "appDataSource")
	@ConfigurationProperties("spring.app.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	public EntityManager entityManager() {
		return entityManagerFactory().createEntityManager();
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan("com.example.demo.model");
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactoryBean.afterPropertiesSet();
		return entityManagerFactoryBean.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {

		return new JpaTransactionManager(entityManagerFactory());
	}
}
