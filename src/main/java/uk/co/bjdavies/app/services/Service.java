package uk.co.bjdavies.app.services;

import uk.co.bjdavies.app.Application;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: Service.java
 * Compiled Class Name: Service.class
 * Date Created: 31/01/2018
 */

public interface Service extends Runnable
{
    /**
     * This is the name of the thread that service will run on.
     *
     * @return String
     */
    String getThreadName();


    /**
     * This is called before the thread is started this is for pre-setup things.
     *
     * @param application - The application instance.
     * @return boolean
     */
    boolean boot(Application application);


    /**
     * This is ran when the thread is stopped.
     *
     * @return boolean
     */
    boolean shutdown();
}
