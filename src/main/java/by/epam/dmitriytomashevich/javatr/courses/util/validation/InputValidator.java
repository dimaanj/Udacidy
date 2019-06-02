package by.epam.dmitriytomashevich.javatr.courses.util.validation;

import by.epam.dmitriytomashevich.javatr.courses.constant.ParameterNames;

/**
 * That class provide user inputs validation
 */
public class InputValidator {
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    public static boolean validateUserRegistrationParameters(
            String firstName, String lastName, String email, String password, String confirmedPassword){
        if(firstName == null ||lastName == null ||
                email == null || password == null ||
                confirmedPassword == null){
            return false;
        }

        if(firstName.length() < ParameterNames.NAME_MIN_LENGTH ||
                lastName.length() < ParameterNames.NAME_MIN_LENGTH){
            return false;
        }

        return email.matches(EMAIL_REGEX) &&
                password.matches(PASSWORD_REGEX) &&
                confirmedPassword.matches(PASSWORD_REGEX);
    }

    public static boolean validateUserLoginParameters(String email, String password){
        if(email == null ||password == null) {
            return false;
        }

        if(email.length() < ParameterNames.EMAIL_MIN_LENGTH ||
                password.length() < ParameterNames.PASSWORD_MIN_LENGTH){
            return false;
        }

        return email.matches(EMAIL_REGEX) &&
                password.matches(PASSWORD_REGEX);
    }
}


