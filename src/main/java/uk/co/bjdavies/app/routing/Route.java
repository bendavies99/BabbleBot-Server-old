package uk.co.bjdavies.app.routing;


import uk.co.bjdavies.app.Application;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: Route.java
 * Compiled Class Name: Route.class
 * Date Created: 30/01/2018
 */

public interface Route
{
    String getName();

    RequestMethod getMethod();

    void onRun(Application application);
}
