package uk.co.bjdavies.app.commands;

import uk.co.bjdavies.app.Application;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: Command.java
 * Compiled Class Name: Command.class
 * Date Created: 31/01/2018
 */

public interface Command
{
    /**
     * The aliases of the command.
     *
     * @return String[]
     */
    String[] getAliases();


    /**
     * The Description for the command.
     *
     * @return String
     */
    String getDescription();

    /**
     * The Usage for the command.
     *
     * @return String
     */
    String getUsage();


    /**
     * The type of command (Terminal, Discord).
     *
     * @return String
     */
    String getType();


    /**
     * This is the execution point for the command.
     *
     * @param application    - The application instance.
     * @param commandContext - The command context for all command parameters and values.
     * @return String
     */
    String run(Application application, CommandContext commandContext);


    /**
     * This is to make sure that the command the user inputted is valid.
     *
     * @param commandContext - The command context for all command parameters and values.
     * @return boolean
     */
    boolean validateUsage(CommandContext commandContext);
}
