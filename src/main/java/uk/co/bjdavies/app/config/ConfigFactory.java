package uk.co.bjdavies.app.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

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
        if (json.equals("config.json"))
        {
            try
            {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(json));
                return new ConfigParser(bufferedReader).getConfig();
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }


        } else
        {
            return new ConfigParser(json).getConfig();
        }
        System.out.println("An error occurred when trying to parse the config");
        return null;
    }
}
