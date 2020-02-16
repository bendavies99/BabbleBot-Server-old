package uk.co.bjdavies.app.modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.annotations.CommandFunction;
import uk.co.bjdavies.app.annotations.ModuleFunction;
import uk.co.bjdavies.app.commands.Command;
import uk.co.bjdavies.app.commands.CommandContext;
import uk.co.bjdavies.app.commands.CommandDispatcher;
import uk.co.bjdavies.app.exceptions.BabbleBotException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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
     * The application instance
     */
    private Application application;

    /**
     * This will init the Map using a HashMap as the concrete version of Map.
     * @param application - The application instance.
     */
    public ModuleContainer(Application application)
    {
        this.application = application;
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
            addCommandsFromModule(module);
            modules.put(name, module);
        }
    }

    private void addCommandsFromModule(Module module)
    {
        for (final Method method : module.getClass().getDeclaredMethods())
        {
            if (method.isAnnotationPresent(CommandFunction.class))
            {
                if (Arrays.asList(method.getParameterTypes()).contains(CommandContext.class) && method.getParameterTypes().length == 1)
                {
                    CommandFunction commandFunction = method.getAnnotation(CommandFunction.class);
                    CommandDispatcher commandDispatcher = (CommandDispatcher) application.getBindingContainer().getBinding("cmdDispatcher");
                    commandDispatcher.addCommand(new Command()
                    {
                        @Override
                        public String[] getAliases()
                        {
                            return commandFunction.aliases();
                        }

                        @Override
                        public String getDescription()
                        {
                            return commandFunction.description();
                        }

                        @Override
                        public String getUsage()
                        {
                            return commandFunction.usage();
                        }

                        @Override
                        public String getType()
                        {
                            return commandFunction.type();
                        }

                        @Override
                        public String run(Application application, CommandContext commandContext)
                        {
                            return (String) executeModuleCommand(new ModuleCommandExecutionBuilder(method.getName(), module.getClass()).setParameterTypes(CommandContext.class).setArgs(commandContext).build());
                        }

                        @Override
                        public boolean validateUsage(CommandContext commandContext)
                        {
                            boolean[] isValid = new boolean[]{true};

                            Arrays.asList(commandFunction.requiredParams()).stream().forEach(e -> {
                                if (!commandContext.hasParameter(e) && !e.isEmpty())
                                {
                                    isValid[0] = false;
                                }
                            });

                            return isValid[0];
                        }
                    });
                } else
                {
                    logger.error("Error a CommandFunction can only have a CommandContext as the parameter. Please fix or your CommandFunction will not work.", new BabbleBotException("Method: " + method.getName()));
                }
            }
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
     * This will execute a module command that are placed in the modules the command has to be annotated with @CommandFunction
     * e.g. play()
     *
     * @param moduleCommandDefinition   - This is the set of data that will be used to run the command.
     * @return Object
     */
    public Object executeModuleCommand(ModuleCommandDefinition moduleCommandDefinition)
    {
        String moduleName = moduleCommandDefinition.getModuleClass().getSimpleName();
        if (!doesModuleExist(moduleName))
        {
            logger.error("The module name entered does not exist inside this container.", new BabbleBotException("executeModuleCommand()"));
        } else
        {
            Class<? extends Module> moduleClass = moduleCommandDefinition.getModuleClass();
            try
            {

                Method method = moduleClass.getDeclaredMethod(moduleCommandDefinition.getName(), moduleCommandDefinition.getParameterTypes());
                method.setAccessible(true);


                if (method.isAnnotationPresent(CommandFunction.class) || method.isAnnotationPresent(ModuleFunction.class))
                {
                    return method.invoke(getModule(moduleName), moduleCommandDefinition.getArgs());
                }

            } catch (NoSuchMethodException e)
            {
                logger.error("The module command entered does not exist inside this module.", new BabbleBotException("executeModuleCommand()"));
            } catch (InvocationTargetException | IllegalAccessException e)
            {
                logger.error("The module command did not execute correctly.", e);
            }
        }
        return null;
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
            logger.error("The module name entered does not exist inside this container.", new BabbleBotException("getModule()"));
        } else
        {
            return modules.get(name);
        }
        return null;
    }


    /**
     * This method checks whether the module specified exists in the container.
     *
     * @param name - The name of the module.
     * @return boolean
     */
    public boolean doesModuleExist(String name)
    {
        return modules.keySet().stream().anyMatch(e -> e.toLowerCase().equals(name.toLowerCase()));
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
