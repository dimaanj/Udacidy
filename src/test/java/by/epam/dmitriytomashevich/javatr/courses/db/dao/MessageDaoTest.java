package by.epam.dmitriytomashevich.javatr.courses.db.dao;

import by.epam.dmitriytomashevich.javatr.courses.domain.Message;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.Assert.*;

public class MessageDaoTest {
    private static MessageDao dao = new MessageDao();

    @Test
    public void testFindLastOnTimeByConversationId() throws DAOException {
        Message expected = new Message();
        expected.setId(1382L);

        Message actual = dao.findLastOnTimeByConversationId(39L);

        assertEquals(actual.getId(), expected.getId());
    }

    @Test
    public void testFindEarliestMessageByConversationId() throws DAOException {
        Message expected = new Message();
        expected.setId(1267L);

        Message actual = dao.findEarliestMessageByConversationId(39L);

        assertEquals(actual.getId(), expected.getId());
    }
}