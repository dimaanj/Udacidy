package by.epam.dmitriytomashevich.javatr.courses.db.builder;

import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityBuilder<T> {
    T build(ResultSet resultSet) throws DAOException, SQLException;
}
