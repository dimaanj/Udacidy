package by.epam.dmitriytomashevich.javatr.courses.db.builder;

import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;

import java.sql.ResultSet;

public interface EntityBuilder<T> {
    T build(ResultSet resultSet) throws DAOException;
}
