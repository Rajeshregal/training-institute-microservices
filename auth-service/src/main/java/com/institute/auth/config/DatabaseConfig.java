package com.institute.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Bean
    public DataSource dataSource() {
        //get dataSource
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // Set the database connection properties
        dataSource.setDriverClassName(
                "org.postgresql.Driver");
        // Update the URL, username, and password according to your PostgreSQL configuration
        dataSource.setUrl(
                "jdbc:postgresql://localhost:5432/training_institute_db");
         // Update the username and password according to your PostgreSQL configuration
        dataSource.setUsername("postgres");
        // Update the username and password according to your PostgreSQL configuration
        dataSource.setPassword("system");
        return dataSource;
    }
}