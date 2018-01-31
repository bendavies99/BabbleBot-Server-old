package uk.co.bjdavies.app.modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.bjdavies.app.exceptions.BabbleBotException;

import java.util.HashMap;
import java.util.Map;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: ModuleContainer.java
 * Compiled Class Name: ModuleContainer.class
 * Date Created: 30/01/2018
 */

public class ModuleContainer
{
    /**
     * This is the logger implementation for this class.
     */
    private final static Logger logger = LoggerFactory.getLogger(ModuleContainer.class);

    /**
     * This is the Map of all the modules in this container.
     */
    private Map<String, Module> modules;


    /**
     * This will init the Map using a HashMap as the concrete version of Map.
     */
    public ModuleContainer()
    {
        modules = new HashMap<>();
    }


    /**
     * This method will allow you to add a module to the container.
     *
     * @param name   - The name of the module.
     * @param module - The module itself.
     */
    public void addModule(String name, Module module)
    {
        if (modules.containsKey(name) || modules.containsValue(module))
        {
            logger.error("The key or module is already in the container", new BabbleBotException());
        } else
        {
            modules.put(name, module);
        }
    }


    /**
     * This method allows you to remove a module from the container.
     *
     * @param name - This is the module that you want to remove.
     */
    public void removeModule(String name)
    {
        if (!modules.containsKey(name))
        {
            logger.error("The module name entered does not exist inside this container.", new BabbleBotException());
        } else
        {
            modules.remove(name);
        }
    }


    /**
     * This method allows you to get a module from the container.
     *
     * @param name - This is the module that you want to get from the container.
     * @return Module
     */
    public Module getModule(String name)
    {
        if (!modules.containsKey(name))
        {
            logger.error("The module name entered does not exist inside this container.", new BabbleBotException());
        } else
        {
            return modules.get(name);
        }
        return null;
    }

    /**
     * This is the toString of the class will show the classes contents.
     *
     * @return String
     */
    @Override
    public String toString()
    {
        return "ModuleContainer{" +
                "modules=" + modules +
                '}';
    }
}
