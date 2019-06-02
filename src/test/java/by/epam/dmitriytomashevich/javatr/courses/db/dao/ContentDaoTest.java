package by.epam.dmitriytomashevich.javatr.courses.db.dao;

import by.epam.dmitriytomashevich.javatr.courses.domain.Content;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ContentDaoTest {
    private static ContentDao contentDao = new ContentDao();

    @Test
    public void testFindById() throws DAOException {
        Content expected = new Content();
        expected.setContent("New theory, novel phenomena and advanced experimental technique");
        expected.setId(916L);

        Content actual = contentDao.findById(916L);

        assertEquals(actual, expected);
    }

    @Test
    public void testFindBySectionId() throws DAOException {
        Content expected = new Content();
        expected.setContent("New theory, novel phenomena and advanced experimental technique");
        expected.setId(916L);

        Content actual = contentDao.findBySectionId(639L);
        assertEquals(actual, expected);
    }
}