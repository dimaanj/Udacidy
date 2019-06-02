package by.epam.dmitriytomashevich.javatr.courses.db.dao;

import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ConferenceDaoTest{
    @Mock
    private ConferenceDao conferenceDao;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() throws DAOException {
        Conference conference = Mockito.mock(Conference.class);

        Mockito.when(conferenceDao.create(conference)).thenReturn(1L)   ;

        assertEquals(1L, java.util.Optional.ofNullable(conference.getId()));
    }
}