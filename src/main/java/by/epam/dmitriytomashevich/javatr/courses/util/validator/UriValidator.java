package by.epam.dmitriytomashevich.javatr.courses.util.validator;

public class UriValidator {
    public static boolean isURICorrespondsToCommand(String stringKey, String command) {
        return command.equals(stringKey) || command.equals(stringKey + "/") || (command + "/").equals(stringKey);
    }
}
