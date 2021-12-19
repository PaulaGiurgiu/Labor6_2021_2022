package com.uni.labor6_2021_2022_javafx.repository;

import java.util.List;

public interface CrudRepository<T> {
    T findOne(Long id);
    List<T> findAll();
    T save(T entity);
    void delete(T entity);
    T update(T entity);

}
