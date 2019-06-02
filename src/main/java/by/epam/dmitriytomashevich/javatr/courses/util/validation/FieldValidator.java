package by.epam.dmitriytomashevich.javatr.courses.util.validation;

/**
 * Package-private class, that provide fields validation
 */
public class FieldValidator {
    @SafeVarargs
    private static <T> boolean isContainsNull(T... values) {
        if (values == null) {
            return true;
        } else {
            for (T value : values) {
                if (value == null) {
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean isNotFilled(String... values) {
        if (isContainsNull(values)) {
            return true;
        }

        for (String value : values) {
            if (value.matches("")) {
                return true;
            }
        }
        return false;
    }
}
