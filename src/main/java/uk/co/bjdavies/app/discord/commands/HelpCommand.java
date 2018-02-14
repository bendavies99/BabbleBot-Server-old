package uk.co.bjdavies.app.discord.commands;

import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.commands.Command;
import uk.co.bjdavies.app.commands.CommandContext;
import uk.co.bjdavies.app.commands.CommandDispatcher;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: HelpCommand.java
 * Compiled Class Name: HelpCommand.class
 * Date Created: 14/02/2018
 */

public class HelpCommand implements Command
{
    @Override
    public String[] getAliases()
    {
        return new String[]{"help"};
    }

    @Override
    public String getDescription()
    {
        return "This will help you discover all the commands and their features.";
    }

    @Override
    public String getUsage()
    {
        return "help (-cmd=CommandName)";
    }

    @Override
    public String getType()
    {
        return "All";
    }

    @Override
    public String run(Application application, CommandContext commandContext)
    {
        CommandDispatcher commandDispatcher = (CommandDispatcher) application.getBindingContainer().getBinding("cmdDispatcher");

        if (commandContext.getValue().equals("") && !commandContext.hasParameter("cmd"))
        {
            StringBuilder sb = new StringBuilder(commandContext.getType().equals("Discord") ? "```" : "");

            for (Command command : commandDispatcher.getCommands(commandContext.getType()))
            {
                sb.append(application.getConfig().getDiscordConfig().getCommandPrefix()).append(command.getAliases()[0]).append(" - ").append(command.getDescription()).append("\n\n");
            }

            sb.append(commandContext.getType().equals("Discord") ? "```" : "");

            if (commandContext.getType().equals("Discord"))
            {
                commandContext.getMessage().getAuthor().getOrCreatePMChannel().sendMessage("List of all commands: use !help {command} for more\n" + sb.toString());
                return "Check your DMs I would have sent you a message :)";
            } else
            {
                return "List of all commands: use !help {command} for more\n" + sb.toString();
            }


        } else
        {

            String commandName;

            if (commandContext.hasParameter("cmd"))
            {

                commandName = commandContext.getParameter("cmd");

            } else
            {
                commandName = commandContext.getValue();
            }

            Optional<Command> command = commandDispatcher.getCommandByAlias(commandName, commandContext.getType());
            if (command.isPresent())
            {

                Command commandFound = command.get();

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("Help for Command: ").append(commandName).append("\n");

                stringBuilder.append("\nAlias(es):\n").append(commandContext.getType().equals("Discord") ? "```" : "");

                List<String> aliases = Arrays.asList(commandFound.getAliases());

                aliases.stream().forEach(e -> {
                    stringBuilder.append(e);
                    if (!(aliases.indexOf(e) == aliases.size() - 1))
                    {
                        stringBuilder.append("/");
                    }
                });

                stringBuilder.append(commandContext.getType().equals("Discord") ? "```" : "").append("\nDescription: \n").append(commandContext.getType().equals("Discord") ? "```" : "").append(commandFound.getDescription());

                stringBuilder.append(commandContext.getType().equals("Discord") ? "```" : "").append("\nUsage: \n").append(commandContext.getType().equals("Discord") ? "```" : "").append(commandFound.getUsage()).append(commandContext.getType().equals("Discord") ? "```" : "");

                return stringBuilder.toString();

            } else
            {
                return "The command entered does not exist.";
            }
        }

    }

    @Override
    public boolean validateUsage(CommandContext commandContext)
    {
        return true;
    }
}
