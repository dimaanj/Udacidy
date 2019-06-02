package by.epam.dmitriytomashevich.javatr.courses.util.validation;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class FieldValidatorTest {

    @Test
    public void testIsNotFilled() {
        String notFilled = "";
        Assert.assertTrue(FieldValidator.isNotFilled(notFilled));
    }

    @Test
    public void testNullParameters(){
        String notFilled = null;
        Assert.assertTrue(FieldValidator.isNotFilled(notFilled));
    }
}