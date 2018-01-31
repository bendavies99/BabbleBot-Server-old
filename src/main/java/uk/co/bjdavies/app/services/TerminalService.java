package uk.co.bjdavies.app.services;

import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.cli.CLIController;
import uk.co.bjdavies.app.cli.CLIView;

import java.util.Scanner;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: TerminalService.java
 * Compiled Class Name: TerminalService.class
 * Date Created: 31/01/2018
 */

public class TerminalService implements Service
{

    /**
     * This is so the terminal keeps asking the user for commands.
     */
    private boolean isRunning = false;

    /**
     * This is so I can read what the user has inputted into the terminal.
     */
    private Scanner scanner;

    /**
     * This is the CLIController which will take the user inputs.
     */
    private CLIController cliController;

    /**
     * This is the name of the thread that service will run on.
     *
     * @return String
     */
    @Override
    public String getThreadName()
    {
        return "CLI-MVC-Terminal-Thread";
    }

    /**
     * This is called before the thread is started this is for pre-setup things.
     *
     * @param application - The application instance.
     * @return boolean
     */
    @Override
    public boolean boot(Application application)
    {
        scanner = new Scanner(System.in);
        CLIView cliView = new CLIView();
        cliController = new CLIController(cliView, application);
        isRunning = true;
        cliView.display("Welcome To BabbleBot3.0 Server");
        cliView.display("Enter A Command: ");
        return true;
    }

    /**
     * This is ran when the thread is stopped.
     *
     * @return boolean
     */
    @Override
    public boolean shutdown()
    {
        isRunning = false;
        scanner = null;
        return true;
    }

    /**
     * This is ran when the thread is started.
     */
    @Override
    public void run()
    {
        while (isRunning)
        {
            if (scanner.hasNext())
            {
                String line = scanner.nextLine();
                cliController.onUserMessage(line);
            }
        }
    }

}
