package uk.co.bjdavies.app;

import uk.co.bjdavies.app.binding.BindingContainer;
import uk.co.bjdavies.app.cli.commands.ExitCommand;
import uk.co.bjdavies.app.cli.commands.TestCommand;
import uk.co.bjdavies.app.commands.CommandDispatcher;
import uk.co.bjdavies.app.config.Config;
import uk.co.bjdavies.app.config.ConfigFactory;
import uk.co.bjdavies.app.modules.AudioModule;
import uk.co.bjdavies.app.modules.ModuleContainer;
import uk.co.bjdavies.app.routing.RouteFactory;
import uk.co.bjdavies.app.routing.RoutingContainer;
import uk.co.bjdavies.app.services.DiscordClientService;
import uk.co.bjdavies.app.services.DiscordMessageService;
import uk.co.bjdavies.app.services.ServiceContainer;
import uk.co.bjdavies.app.services.TerminalService;
import uk.co.bjdavies.app.variables.GlobalVariables;
import uk.co.bjdavies.app.variables.VariableContainer;

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
        moduleContainer = new ModuleContainer();
        routingContainer = new RoutingContainer();
        variableContainer = new VariableContainer();

        createConfig();
        bootDiscordClient();
        allAllServices();
        addAllModules();
        addAllRoutes();
        addAllVariables();
        bindDispatcher();
        addDefaultCommandsToDispatcher();
        bootServices();
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
    private void addAllModules()
    {
        moduleContainer.addModule("audioDJ", new AudioModule());
    }


    /**
     * This method will load all routes into the routing container and that will be used for the API System.
     */
    private void addAllRoutes()
    {
        routingContainer.addRoute(RouteFactory.makeGetRoute("/test", e -> System.out.println("Test")));
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
    private void allAllServices()
    {
        serviceContainer.addService("terminal", new TerminalService());
        serviceContainer.addService("discordClient", new DiscordClientService());
        serviceContainer.addService("discordMessaging", new DiscordMessageService());
    }


    /**
     * This method will boot all the standard services that are needed to run the application.
     */
    private void bootServices()
    {
        serviceContainer.startService("terminal", this);
        serviceContainer.startService("discordClient", this);
        serviceContainer.startService("discordMessaging", this);
    }


    /**
     * This method will boot the discord client so we can interact with discord.
     */
    private void bootDiscordClient()
    {

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
        commandDispatcher.addCommand(new TestCommand());
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
