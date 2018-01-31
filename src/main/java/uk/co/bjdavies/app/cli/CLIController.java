package uk.co.bjdavies.app.cli;


import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.commands.CommandDispatcher;
import uk.co.bjdavies.app.commands.TerminalMessageParser;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: CLIController.java
 * Compiled Class Name: CLIController.class
 * Date Created: 31/01/2018
 */

public class CLIController
{
    /**
     * The view that the user will see.
     */
    private CLIView cliView;

    /**
     * The application instance.
     */
    private Application application;

    /**
     * This is the constructor for the CLIController.
     *
     * @param cliView     - The user's view.
     * @param application - The application instance.
     */
    public CLIController(CLIView cliView, Application application)
    {
        this.cliView = cliView;
        this.application = application;
    }

    /**
     * This is where the command will be parsed and then executed and then the response will be sent to the user.
     *
     * @param message - The inputted message from the user.
     */
    public void onUserMessage(String message)
    {
        cliView.display(((CommandDispatcher) (application.getBindingContainer().getBinding("cmdDispatcher"))).execute(new TerminalMessageParser(), message, application));
        cliView.display("Enter A Command: ");
    }


}
