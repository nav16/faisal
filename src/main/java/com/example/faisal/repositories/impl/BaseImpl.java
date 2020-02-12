package com.example.faisal.repositories.impl;

import com.example.faisal.configs.ApplicationContextProvider;
import com.example.faisal.repositories.Base;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.Set;

public class BaseImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements Base<T, ID> {

    private final EntityManager entityManager;

    private Validator validator;

    public BaseImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public <S extends T> S vSave(S entity) {
        validator = ApplicationContextProvider.getApplicationContext().getBean(Validator.class);
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
        return super.save(entity);
    }
}
