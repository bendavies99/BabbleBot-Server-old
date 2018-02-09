package uk.co.bjdavies.app.routing;

import uk.co.bjdavies.app.Application;

import java.util.HashMap;
import java.util.Map;


/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: RouteClosure.java
 * Compiled Class Name: RouteClosure.class
 * Date Created: 08/02/2018
 */

public abstract class RouteClosure
{
    /**
     * A helper variable for a JSON Response or any other responses.
     */
    protected Map<String, Object> objectMap = new HashMap<>();

    /**
     * Handle when the route gets run.
     *
     * @param application - The application instance.
     * @param request     - The request bag.
     * @return Response
     */
    public abstract Response handle(Application application, RequestBag request);
}
