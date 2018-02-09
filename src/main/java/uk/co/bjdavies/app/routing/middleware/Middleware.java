package uk.co.bjdavies.app.routing.middleware;

import uk.co.bjdavies.app.routing.RequestBag;
import uk.co.bjdavies.app.routing.Route;
import uk.co.bjdavies.app.routing.RouteHandler;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: Middleware.java
 * Compiled Class Name: Middleware.class
 * Date Created: 09/02/2018
 */

public interface Middleware
{
    /**
     * This will do a middleware check.
     *
     * @param route        - The route being checked.
     * @param request      - The request bag.
     * @param routeHandler - The route handler instance.
     * @return boolean
     */
    boolean run(Route route, RequestBag request, RouteHandler routeHandler);
}
