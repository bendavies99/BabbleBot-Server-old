package uk.co.bjdavies.app.modules;

import uk.co.bjdavies.app.annotations.CommandFunction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: Module.java
 * Compiled Class Name: Module.class
 * Date Created: 30/01/2018
 */

public abstract class Module
{
    /**
     * This is the name of the Module.
     *
     * @return String
     */
    public abstract String getName();


    /**
     * This is the version of the Module.
     *
     * @return String
     */
    public abstract String getVersion();


    /**
     * This is the author of the Module.
     *
     * @return String
     */
    public abstract String getAuthor();


    /**
     * This is the minimum version of the server that the module can run on. Default: 1
     *
     * @return String
     */
    public String getMinimumServerVersion()
    {
        return "1";
    }


    /**
     * This is the maximum version of the server that the module can run on. Default: 0 (no limit)
     *
     * @return String
     */
    public String getMaximumServerVersion()
    {
        return "0";
    }


    /**
     * This is a method to get all the methods that are used for commands.
     *
     * @return List
     */
    public final List<Method> getCommands()
    {
        List<Method> theAnnotatedMethods = new ArrayList<>();
        Class<? extends Module> moduleClass = this.getClass();
        Method[] methods = moduleClass.getMethods();
        for (Method method : methods)
        {
            if (method.isAnnotationPresent(CommandFunction.class)) theAnnotatedMethods.add(method);
        }
        return theAnnotatedMethods;
    }


    /**
     * This is a method to return a method that can be invoked by the command.
     *
     * @param name - the name of the method
     * @return Method
     */
    public final Method getCommand(String name)
    {
        return getCommands().stream().filter(e -> e.getName().toLowerCase().equals(name.toLowerCase())).findFirst().get();
    }

}
