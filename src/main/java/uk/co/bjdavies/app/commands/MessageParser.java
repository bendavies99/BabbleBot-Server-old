package uk.co.bjdavies.app.commands;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: MessageParser.java
 * Compiled Class Name: MessageParser.class
 * Date Created: 31/01/2018
 */

public interface MessageParser
{
    /**
     * This will parse the user's input.
     *
     * @param message - The raw inputted message.
     * @return CommandContext
     */
    CommandContext parseString(String message);
}
