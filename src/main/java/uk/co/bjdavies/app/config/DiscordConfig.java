package uk.co.bjdavies.app.config;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: DiscordConfig.java
 * Compiled Class Name: DiscordConfig.class
 * Date Created: 31/01/2018
 */

public class DiscordConfig
{
    /**
     * This is used to connect to the discord api with your selected bot.
     */
    private String token;


    /**
     * This is what will be used to determine if a discord message can be considered a command.
     */
    private String commandPrefix;


    /**
     * This will return the token.
     *
     * @return String
     */
    public String getToken()
    {
        return token;
    }

    /**
     * This will return the command prefix.
     *
     * @return String
     */
    public String getCommandPrefix()
    {
        return commandPrefix;
    }
}
