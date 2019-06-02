package by.epam.dmitriytomashevich.javatr.courses.command;

import by.epam.dmitriytomashevich.javatr.courses.command.admin.*;
import by.epam.dmitriytomashevich.javatr.courses.command.conversation.LoadMessagesCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.conversation.SendMessageCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.conversation.UpdateMessagesCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.conversation.ViewMoreCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.error.PageNotFoundCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.main.ChangeLocaleCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.main.GetPageContentCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.main.GreetingCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.main.MainCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.profile.*;
import by.epam.dmitriytomashevich.javatr.courses.command.user.*;
import by.epam.dmitriytomashevich.javatr.courses.constant.CommandNames;
import by.epam.dmitriytomashevich.javatr.courses.util.validation.CommandValidator;

import java.util.*;

public class CommandFactory {
    private final Set<Map.Entry<String, Command>> commands = new HashSet<>();

    public CommandFactory() {
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.GREETING, new GreetingCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.REGISTRATION, new RegistrationCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.LOGIN, new LoginCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.LOG_OUT, new LogoutCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.HELP, new HelpCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.REMOVE_REQUEST, new RemoveRequestCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.VIEW_MORE, new ViewMoreCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.UPLOAD_MESSAGES, new UpdateMessagesCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.LOAD_MESSAGES, new LoadMessagesCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.AJAX_SEND_MESSAGE, new SendMessageCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.USER_PAGE, new ProfileCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.ADMIN_PAGE, new AdminPageCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.ADMIN_CONVERSATION, new AdminConversationCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.ADD_CONFERENCE, new CreateConferencePage()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.CREATE_CONFERENCE, new CreateConferenceCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.EDIT_CONFERENCE_CONTENT, new EditConferenceContentCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.DELETE_CONFERENCE, new RemoveConferenceCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.PREVIEW_COURSE_CONTENT, new PreviewCourseContent()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.PAGE_NOT_FOUND, new PageNotFoundCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.CONTENT_EDITING, new MainCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.CONFERENCES, new MainCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.SEND_CLIENT_REQUEST, new SendClientRequestCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.CHANGE_PASSWORD, new ChangePasswordCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.GET_CONFERENCE_CONTENT, new GetConferenceContentCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.REMOVE_QUESTION_CONVERSATION, new RemoveQuestionConversationCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.ACCEPT_USER_REQUEST, new AcceptRequestCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.GET_PAGE_CONTENT, new GetPageContentCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.REJECT_USER_REQUEST, new RejectRequestCommand()));
        commands.add(new AbstractMap.SimpleEntry<>(CommandNames.CHANGE_LOCALE, new ChangeLocaleCommand()));
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
                        commandEntry -> CommandValidator.isCommandEqualsToString(commandEntry.getKey(), commandStringKey)
                )
                .map(Map.Entry::getValue)
                .findFirst();
    }
}
