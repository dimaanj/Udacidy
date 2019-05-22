package by.epam.dmitriytomashevich.javatr.courses.util.converter;

public interface EntityConverter<T, E> {
    T convert(E entity);
}
