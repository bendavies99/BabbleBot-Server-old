package uk.co.bjdavies.app;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;
import uk.co.bjdavies.app.binding.BindingContainer;
import uk.co.bjdavies.app.cli.commands.ExitCommand;
import uk.co.bjdavies.app.commands.CommandDispatcher;
import uk.co.bjdavies.app.config.Config;
import uk.co.bjdavies.app.config.ConfigFactory;
import uk.co.bjdavies.app.config.ModuleConfig;
import uk.co.bjdavies.app.discord.commands.HelpCommand;
import uk.co.bjdavies.app.discord.commands.IgnoreCommand;
import uk.co.bjdavies.app.discord.commands.ListenCommand;
import uk.co.bjdavies.app.exceptions.BabbleBotException;
import uk.co.bjdavies.app.modules.Module;
import uk.co.bjdavies.app.modules.ModuleContainer;
import uk.co.bjdavies.app.routing.Routes;
import uk.co.bjdavies.app.routing.RoutingContainer;
import uk.co.bjdavies.app.services.*;
import uk.co.bjdavies.app.variables.GlobalVariables;
import uk.co.bjdavies.app.variables.VariableContainer;

import java.util.Collection;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: Application.java
 * Compiled Class Name: Application.class
 * Date Created: 30/01/2018
 */

public class Application
{
    /**
     * This is the container for binding objects to a name for use around the application.
     */
    private BindingContainer bindingContainer;


    /**
     * This is the container to hold all the services that are ran and used throughout the application.
     */
    private ServiceContainer serviceContainer;


    /**
     * This is the container to hold all the modules that will be used on the server. These are provided as 'plugins'.
     */
    private ModuleContainer moduleContainer;


    /**
     * This is the container for routing needs; Routing is for the api system that the server and client will communicate.
     */
    private RoutingContainer routingContainer;


    /**
     * This is the container that will hold all the global variables and custom variables for use within the command system.
     */
    private VariableContainer variableContainer;


    /**
     * This will hold the configuration data for the application.
     */
    private Config config;


    /**
     * This is where all the containers are instantiated.
     */
    public Application()
    {
        bindingContainer = new BindingContainer();
        serviceContainer = new ServiceContainer();
        moduleContainer = new ModuleContainer(this);
        routingContainer = new RoutingContainer();
        variableContainer = new VariableContainer();

        createConfig();
        bindDispatcher();
        addAllServices();
        bootDiscordClient();
        addAllRoutes();
        addAllVariables();
        addDefaultCommandsToDispatcher();
        bootServices();
        addAllModules();
        try
        {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * This will boot the discord client and make sure it is binded for all modules.
     */
    private void bootDiscordClient()
    {
        serviceContainer.addService("discordClient", new DiscordClientService());
    }


    /**
     * This method will create a class of config that will hold all the configuration data for later use in the application.
     */
    private void createConfig()
    {
        config = ConfigFactory.makeConfig("config.json");
    }


    /**
     * This method will add all the modules to the module container and will be used later on in the program.
     */
    protected void addAllModules()
    {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        if (!config.getSystemConfig().isDebugOn()) context.stop();
        Collection<ModuleConfig> moduleConfigs = config.getModules();
        for (ModuleConfig moduleConfig : moduleConfigs)
        {
            JarClassLoader jcl = new JarClassLoader();
            if (moduleConfig.getModuleLocation().contains(".jar"))
                jcl.add(moduleConfig.getModuleLocation());
            else
                jcl.add(moduleConfig.getModuleLocation() + "/" + moduleConfig.getModuleLocation().split("/")[1] + ".jar");

            JclObjectFactory factory = JclObjectFactory.getInstance();

            Module module;
            try
            {
                module = (Module) factory.create(jcl, moduleConfig.getModuleClassPath());
            } catch (Exception e)
            {
                try
                {
                    module = (Module) factory.create(jcl, moduleConfig.getModuleClassPath(), this);
                } catch (Exception e1)
                {
                    try
                    {
                        throw new BabbleBotException(e1.getMessage());
                    } catch (BabbleBotException e2)
                    {
                        e1.printStackTrace();
                        return;
                    }
                }
            }

            moduleContainer.addModule(module.getName(), module);
        }

        if (!config.getSystemConfig().isDebugOn()) context.start();
    }


    /**
     * This method will load all routes into the routing container and that will be used for the API System.
     */
    private void addAllRoutes()
    {
        routingContainer.addRoutes(Routes.class);
    }


    /**
     * This method will load all the standard global variables into the variable container and these then can be used with the command responses.
     */
    private void addAllVariables()
    {
        variableContainer.addAllFrom(GlobalVariables.class);
    }


    /**
     * This method will load all the services that are needed into the services container.
     */
    private void addAllServices()
    {
        serviceContainer.addService("terminal", new TerminalService());
        serviceContainer.addService("discordMessaging", new DiscordMessageService());
        serviceContainer.addService("authService", new AuthService());
        serviceContainer.addService("jwtService", new JWTTokenService());
        serviceContainer.addService("routingService", new RoutingService());
    }


    /**
     * This method will boot all the standard services that are needed to run the application.
     */
    private void bootServices()
    {
        serviceContainer.startService("terminal", this);
        serviceContainer.startService("discordClient", this);
        serviceContainer.startService("discordMessaging", this);
        serviceContainer.startService("authService", this);
        serviceContainer.startService("jwtService", this);
        serviceContainer.startService("routingService", this);
    }


    /**
     * This method will bind the command dispatcher to the binding container and this will allow use to add commands from a module for example.
     */
    private void bindDispatcher()
    {
        bindingContainer.addBinding("cmdDispatcher", new CommandDispatcher());
    }


    /**
     * This will add default commands to the dispatcher for terminal use and some standard discord command like ((prefix)ignore).
     */
    private void addDefaultCommandsToDispatcher()
    {
        CommandDispatcher commandDispatcher = (CommandDispatcher) bindingContainer.getBinding("cmdDispatcher");
        commandDispatcher.addCommand(new ExitCommand());
        commandDispatcher.addCommand(new IgnoreCommand());
        commandDispatcher.addCommand(new ListenCommand());
        commandDispatcher.addCommand(new HelpCommand());
    }


    /**
     * This will return the Binding Container.
     *
     * @return BindingContainer
     */
    public BindingContainer getBindingContainer()
    {
        return bindingContainer;
    }


    /**
     * This will return the Service Container.
     *
     * @return ServiceContainer
     */
    public ServiceContainer getServiceContainer()
    {
        return serviceContainer;
    }


    /**
     * This will return the Module Container.
     *
     * @return ModuleContainer
     */
    public ModuleContainer getModuleContainer()
    {
        return moduleContainer;
    }


    /**
     * This will return the Routing Container.
     *
     * @return RoutingContainer
     */
    public RoutingContainer getRoutingContainer()
    {
        return routingContainer;
    }


    /**
     * This will return the Variable Container.
     *
     * @return VariableContainer.
     */
    public VariableContainer getVariableContainer()
    {
        return variableContainer;
    }


    /**
     * This will return the Config
     *
     * @return Config
     */
    public Config getConfig()
    {
        return config;
    }

    public void requestShutDown()
    {
        serviceContainer.stopAllServices();
    }


}
