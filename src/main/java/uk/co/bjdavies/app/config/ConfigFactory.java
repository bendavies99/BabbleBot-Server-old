package uk.co.bjdavies.app.config;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: ConfigFactory.java
 * Compiled Class Name: ConfigFactory.class
 * Date Created: 30/01/2018
 */

public class ConfigFactory
{

    /**
     * This factory method will create a config from a json file / string.
     *
     * @param json - The inputted json file / string.
     * @return Config
     */
    public static Config makeConfig(String json)
    {
        return new ConfigParser(json).getConfig();
    }
}
