package uk.co.bjdavies.app.cli.commands;

import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.commands.Command;
import uk.co.bjdavies.app.commands.CommandContext;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: TestCommand.java
 * Compiled Class Name: TestCommand.class
 * Date Created: 31/01/2018
 */

public class TestCommand implements Command
{
    @Override
    public String[] getAliases()
    {
        return new String[]{"test"};
    }

    @Override
    public String getDescription()
    {
        return "Just a Test";
    }

    @Override
    public String getUsage()
    {
        return "!test";
    }

    @Override
    public String getType()
    {
        return "Discord";
    }

    @Override
    public String run(Application application, CommandContext commandContext)
    {
        return "This is just a test nothing more. What are you playing? $(audioDJ.currentlyPlaying)";
    }

    @Override
    public boolean validateUsage(CommandContext commandContext)
    {
        return true;
    }
}
