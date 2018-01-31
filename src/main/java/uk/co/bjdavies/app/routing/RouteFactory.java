package uk.co.bjdavies.app.routing;


import uk.co.bjdavies.app.Application;

import java.util.function.Consumer;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: RouteFactory.java
 * Compiled Class Name: RouteFactory.class
 * Date Created: 30/01/2018
 */

public class RouteFactory
{

    /**
     * This is the factory method to create a GET route.
     *
     * @param name  - the url of the route.
     * @param onRun - the function that will get ran when the route is called.
     * @return Route
     */
    public static Route makeGetRoute(String name, Consumer<Application> onRun)
    {
        return makeRoute(name, RequestMethod.GET, onRun);
    }


    /**
     * This is the factory method to create a POST route.
     *
     * @param name  - the url of the route.
     * @param onRun - the function that will get ran when the route is called.
     * @return Route
     */
    public static Route makePostRoute(String name, Consumer<Application> onRun)
    {
        return makeRoute(name, RequestMethod.POST, onRun);
    }


    /**
     * This is the factory method to create a PUT route.
     *
     * @param name  - the url of the route.
     * @param onRun - the function that will get ran when the route is called.
     * @return Route
     */
    public static Route makePutRoute(String name, Consumer<Application> onRun)
    {
        return makeRoute(name, RequestMethod.PUT, onRun);
    }


    /**
     * This is the factory method to create a PATCH route.
     *
     * @param name  - the url of the route.
     * @param onRun - the function that will get ran when the route is called.
     * @return Route
     */
    public static Route makePatchRoute(String name, Consumer<Application> onRun)
    {
        return makeRoute(name, RequestMethod.PATCH, onRun);
    }


    /**
     * This is the factory method to create a DELETE route.
     *
     * @param name  - the url of the route.
     * @param onRun - the function that will get ran when the route is called.
     * @return Route
     */
    public static Route makeDeleteRoute(String name, Consumer<Application> onRun)
    {
        return makeRoute(name, RequestMethod.DELETE, onRun);
    }


    /**
     * This is the main factory method to create a route.
     *
     * @param name   - the name of the route which is the url of the route.
     * @param method - the request method that route will work on.
     * @param onRun  - the function that will get run when the route has ran.
     * @return Route
     */
    private static Route makeRoute(String name, RequestMethod method, Consumer<Application> onRun)
    {
        return new Route()
        {
            @Override
            public String getName()
            {
                return name;
            }

            @Override
            public RequestMethod getMethod()
            {
                return method;
            }

            @Override
            public void onRun(Application application)
            {
                onRun.accept(application);
            }
        };
    }
}
