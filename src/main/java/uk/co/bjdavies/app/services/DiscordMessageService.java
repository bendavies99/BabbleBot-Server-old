package uk.co.bjdavies.app.services;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.MessageChannel;
import reactor.core.publisher.Mono;
import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.binding.Bindable;
import uk.co.bjdavies.app.binding.BindingEventListener;
import uk.co.bjdavies.app.commands.CommandDispatcher;
import uk.co.bjdavies.app.commands.DiscordMessageParser;
import uk.co.bjdavies.app.db.models.Ignore;
import uk.co.bjdavies.app.db.models.Models;
import uk.co.bjdavies.app.discord.DiscordClientFacade;

import java.util.Objects;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: DiscordMessageService.java
 * Compiled Class Name: DiscordMessageService.class
 * Date Created: 31/01/2018
 */

public class DiscordMessageService implements Service
{

    private CommandDispatcher commandDispatcher;
    private DiscordClientFacade discordClientFacade;
    private Application application;

    @Override
    public String getThreadName()
    {
        return "Discord-Message-Service";
    }

    @Override
    public boolean boot(Application application)
    {
        this.application = application;
        commandDispatcher = (CommandDispatcher) application.getBindingContainer().getBinding("cmdDispatcher");
        discordClientFacade = (DiscordClientFacade) application.getBindingContainer().getBinding("DiscordClient");

        application.getBindingContainer().addListener(new BindingEventListener()
        {
            @Override
            public void onUpdate(Bindable bindable)
            {
                discordClientFacade = (DiscordClientFacade) bindable;
            }
        }, discordClientFacade);

        return false;
    }

    @Override
    public boolean shutdown()
    {
        return false;
    }

    @Override
    public void run()
    {
        discordClientFacade.addListener(MessageCreateEvent.class).subscribe(event -> {
            String message = event.getMessage().getContent().isPresent() ? event.getMessage().getContent().get() : "";
            Mono<MessageChannel> channel = event.getMessage().getChannel();
            if(message.startsWith(application.getConfig().getDiscordConfig().getCommandPrefix()) && (Models.whereFirst(Ignore.class, application, "channelID", event.getMessage().getChannelId().asString()) == null || message.contains("listen"))) {
                String response = commandDispatcher.execute(new DiscordMessageParser(event.getMessage()), message.replace(application.getConfig().getDiscordConfig().getCommandPrefix(), ""), application);

                if(response.isEmpty()){
                    return;
                }
                Objects.requireNonNull(channel.block()).createMessage(response).block();
            }
        });
    }


}
