package uk.co.bjdavies.app.cli.commands;

import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.commands.Command;
import uk.co.bjdavies.app.commands.CommandContext;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: ExitCommand.java
 * Compiled Class Name: ExitCommand.class
 * Date Created: 31/01/2018
 */

public class ExitCommand implements Command
{
    @Override
    public String[] getAliases()
    {
        return new String[]{"quit", "exit"};
    }

    @Override
    public String getDescription()
    {
        return "To quit the program.";
    }

    @Override
    public String getUsage()
    {
        return "exit | quit";
    }

    @Override
    public String getType()
    {
        return "Terminal";
    }

    @Override
    public String run(Application application, CommandContext commandContext)
    {
        application.requestShutDown();
        return "Program is quitting";
    }

    @Override
    public boolean validateUsage(CommandContext commandContext)
    {
        return true;
    }
}
