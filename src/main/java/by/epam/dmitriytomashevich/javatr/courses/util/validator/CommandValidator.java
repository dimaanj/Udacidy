package by.epam.dmitriytomashevich.javatr.courses.util.validator;

import java.util.Set;

public class CommandValidator {
    public static boolean defineCommand(Set<String> commandsSet, String stringCommand){
        return commandsSet.stream().anyMatch(s -> UriValidator.isURICorrespondsToCommand(s, stringCommand));
    }
}
