package by.epam.dmitriytomashevich.javatr.courses.constant;

public class ActionNames {
    /**
     * Paths
     */
    public static final String GREETING = "/WEB-INF/greeting.jsp";
    public static final String REGISTRATION = "/WEB-INF/registration.jsp";
    public static final String LOGIN = "/WEB-INF/login.jsp";
    public static final String ERROR = "/WEB-INF/error-page.jsp";
    public static final String PAGE_404 = "/WEB-INF/404-page.jsp";
    public static final String MESSAGES = "/WEB-INF/messages.jsp";
    public static final String ADMIN = "/WEB-INF/admin-page.jsp";
    public static final String MAIN = "/WEB-INF/main.jsp";
    public static final String ADD_NEW_COURSE = "/WEB-INF/addNewConference.jsp";
    public static final String PROFILE = "/WEB-INF/user-page.jsp";
    public static final String CONTENT_EDITING = "/WEB-INF/contentEditing.jsp";
    public static final String CONFERENCES = "/WEB-INF/conferences.jsp";
    public static final String CONFERENCE = "/WEB-INF/conferenceDetailsPage.jsp";

    /**
     * Request URI
     */
    public static final String GREETING_ACTION = "/";
    public static final String LOGIN_ACTION = "/login";
    public static final String REGISTRATION_ACTION = "/registration";
    public static final String PAGE_NOT_FOUND_ACTION = "/404";
    public static final String HELP_ACTION = "/help";   //mapping for users
    public static final String ADMIN_CONVERSATION_ACTION = "/conversation?conversationId="; //mapping for admins
    public static final String ERROR_ACTION = "/error";
    public static final String ADMIN_ACTION = "/admin";
    public static final String MAIN_ACTION = "/main";
    public static final String CONTENT_EDITING_ACTION = "/contentediting";
    public static final String CONFERENCES_ACTION = "/conferences";
    public static final String PROFILE_ACTION = "/profile";


}
