package by.epam.dmitriytomashevich.javatr.courses.command;

import by.epam.dmitriytomashevich.javatr.courses.command.admin.*;
import by.epam.dmitriytomashevich.javatr.courses.command.conversation.LoadMessagesCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.conversation.SendMessageCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.conversation.UpdateMessagesCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.conversation.ViewMoreCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.error.PageNotFoundCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.user.HelpCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.main.GreetingCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.main.MainCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.user.SendRequestCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.main.ViewMoreConferencesCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.profile.LoginCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.profile.LogoutCommand;
import by.epam.dmitriytomashevich.javatr.courses.command.profile.RegistrationCommand;

public enum CommandEnum {
    GREETING("/udacidy"){
        {
            this.command = new GreetingCommand();
        }
    },
    REGISTRATION("/udacidy/registration"){
        {
            this.command = new RegistrationCommand();
        }
    },
    LOGIN("/udacidy/login"){
        {
           this.command = new LoginCommand();
        }
    },

    LOG_OUT("logout"){
        {
            this.command = new LogoutCommand();
        }
    },
    HELP("/udacidy/help"){
        {
            this.command = new HelpCommand();
        }
    },
    SEND_REQUEST("sendRequest"){
        {
            this.command = new SendRequestCommand();
        }
    },
    REMOVE_REQUEST_COMMAND("removeRequest"){
        {
            command = new RemoveConferenceCommand();
        }
    },
    VIEW_MORE("viewMore"){
        {
            this.command = new ViewMoreCommand();
        }
    },
    UPLOAD_MESSAGES("/udacidy/updateMessages"){
        {
            this.command = new UpdateMessagesCommand();
        }
    },
    LOAD_MESSAGES("/udacidy/loadMessages"){
        {
            command = new LoadMessagesCommand();
        }
    },

    AJAX_SEND_MESSAGE("ajaxSendMessage"){
        {
            this.command = new SendMessageCommand();
        }
    },
    VIEW_MORE_CONFERENCES("viewMoreConferences"){
        {
            this.command = new ViewMoreConferencesCommand();
        }
    },



    ADMIN_PAGE("/udacidy/admin"){
        {
            this.command = new AdminCommand();
        }
    },
    ADMIN_CONVERSATION_COMMAND("/udacidy/conversation"){
        {
            this.command = new AdminConversationCommand();
        }
    },
    ADD_CONFERENCE("/udacidy/addconference"){
        {
            this.command = new CreateConferencePage();
        }
    },
    CREATE_CONFERENCE("createConference"){
        {
            command = new CreateConferenceCommand();
        }
    },
    EDIT_CONFERENCE_CONTENT("editConferenceContent"){
        {
            this.command = new EditConferenceContentCommand();
        }
    },
    DELETE_CONFERENCE("deleteConference"){
        {
            this.command = new RemoveConferenceCommand();
        }
    },
    PREVIEW_COURSE_CONTENT("previewCourseContent"){
        {
            command = new PreviewCourseContent();
        }
    },

    MAIN("/udacidy/main"){
        {
            this.command = new MainCommand();
        }
    },
    PAGE_NOT_FOUND("/udacidy/404"){
        {
            this.command = new PageNotFoundCommand();
        }
    };

    Command command;
    String name;

    CommandEnum(String name) {
        this.name = name;
    }

    /**
     * Custom command (only for get methods) identifier.
     * When get request - command will defined by request URI.
     * When post method - command will defined by "command" request parameter.
     * This method was invented for case: when URI ends with '/' symbol
     * @param c
     * @param uri
     * @return
     */
    public static boolean isURICorrespondsToCommand(CommandEnum c, String uri){
        return uri.equals(c.name) || uri.equals(c.name + "/") || (uri+"/").equals(c.name);
    }

    public Command getCommand(){
        return command;
    }

    public String getName() {
        return name;
    }

    public static CommandEnum fromValue(String v) throws IllegalArgumentException{
        for (CommandEnum c: CommandEnum.values()) {
            if (isURICorrespondsToCommand(c, v) || c.name.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
