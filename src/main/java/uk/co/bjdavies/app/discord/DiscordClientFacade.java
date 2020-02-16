package uk.co.bjdavies.app.discord;

import ch.qos.logback.classic.LoggerContext;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
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
    private DiscordClient client;


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
        client = new DiscordClientBuilder(token).build();
        if (!application.getConfig().getSystemConfig().isDebugOn()) context.start();
    }

    /**
     * This will log the bot in and make it ready for use.
     */
    public void connect()
    {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        if (!application.getConfig().getSystemConfig().isDebugOn()) context.stop();

        addListener(ReadyEvent.class).subscribe(ready -> { System.out.println("Playing Text is Different Now!"); });

        client.login().block();
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
     * @param
     * @param event - the listener to you want to attach.
     */
     public <E extends Event>Flux<E> addListener(Class<E> event)
    {
        return client.getEventDispatcher().on(event);
    }

}
