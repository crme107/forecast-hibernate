package org.example.hibernate.Dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    Optional<T> getById(long id);
    Optional<T> getByCode(String code);
    List<T> getAll();
    void save(T t);
    void update(T t);
    void delete(T t);
}