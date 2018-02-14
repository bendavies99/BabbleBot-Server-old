package uk.co.bjdavies.app.discord;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.binding.Bindable;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: DiscordClientFacade.java
 * Compiled Class Name: DiscordClientFacade.class
 * Date Created: 31/01/2018
 */

public class DiscordClientFacade implements Bindable
{

    /**
     * This is the client that all the discord action will be ran on.
     */
    private IDiscordClient client;


    /**
     * This is the application instance.
     */
    private Application application;


    /**
     * This will init the class.
     *
     * @param token - The token that the client will run on.
     * @param application - The application instance.
     */
    public DiscordClientFacade(String token, Application application)
    {
        this.application = application;
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        if (!application.getConfig().getSystemConfig().isDebugOn()) context.stop();
        client = new ClientBuilder().withToken(token).build();
        if (!application.getConfig().getSystemConfig().isDebugOn()) context.start();
    }

    /**
     * This will log the bot in and make it ready for use.
     */
    public void connect()
    {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        if (!application.getConfig().getSystemConfig().isDebugOn()) context.stop();

        addListener(readyEvent ->
        {
            if (readyEvent instanceof ReadyEvent)
            {
                client.changePlayingText(" 2018 | Author: Ben Davies | " + application.getConfig().getDiscordConfig().getCommandPrefix() + "help");
            }
        });

        client.login();
        if (!application.getConfig().getSystemConfig().isDebugOn()) context.start();

    }

    /**
     * This will logout the bot and then all of the bot's functions will stop.
     */
    public void disconnect()
    {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        if (!application.getConfig().getSystemConfig().isDebugOn()) context.stop();
        client.logout();
        if (!application.getConfig().getSystemConfig().isDebugOn()) context.start();
    }

    /**
     * This will register a listener to the event dispatcher.
     *
     * @param listener - THe listener you want to attach
     */
    public void addListener(IListener listener)
    {
        client.getDispatcher().registerListener(listener);
    }

    /**
     * This will unregister a listener from the event dispatcher.
     *
     * @param listener - THe listener you want to de-attach
     */
    public void removeListener(IListener listener)
    {
        client.getDispatcher().unregisterListener(listener);
    }

    /**
     * This will register a listener to the event dispatcher.
     *
     * @param listener - THe listener you want to attach
     */
    public void addListener(Class<?> listener)
    {
        client.getDispatcher().registerListener(listener);
    }

    /**
     * This will unregister a listener from the event dispatcher.
     *
     * @param listener - THe listener you want to de-attach
     */
    public void removeListener(Class<?> listener)
    {
        client.getDispatcher().unregisterListener(listener);
    }

}
