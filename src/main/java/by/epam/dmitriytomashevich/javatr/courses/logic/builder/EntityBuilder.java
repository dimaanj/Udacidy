package by.epam.dmitriytomashevich.javatr.courses.logic.builder;

import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;

import java.sql.ResultSet;

public interface EntityBuilder<T> {
    T build(ResultSet resultSet) throws DAOException;
}
