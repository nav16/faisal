package com.example.faisal.configs;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class HikariCPConfig {

    @Bean("masterDataSource")
    @ConfigurationProperties("spring.datasource.hikari.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
