package uk.co.bjdavies.app.services;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.binding.Bindable;
import uk.co.bjdavies.app.binding.BindingEventListener;
import uk.co.bjdavies.app.commands.CommandDispatcher;
import uk.co.bjdavies.app.commands.DiscordMessageParser;
import uk.co.bjdavies.app.facades.DiscordClientFacade;

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
        discordClientFacade.removeListener(this.getClass());
        return false;
    }

    @Override
    public void run()
    {
        discordClientFacade.addListener(event -> {
            if (event instanceof MessageReceivedEvent)
            {
                String message = ((MessageReceivedEvent) event).getMessage().getFormattedContent();
                IChannel channel = ((MessageReceivedEvent) event).getChannel();
                if (message.startsWith(application.getConfig().getDiscordConfig().getCommandPrefix()))
                {

                    String response = commandDispatcher.execute(new DiscordMessageParser(((MessageReceivedEvent) event).getMessage()), message.replace(application.getConfig().getDiscordConfig().getCommandPrefix(), ""), application);

                    if (response.isEmpty())
                    {
                        return;
                    }

                    channel.sendMessage(
                            response
                    );
                }
            }
        });
    }


}
