package by.epam.dmitriytomashevich.javatr.courses.command;

import by.epam.dmitriytomashevich.javatr.courses.command.user.SendClientRequestCommand;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class CommandFactoryTest {

    @Test
    public void testCreateCommand() {
        CommandFactory factory = new CommandFactory();
        Optional<Command> command = factory.createCommand("sendClientRequest");
        Command expected = new SendClientRequestCommand();

        assertEquals(command.get().getClass(), expected.getClass());
    }

    @Test
    public void testCreateIncorrectCommand(){
        CommandFactory factory = new CommandFactory();
        Optional<Command> incorrectCommand = factory.createCommand("sendClientRequestt");
        assertFalse(incorrectCommand.isPresent());
    }
}