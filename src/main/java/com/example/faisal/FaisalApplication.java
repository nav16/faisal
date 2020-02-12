package com.example.faisal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Slf4j
@EnableJpaRepositories(basePackages = {"com.example.faisal.repositories"})
@EnableTransactionManagement
@PropertySource(value = "${spring.config.location:classpath}:application-${spring.profiles.active}.properties",
        ignoreResourceNotFound = true)
public class FaisalApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaisalApplication.class, args);
    }

}
