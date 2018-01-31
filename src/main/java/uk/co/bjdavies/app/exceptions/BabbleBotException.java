package uk.co.bjdavies.app.exceptions;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: BabbleBotException.java
 * Compiled Class Name: BabbleBotException.class
 * Date Created: 30/01/2018
 */

public class BabbleBotException extends Exception
{
    public BabbleBotException()
    {
    }

    public BabbleBotException(String message)
    {
        super(message);
    }
}
