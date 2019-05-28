package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.domain.*;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AdminPageCommand implements Command {
    private final ConversationService conversationService;
    private final MessageService messageService;
    private final UserService userService;
    private final ConferenceService conferenceService;
    private final RequestService requestService;

    public AdminPageCommand(ServiceFactory serviceFactory) {
        conversationService = serviceFactory.createConversationService();
        messageService = serviceFactory.createMessageService();
        userService = serviceFactory.createUserService();
        conferenceService = serviceFactory.createConferenceService();
        requestService = serviceFactory.createRequestService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        List<Conversation> conversationList = conversationService
                .findAllByType(Conversation.ConversationType.QUESTION_CONVERSATION);
        List<Map.Entry<User, Conversation>> map = new ArrayList<>();
        initUserQuestions(map, conversationList);

        List<Map.Entry<User, Conference>> usersWithConferencesRequests = findUsersWithConferencesRequests();

        content.setRequestAttribute("usersWithConferencesRequests", usersWithConferencesRequests);
        content.setRequestAttribute("askingUsersWithTheirsConversations", map);
        return Optional.of(ActionNames.ADMIN);
    }

    private void initUserQuestions(List<Map.Entry<User, Conversation>> map, List<Conversation> conversationList) throws LogicException {
        for (Conversation c : conversationList) {
            Message lastMessage = messageService.getLastOnTimeByConversationId(c.getId());
            if (lastMessage != null) {
                User creator = userService.findById(lastMessage.getCreatorId());
                lastMessage.setCreator(creator);
                c.setLastMessage(lastMessage);

                List<User> conversationMembers = userService.findAllByConversationId(c.getId());
                for (User u : conversationMembers) {
                    if (u.getRole().equals(UserRole.USER)) {
                        map.add(new AbstractMap.SimpleEntry<>(u, c));
                        break;
                    }
                }
            }
        }
    }

    private List<Map.Entry<User, Conference>> findUsersWithConferencesRequests() throws LogicException {
        Set<Request> singleUserRequests = new HashSet<>(requestService.findAll());
        return singleUserRequests.stream().map(request -> {
            User user = null;
            Conference conference = null;
            try {
                user = userService.findById(request.getUserId());
                conference = conferenceService.getById(request.getConferenceId());
            } catch (LogicException e) {
                e.printStackTrace();
            }
            return new AbstractMap.SimpleEntry<>(user, conference);
        }).collect(Collectors.toList());
    }
}
