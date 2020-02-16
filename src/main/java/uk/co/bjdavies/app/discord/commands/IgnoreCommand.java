package uk.co.bjdavies.app.discord.commands;

import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.commands.Command;
import uk.co.bjdavies.app.commands.CommandContext;
import uk.co.bjdavies.app.db.DB;
import uk.co.bjdavies.app.db.models.Ignore;
import uk.co.bjdavies.app.db.models.Models;

import java.util.Objects;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: IgnoreCommand.java
 * Compiled Class Name: IgnoreCommand.class
 * Date Created: 07/02/2018
 */

public class IgnoreCommand implements Command
{

    @Override
    public String[] getAliases()
    {
        return new String[]{"ignore"};
    }

    @Override
    public String getDescription()
    {
        return "This will stop the bot from listening on the channel you run the command on.";
    }

    @Override
    public String getUsage()
    {
        return "ignore";
    }

    @Override
    public String getType()
    {
        return "Discord";
    }

    @Override
    public String run(Application application, CommandContext commandContext)
    {
        if (Models.whereFirst(Ignore.class, application, "channelID", Objects.requireNonNull(commandContext.getMessage().getChannel().block()).getId().asString()) == null)
        {
            DB.table("ignores", application).insert("{'guildID': '" + Objects.requireNonNull(commandContext.getMessage().getChannel().block()).getId().asString() + "','channelID': '" + Objects.requireNonNull(commandContext.getMessage().getChannel().block()).getId().asString() + "','ignoredBy':'" + Objects.requireNonNull(commandContext.getMessage().getChannel().block()).getId().asString() + "'}");
        }

        if (Models.whereFirst(Ignore.class, application, "channelID", Objects.requireNonNull(commandContext.getMessage().getChannel().block()).getId().asString()) != null)
        {
            return "I will stop listening for commands on this channel :) use !listen for me to start listening to again.";
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
