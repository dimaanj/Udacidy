package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.Message;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.UserRole;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationService;
import by.epam.dmitriytomashevich.javatr.courses.logic.MessageService;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ConversationServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.MessageServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.UserServiceImpl;

import java.util.*;

public class AdminCommand implements Command {
    private static final ConversationService CONVERSATION_SERVICE = new ConversationServiceImpl();
    private static final MessageService MESSAGE_SERVICE = new MessageServiceImpl();
    private static final UserService USER_SERVICE = new UserServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        List<Conversation> conversationList = CONVERSATION_SERVICE.getAllConversations();


        List<Map.Entry<User, Conversation>> map = new ArrayList<>();
        for (Conversation c : conversationList) {
            Message m = MESSAGE_SERVICE.getLastOnTimeByConversationId(c.getId());
            if (m != null) {
                List<User> conversationMembers = USER_SERVICE.findAllByConversationId(c.getId());
                for (User u : conversationMembers) {
                    if (u.getRole().equals(UserRole.USER)) {
                        map.add(new AbstractMap.SimpleEntry(u, c));
                        break;
                    }
                }
            }
        }
        content.setRequestAttribute("askingUsersWithTheirsConversations", map);

        return Optional.of(JSP.ADMIN);
    }
}
