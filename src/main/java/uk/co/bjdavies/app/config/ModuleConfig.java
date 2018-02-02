package uk.co.bjdavies.app.config;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: ModuleConfig.java
 * Compiled Class Name: ModuleConfig.class
 * Date Created: 31/01/2018
 */

public class ModuleConfig
{
    /**
     * This is the location that the module's Jar file and configs are kept
     * Keep in mind there is a convention for the structure. Convention over Configuration approach.
     * e.g. AudioDJ -> AudioDJ.jar , AudioDJ.json
     */
    private String moduleLocation;

    /**
     * This is the class path that the class that implements <strong>uk.co.bjdavies.modules.Module</strong>
     */
    private String moduleClassPath;


    /**
     * This will return the module's location.
     *
     * @return String
     */
    public String getModuleLocation()
    {
        return moduleLocation;
    }

    /**
     * This will return the module's class path.
     *
     * @return String
     */
    public String getModuleClassPath()
    {
        return moduleClassPath;
    }
}
