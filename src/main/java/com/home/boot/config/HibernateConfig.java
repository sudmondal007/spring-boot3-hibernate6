package com.home.boot.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.annotation.Resource;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
	
	private static final int MAX_POOL_SIZE = 5;
	private static final int MAX_POOL_SIZE_CRSP = 2;
	private static final int MAX_LIFE_TIME = 30000;
	private static final int CONN_TIME_OUT = 60000;
	
	@Resource
	public org.springframework.core.env.Environment env;
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSourceProperties applicationDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	public DataSource dataSource(@Qualifier("applicationDataSourceProperties")DataSourceProperties applicationDataSourceProperties) {
		//final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		//dataSource.setUrl(env.getProperty("spring.datasource.url"));
		//dataSource.setUsername(env.getProperty("spring.datasource.username"));
		//dataSource.setPassword(env.getProperty("spring.datasource.password"));
		//return dataSource;
		
		HikariDataSource hikariDataSource = applicationDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
		hikariDataSource.setMaximumPoolSize(MAX_POOL_SIZE);
		hikariDataSource.setMaxLifetime(MAX_LIFE_TIME);
		hikariDataSource.setConnectionTimeout(CONN_TIME_OUT);
		return hikariDataSource;
	}
	
	private Properties getJPAProperties() {
		Properties properties = new Properties();
		properties.setProperty(Environment.DIALECT, env.getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.setProperty(Environment.HBM2DDL_AUTO, env.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.setProperty(Environment.SHOW_SQL, env.getProperty("spring.jpa.properties.hibernate.show_sql"));
		properties.setProperty(Environment.FORMAT_SQL, env.getProperty("spring.jpa.properties.hibernate.format_sql"));
		properties.setProperty(Environment.USE_SQL_COMMENTS, env.getProperty("spring.jpa.properties.hibernate.use_sql_comments"));
		//properties.setProperty(Environment.USE_SECOND_LEVEL_CACHE, env.getProperty("spring.jpa.properties.hibernate.cache.use_second_level_cache"));
		//properties.setProperty(Environment.USE_QUERY_CACHE, env.getProperty("spring.jpa.properties.hibernate.cache.use_query_cache"));
		//properties.setProperty(Environment.CACHE_REGION_FACTORY, env.getProperty("spring.jpa.properties.hibernate.cache.region.factory_class"));
		properties.setProperty(Environment.JPA_SHARED_CACHE_MODE, env.getProperty("spring.jpa.properties.javax.persistence.sharedCache.mode"));

		return properties;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("applicationDataSourceProperties")DataSourceProperties applicationDataSourceProperties) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource(applicationDataSourceProperties));
		em.setPackagesToScan(new String[] { "com.home.boot.model" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		
		em.setJpaProperties(getJPAProperties());

		return em;
	}
}
