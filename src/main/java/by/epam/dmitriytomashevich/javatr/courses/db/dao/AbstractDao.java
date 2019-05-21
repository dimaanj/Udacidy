package by.epam.dmitriytomashevich.javatr.courses.db.dao;

import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;

import java.util.List;

public interface AbstractDao<K, T> {
    List<T> findAll() throws DAOException;
    T findById(K id) throws DAOException;
    void deleteById(K id) throws DAOException;
    K create(T entity) throws DAOException;
    void update(T entity) throws DAOException;
}
