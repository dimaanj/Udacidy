package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.Message;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.UserRole;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationService;
import by.epam.dmitriytomashevich.javatr.courses.logic.MessageService;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.*;

public class AdminCommand implements Command {
    private ConversationService conversationService;
    private MessageService messageService;
    private UserService userService;

    public AdminCommand(ServiceFactory serviceFactory){
        conversationService = serviceFactory.createConversationService();
        messageService = serviceFactory.createMessageService();
        userService = serviceFactory.createUserService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        List<Conversation> conversationList = conversationService.getAllConversations();
        List<Map.Entry<User, Conversation>> map = new ArrayList<>();
        for (Conversation c : conversationList) {
            Message m = messageService.getLastOnTimeByConversationId(c.getId());
            if (m != null) {
                List<User> conversationMembers = userService.findAllByConversationId(c.getId());
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
