package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.ConversationGroup;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import javafx.fxml.LoadException;

public interface ConversationGroupService {
    Long add(ConversationGroup conversationGroup) throws LogicException;
    boolean isConversationGroupCreatedForUser(User user);
    ConversationGroup defineConversationGroup(Long userId, Long conversationId);
//    boolean isUserInConversationGroup(User user, Long conversationId) throws LoadException;

}
