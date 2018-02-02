package uk.co.bjdavies.app.config;

import java.util.Arrays;
import java.util.Collection;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: Config.java
 * Compiled Class Name: Config.class
 * Date Created: 30/01/2018
 */

public class Config
{
    /**
     * This is the config settings for the discord part of this bot.
     */
    private DiscordConfig discord;


    /**
     * This is the config settings for the system part of this bot.
     */
    private SystemConfig system;


    /**
     * This is the config settings for the modules used in this bot.
     */
    private ModuleConfig[] modules;


    /**
     * This will return the config for the discord part of this bot.
     *
     * @return DiscordConfig
     */
    public DiscordConfig getDiscordConfig()
    {
        return discord;
    }


    /**
     * This will return the config for the system part of this bot.
     *
     * @return SystemConfig
     */
    public SystemConfig getSystemConfig()
    {
        return system;
    }


    /**
     * This will return the config for the modules used in this bot.
     *
     * @return Collection(ModuleConfig)
     */
    public Collection<ModuleConfig> getModules()
    {
        return Arrays.asList(modules);
    }

}
