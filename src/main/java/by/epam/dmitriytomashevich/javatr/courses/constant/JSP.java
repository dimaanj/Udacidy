package by.epam.dmitriytomashevich.javatr.courses.constant;

public class JSP {
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

    /**
     * Request URI
     */
    public static final String GREETING_ACTION = "/udacidy/";
    public static final String LOGIN_ACTION = "/udacidy/login";
    public static final String REGISTRATION_ACTION = "/udacidy/registration";
    public static final String PAGE_NOT_FOUND_ACTION = "/udacidy/404";
    public static final String HELP_ACTION = "/udacidy/help";   //mapping for users
    public static final String ADMIN_CONVERSATION_ACTION = "/udacidy/conversation?conversationId="; //mapping for admins
    public static final String ERROR_ACTION = "/udacidy/error";
    public static final String ADMIN_ACTION = "/udacidy/admin";
    public static final String MAIN_ACTION = "/udacidy/main";


}
