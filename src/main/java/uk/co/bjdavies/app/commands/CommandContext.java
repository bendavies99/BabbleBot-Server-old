package uk.co.bjdavies.app.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;
import uk.co.bjdavies.app.exceptions.BabbleBotException;

import java.util.Map;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: CommandContext.java
 * Compiled Class Name: CommandContext.class
 * Date Created: 31/01/2018
 */

public class CommandContext
{

    /**
     * This is the Logger implementation for this class.
     */
    private Logger logger = LoggerFactory.getLogger(CommandContext.class);


    /**
     * This is the Map for all the command's paramaters.
     */
    private Map<String, String> parameters;


    /**
     * This is the name of the command.
     */
    private String commandName;


    /**
     * This is the value of the command (if any.)
     */
    private String value;


    /**
     * This is the type of command (Terminal, Discord)
     */
    private String type;

    /**
     * This is used for discord messages.
     */
    private IMessage message;


    /**
     * This is the CommandContext Constructor.
     *
     * @param commandName - The name of the command.
     * @param parameters  - THe command's paramaters.
     * @param value       - The value of the command (if any).
     * @param type        - The type of the command.
     */
    public CommandContext(String commandName, Map<String, String> parameters, String value, String type)
    {
        this.commandName = commandName;
        this.parameters = parameters;
        this.value = value;
        this.type = type;
    }


    /**
     * This is the CommandContext Constructor.
     *
     * @param commandName - The name of the command.
     * @param parameters  - THe command's paramaters.
     * @param value       - The value of the command (if any).
     * @param type        - The type of the command.
     * @param message     - IMessage of which was created when the message was sent.
     */
    public CommandContext(String commandName, Map<String, String> parameters, String value, String type, IMessage message)
    {
        this.commandName = commandName;
        this.parameters = parameters;
        this.value = value;
        this.type = type;
        this.message = message;
    }


    /**
     * This will return the value of a given parameter.
     *
     * @param name - The name of the paramater
     * @return String
     */
    public String getParameter(String name)
    {
        if (!parameters.containsKey(name))
        {
            logger.error("Parameter not found.", new BabbleBotException());
        } else
        {
            return parameters.get(name);
        }
        return "";
    }


    /**
     * This checks whether a parameter is present.
     *
     * @param name - This is the name of the paramater.
     * @return boolean
     */
    public boolean hasParamater(String name)
    {
        return parameters.containsKey(name);
    }


    /**
     * Returns the command's name.
     *
     * @return String
     */
    public String getCommandName()
    {
        return commandName;
    }

    /**
     * Returns the value of the command (if any).
     *
     * @return String
     */
    public String getValue()
    {
        return value;
    }

    /**
     * This returns the command's type.
     *
     * @return String
     */
    public String getType()
    {
        return type;
    }

    /**
     * This will return the message object.
     *
     * @return IMessage
     */
    public IMessage getMessage()
    {
        return message;
    }
}
