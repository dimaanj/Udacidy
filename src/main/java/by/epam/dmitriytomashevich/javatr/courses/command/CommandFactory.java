package by.epam.dmitriytomashevich.javatr.courses.command;

import by.epam.dmitriytomashevich.javatr.courses.command.admin.*;
import by.epam.dmitriytomashevich.javatr.courses.command.conversation.LoadMessagesCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.conversation.SendMessageCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.conversation.UpdateMessagesCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.conversation.ViewMoreCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.error.PageNotFoundCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.main.GreetingCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.main.ViewMoreConferencesCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.profile.LoginCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.profile.LogoutCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.profile.UserProfileCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.profile.RegistrationCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.user.*;
import by.epam.dmitriytomashevich.javatr.courses.constant.CommandNames;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.util.validator.UriValidator;

import java.util.*;

public class CommandFactory {
    private final Set<Map.Entry<String, Command>> commands = new HashSet<>();

    public CommandFactory(ServiceFactory serviceFactory) {
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.GREETING, new GreetingCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.REGISTRATION, new RegistrationCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.LOGIN, new LoginCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.LOG_OUT, new LogoutCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.HELP, new HelpCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.REMOVE_REQUEST, new RemoveRequestCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.VIEW_MORE, new ViewMoreCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.UPLOAD_MESSAGES, new UpdateMessagesCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.LOAD_MESSAGES, new LoadMessagesCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.AJAX_SEND_MESSAGE, new SendMessageCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.VIEW_MORE_CONFERENCES, new ViewMoreConferencesCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.USER_PAGE, new UserProfileCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.ADMIN_PAGE, new AdminPageCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.ADMIN_CONVERSATION, new AdminConversationCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.ADD_CONFERENCE, new CreateConferencePage()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.CREATE_CONFERENCE, new CreateConferenceCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.EDIT_CONFERENCE_CONTENT, new EditConferenceContentCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.DELETE_CONFERENCE, new RemoveConferenceCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.PREVIEW_COURSE_CONTENT, new PreviewCourseContent()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.PAGE_NOT_FOUND, new PageNotFoundCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.CONTENT_EDITING, new ContentEditingCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.CONFERENCES, new ConferencesCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.SEND_CLIENT_REQUEST, new SendClientRequestCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.CHANGE_PASSWORD, new ChangePasswordCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.GET_CONFERENCE_CONTENT, new GetConferenceContentCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.GET_CONFERENCES, new ConferencesCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.REMOVE_QUESTION_CONVERSATION, new RemoveQuestionConversationCommand(serviceFactory)));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.HELP_USER, new HelpCommand(serviceFactory)));
    }

    /**
     * package-private
     *
     * @param commandStringKey
     * @return
     */
    Optional<Command> createCommand(String commandStringKey) {
        return commands.stream()
                .filter(
                        commandEntry -> UriValidator.isURICorrespondsToCommand(commandEntry.getKey(), commandStringKey)
                )
                .map(Map.Entry::getValue)
                .findFirst();
    }
}
