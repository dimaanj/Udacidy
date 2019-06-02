package by.epam.dmitriytomashevich.javatr.courses.util.validation;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class InputValidatorTest {

    @Test
    public void testValidateUserRegistrationParameters() {
        String firstName = "Vasya";
        String lastName = "Pupkin";
        String email = "vasya@gmail.com";
        String password = "1Ft32%hgf9";
        String confirmedPassword = "1Ft32%hgf9";

        boolean actual = InputValidator.validateUserRegistrationParameters(
                firstName,
                lastName,
                email,
                password,
                confirmedPassword);

        assertTrue(actual);
    }

    @Test
    public void testValidateUserLoginParameters() {
        String email = "vasya@gmail.com";
        String password = "1Ft32%hgf9";
        boolean actual = InputValidator.validateUserLoginParameters(email, password);
        assertTrue(actual);
    }

    @Test
    public void testValidateNullRegistrationParameters(){
        String email = null;
        String password = null;
        boolean actual = InputValidator.validateUserLoginParameters(email, password);
        assertFalse(actual);
    }

    @Test
    public void testValidateIncorrectRegistrationParameters(){
        String firstName = "Ktoto";
        String lastName = "ktotovich";
        String email = "kto@mail.ru";
        String incorrectPassword = "1fRhfykugey5";
        String incorrectConfirmedPassword = "1fRhfykugey5";

        boolean actual = InputValidator.validateUserRegistrationParameters(
                firstName,
                lastName,
                email,
                incorrectPassword,
                incorrectConfirmedPassword);

        assertFalse(actual);
    }
}