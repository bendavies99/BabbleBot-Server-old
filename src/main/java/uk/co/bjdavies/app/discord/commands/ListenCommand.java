package uk.co.bjdavies.app.discord.commands;

import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.commands.Command;
import uk.co.bjdavies.app.commands.CommandContext;
import uk.co.bjdavies.app.db.DB;
import uk.co.bjdavies.app.db.models.Ignore;
import uk.co.bjdavies.app.db.models.Models;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: IgnoreCommand.java
 * Compiled Class Name: IgnoreCommand.class
 * Date Created: 07/02/2018
 */

public class ListenCommand implements Command
{

    @Override
    public String[] getAliases()
    {
        return new String[]{"listen"};
    }

    @Override
    public String getDescription()
    {
        return "This will make the bot start listening again to the channel";
    }

    @Override
    public String getUsage()
    {
        return "listen";
    }

    @Override
    public String getType()
    {
        return "Discord";
    }

    @Override
    public String run(Application application, CommandContext commandContext)
    {
        if (Models.whereFirst(Ignore.class, application, "channelID", commandContext.getMessage().getChannel().getStringID()) != null)
        {
            DB.table("ignores", application).where("channelID", commandContext.getMessage().getChannel().getStringID()).delete();
        }

        if (Models.whereFirst(Ignore.class, application, "channelID", commandContext.getMessage().getChannel().getStringID()) == null)
        {
            return "I am free! I will start listening on this channel again <3";
        } else
        {
            return "Something went wrong please send a message to the server administrator.";
        }
    }

    @Override
    public boolean validateUsage(CommandContext commandContext)
    {
        return true;
    }
}
