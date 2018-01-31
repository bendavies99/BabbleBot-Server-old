package uk.co.bjdavies.app.commands;

import java.util.HashMap;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: TerminalParser.java
 * Compiled Class Name: TerminalParser.class
 * Date Created: 31/01/2018
 */

public class TerminalMessageParser implements MessageParser
{

    @Override
    public CommandContext parseString(String message)
    {

        return new CommandContext(message, new HashMap<>(), "", "Terminal");
    }
}
