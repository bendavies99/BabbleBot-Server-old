package uk.co.bjdavies.app.routing;


import uk.co.bjdavies.app.Application;


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
     * @param closure - the function that will get ran when the route is called.
     * @return RouteMethod
     */
    public static Route makeGetRoute(String name, RouteClosure closure)
    {
        return makeRoute(name, RequestMethod.GET, closure);
    }


    /**
     * This is the factory method to create a POST route.
     *
     * @param name  - the url of the route.
     * @param closure - the function that will get ran when the route is called.
     * @return RouteMethod
     */
    public static Route makePostRoute(String name, RouteClosure closure)
    {
        return makeRoute(name, RequestMethod.POST, closure);
    }


    /**
     * This is the factory method to create a PUT route.
     *
     * @param name  - the url of the route.
     * @param closure - the function that will get ran when the route is called.
     * @return RouteMethod
     */
    public static Route makePutRoute(String name, RouteClosure closure)
    {
        return makeRoute(name, RequestMethod.PUT, closure);
    }


    /**
     * This is the factory method to create a PATCH route.
     *
     * @param name  - the url of the route.
     * @param closure - the function that will get ran when the route is called.
     * @return RouteMethod
     */
    public static Route makePatchRoute(String name, RouteClosure closure)
    {
        return makeRoute(name, RequestMethod.PATCH, closure);
    }


    /**
     * This is the factory method to create a DELETE route.
     *
     * @param name  - the url of the route.
     * @param closure - the function that will get ran when the route is called.
     * @return RouteMethod
     */
    public static Route makeDeleteRoute(String name, RouteClosure closure)
    {
        return makeRoute(name, RequestMethod.DELETE, closure);
    }


    /**
     * This is the main factory method to create a route.
     *
     * @param name   - the name of the route which is the url of the route.
     * @param method - the request method that route will work on.
     * @param closure  - the function that will get run when the route has ran.
     * @return RouteMethod
     */
    public static Route makeRoute(String name, RequestMethod method, RouteClosure closure)
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
            public Response onRun(Application application, RequestBag context)
            {
                return closure.handle(application, context);
            }
        };
    }
}
