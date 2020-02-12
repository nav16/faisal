package com.example.faisal.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface Base<T, ID extends Serializable> extends JpaRepository<T, ID> {

    <S extends T> S vSave(S entity);
}
