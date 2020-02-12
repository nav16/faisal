package com.example.faisal;

import com.example.faisal.repositories.impl.BaseImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.ValidatorFactory;

@SpringBootApplication
@Slf4j
@EnableJpaRepositories(basePackages = {"com.example.faisal.repositories"}, repositoryBaseClass = BaseImpl.class)
@EnableTransactionManagement
@EnableWebMvc
@PropertySource(value = "${spring.config.location:classpath}:application-${spring.profiles.active}.properties",
        ignoreResourceNotFound = true)
public class FaisalApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaisalApplication.class, args);
    }

    @Bean
    public ValidatorFactory validator() {
        return new LocalValidatorFactoryBean();
    }
}
