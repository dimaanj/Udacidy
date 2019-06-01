package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationGroupService;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationService;
import by.epam.dmitriytomashevich.javatr.courses.logic.MessageService;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ConversationGroupServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ConversationServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.MessageServiceImpl;

import java.util.Optional;

public class RemoveQuestionConversationCommand implements Command {
    private final MessageService messageService = new MessageServiceImpl();
    private final ConversationService conversationService = new ConversationServiceImpl();
    private final ConversationGroupService conversationGroupService = new ConversationGroupServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        String conversationIdAsString = content.getParameter("conversationId");
        if(conversationIdAsString != null){
            long convId = Long.parseLong(conversationIdAsString);
            messageService.removeAllByConversationId(convId);
            conversationGroupService.deleteByConversationId(convId);
            conversationService.deleteById(convId);
        }

        return Optional.empty();
    }
}
