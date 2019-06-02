package by.epam.dmitriytomashevich.javatr.courses.util.validation;

import java.util.Set;

public class CommandValidator {
    public static boolean isSetContainsCommand(Set<String> commandsSet, String stringCommand){
        return commandsSet.stream().anyMatch(s -> isCommandEqualsToString(s, stringCommand));
    }

    public static boolean isCommandEqualsToString(String stringKey, String command) {
        return command.equals(stringKey) || command.equals(stringKey + "/") || (command + "/").equals(stringKey);
    }
}
