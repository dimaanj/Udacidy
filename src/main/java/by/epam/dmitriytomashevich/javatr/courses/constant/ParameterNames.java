package by.epam.dmitriytomashevich.javatr.courses.constant;

public class ParameterNames {
    /**
     * Method names
     */
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    /**
     * General commands
     */
    public static final String COMMAND = "command";
    public static final String LOCALE = "locale";

    /**
     * Image paths
     */
    public static final String STATIC_IMAGES_PATH ="/images";
    public static final String TMP_IMAGES_PATH = "/images/tmp";

    /**
     * Session attributes
     */
    public static final String USER = "user";

    /**
     * Request attributes names
     */
    public static final String ERROR_LOGIN_PASS_MESSAGE = "errorLoginPassMessage";
    public static final String ERROR_REGISTRATION_PASS_MESSAGE = "errorRegistrationPassMessage";
    public static final String IS_CONFERENCE_EXISTS = "isConferenceExists";
    public static final String MESSAGE = "message";
    public static final String DID_CONFERENCE_EXIST = "didConferenceExist";
    public static final String IS_POSITIVE_RESULT = "isPositiveResult";
    public static final String CONVERSATION_ID="conversationId";

    /**
     * Request parameters
     */
    public static final String FIRST_NAME = "firstname";
    public static final String LAST_NAME = "lastname";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String CONFIRMED_PASSWORD = "confirmedPassword";
    public static final String CONFERENCE_ID = "conferenceId";
    public static final String CONTENT = "content";
    public static final String SECTIONS = "sections";
    public static final String REQUEST_SECTIONS_IDS = "sectionsIds";
    public static final String SHOW_HIDE_VIEW_MORE_BUTTON="showViewMoreButton";





    /**
     * General constants
     */
    public static final int MESSAGES_UPDATE_AMOUNT = 7;
    public static final int CONFERENCES_PER_PAGE_AMOUNT = 3;
    public static final int EMAIL_MIN_LENGTH = 10;
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int NAME_MIN_LENGTH = 2;
}
