package uk.co.bjdavies.app.config;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: ConfigParser.java
 * Compiled Class Name: ConfigParser.class
 * Date Created: 30/01/2018
 */

public class ConfigParser
{
    /**
     * This is the config variable that can be used after parsing through a getter.
     */
    private Config config;

    /**
     * This is where the config will get parsed.
     *
     * @param json - The inputted file / string.
     */
    public ConfigParser(String json)
    {
        config = new Config()
        {
        };
    }


    /**
     * This will return the Config.
     *
     * @return Config
     */
    public Config getConfig()
    {
        return config;
    }
}
