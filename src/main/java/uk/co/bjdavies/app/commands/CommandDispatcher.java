package uk.co.bjdavies.app.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.binding.Bindable;
import uk.co.bjdavies.app.exceptions.BabbleBotException;
import uk.co.bjdavies.app.variables.VariableParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: CommandDispatcher.java
 * Compiled Class Name: CommandDispatcher.class
 * Date Created: 31/01/2018
 */

public class CommandDispatcher implements Bindable
{

    /**
     * This is the logger implementation for this class.
     */
    Logger logger = LoggerFactory.getLogger(CommandDispatcher.class);

    /**
     * This is the list of command that can be executed by the dispatcher.
     */
    private List<Command> commands;


    /**
     * This will initialize the commands list to an ArrayList.
     */
    public CommandDispatcher()
    {
        this.commands = new ArrayList<>();
    }


    /**
     * This is where you can a command to the command dispatcher.
     *
     * @param command - The command you wish to add.
     */
    public void addCommand(Command command)
    {
        if (commands.contains(command))
        {
            logger.error("The command has already been added to the commands dispatcher.", new BabbleBotException());
        } else
        {
            commands.add(command);
        }
    }


    /**
     * This is where you can remove the command from the dispatcher.
     *
     * @param command - The command you wish to remove.
     */
    public void removeCommand(Command command)
    {
        if (!commands.contains(command))
        {
            logger.error("Command is not part of the dispatcher so it cannot be removed.");
        } else
        {
            commands.remove(command);
        }

    }


    /**
     * This will execute the command that user has entered is valid.
     *
     * @param parser      - The way the message is interpreted.
     * @param message     - This is the user's input or any other type of input.
     * @param application - The application instance.
     * @return String - The command's response
     */
    public String execute(MessageParser parser, String message, Application application)
    {

        System.out.println("This got ran with the application code of " + application);
        System.out.println(message);

        CommandContext commandContext = parser.parseString(message);

        if (commandContext != null)
        {
            final Command[] command = new Command[1];
            commands.stream().filter(e -> e.getType().toLowerCase().equals(commandContext.getType().toLowerCase())).forEach(e -> {
                Optional<String> findCommand = Arrays.asList(e.getAliases()).stream().filter(alias -> alias.toLowerCase().equals(commandContext.getCommandName().toLowerCase())).findFirst();
                if (findCommand.isPresent())
                {
                    command[0] = e;
                }
            });

            if (command[0] != null)
            {
                boolean isValid = command[0].validateUsage(commandContext);
                if (isValid)
                {
                    return new VariableParser(command[0].run(application, commandContext), application).toString();
                } else
                {
                    return command[0].getUsage();
                }
            } else
            {
                return "The command couldn't be found.";
            }

        } else
        {
            return "Command could not be parsed.";
        }

    }

}
