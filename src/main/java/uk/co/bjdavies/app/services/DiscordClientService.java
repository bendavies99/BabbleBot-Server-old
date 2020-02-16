package uk.co.bjdavies.app.services;


import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.discord.DiscordClientFacade;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: DiscordClientService.java
 * Compiled Class Name: DiscordClientService.class
 * Date Created: 31/01/2018
 */

public class DiscordClientService implements Service
{
    /**
     * The client that all the discord functions will run on.
     */
    private DiscordClientFacade discordClient;

    @Override
    public String getThreadName()
    {
        return "Discord-Client";
    }

    @Override
    public boolean boot(Application application)
    {
            discordClient = new DiscordClientFacade(application.getConfig().getDiscordConfig().getToken(), application);
            application.getBindingContainer().addBinding("DiscordClient", discordClient);
            return true;
    }

    @Override
    public boolean shutdown()
    {
        discordClient.disconnect();
        return true;
    }

    @Override
    public void run()
    {
        discordClient.connect();
    }
}
