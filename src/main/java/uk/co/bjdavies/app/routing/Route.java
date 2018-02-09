package uk.co.bjdavies.app.routing;


import uk.co.bjdavies.app.Application;


/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: RouteMethod.java
 * Compiled Class Name: RouteMethod.class
 * Date Created: 30/01/2018
 */

public interface Route
{
    /**
     * The name of the route, <strong>must be the route URL</strong>!
     *
     * @return String
     */
    String getName();

    /**
     * The request method of the route.
     *
     * @return RequestMethod
     */
    RequestMethod getMethod();

    /**
     * This gets run when the route is ran.
     *
     * @param application - The application instance.
     * @param context     - The request bag.
     * @return Response
     */
    Response onRun(Application application, RequestBag context);
}
