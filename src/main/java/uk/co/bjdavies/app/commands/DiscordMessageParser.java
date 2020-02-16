package uk.co.bjdavies.app.commands;

import discord4j.core.object.entity.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: DiscordMessageParser.java
 * Compiled Class Name: DiscordMessageParser.class
 * Date Created: 31/01/2018
 */

public class DiscordMessageParser implements MessageParser
{


    /**
     * This is the message that was created when the message was sent.
     */
    private final Message message;

    /**
     * This will construct the class.
     *
     * @param message - The IMessage which was created when the message was sent.
     */
    public DiscordMessageParser(Message message)
    {
        this.message = message;
    }


    /**
     * This will parse the string inputted the by the user.
     *
     * @param message - The raw inputted message.
     * @return CommandContext
     */
    @Override
    public CommandContext parseString(String message)
    {
        return new CommandContext(parseCommandName(message).toLowerCase(), parseParams(message), parseValue(message), "Discord", this.message);
    }


    /**
     * This will parse the value of the command if there is one.
     *
     * @param message - The raw inputted message.
     * @return String
     */
    private String parseValue(String message)
    {
        Matcher matcher = getParameterMatcher(message);
        message = message.replace(parseCommandName(message), "");
        while (matcher.find())
        {
            message = message.replace(matcher.group(), "");
        }
        message = message.trim();
        return message;
    }


    /**
     * This will parse the command name from the message.
     *
     * @param message - The raw inputted message.
     * @return String
     */
    private String parseCommandName(String message)
    {
        int indexOfFirstSpace = message.indexOf(" ");

        if (indexOfFirstSpace == -1)
        {
            return message;
        } else
        {
            return message.substring(0, indexOfFirstSpace).trim();
        }
    }


    /**
     * This will parse the parameters from the inputted message
     *
     * @param message - The raw inputted message.
     * @return Map(String, String)
     */
    private Map<String, String> parseParams(String message)
    {
        Map<String, String> params = new HashMap<>();
        Matcher matcher = getParameterMatcher(message);

        while (matcher.find())
        {
            String name = matcher.group(1);
            String value = matcher.group(2).replaceAll("\"", "");
            params.put(name, value);
        }
        return params;
    }


    /**
     * This will parse the parameters in the message and return a Matcher.
     *
     * @param message - The raw inputted message.
     * @return Matcher
     */
    private Matcher getParameterMatcher(String message)
    {
        String parameterRegex = "-([a-zA-Z0-9]+)=(([a-zA-Z0-9:/?=&_.]+)|(\"([a-zA-Z0-9:/?=&_.]+)\"))";

        Pattern pattern = Pattern.compile(parameterRegex);

        return pattern.matcher(message);
    }
}
