package by.epam.dmitriytomashevich.javatr.courses.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Returns message
 */
public class MessageManager {
    private static ResourceBundle ruBundle = ResourceBundle.getBundle("locale", new Locale("ru"));
    private static ResourceBundle enBundle = ResourceBundle.getBundle("locale", new Locale("en"));

    public static String getMessage(String key, String locale) {
        if ("en".equalsIgnoreCase(locale)) {
            return enBundle.getString(key);
        }
        return ruBundle.getString(key);
    }
}

