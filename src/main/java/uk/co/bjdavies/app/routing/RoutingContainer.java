package uk.co.bjdavies.app.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.annotations.RouteMethod;
import uk.co.bjdavies.app.exceptions.BabbleBotException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: RoutingContainer.java
 * Compiled Class Name: RoutingContainer.class
 * Date Created: 30/01/2018
 */

public class RoutingContainer
{

    /**
     * This is the logger implementation for this class.
     */
    Logger logger = LoggerFactory.getLogger(RoutingContainer.class);


    /**
     * This is the the Map of Routes that will be held in this container.
     */
    private Map<String, Route> routes;


    /**
     * This is where the routes Map will get initialized with the HashMap concrete class of Map.
     */
    public RoutingContainer()
    {
        routes = new HashMap<>();
    }


    /**
     * This is where you are able to add routes to the routing container.
     *
     * @param route - This is the route that you wish to add.
     */
    public void addRoute(Route route)
    {
        if (routes.containsKey(route.getName() + ":" + route.getMethod()) || routes.containsValue(route))
        {
            logger.error("The key or route is already in the container", new BabbleBotException());
        } else
        {
            routes.put(route.getName() + ":" + route.getMethod(), route);
        }
    }

    public void addRoutes(Class<?> routesClass)
    {
        try
        {
            final Object routes = routesClass.newInstance();
            for (final Method method : routesClass.getDeclaredMethods())
            {
                if (method.isAnnotationPresent(RouteMethod.class) && method.getReturnType() == Response.class)
                {
                    List<Class> parameterTypes = new ArrayList<>(Arrays.asList(method.getParameterTypes()));
                    if (parameterTypes.contains(Application.class) && parameterTypes.contains(RequestBag.class))
                    {
                        RouteMethod annotation = method.getAnnotation(RouteMethod.class);

                        addRoute(RouteFactory.makeRoute(annotation.getName(), annotation.getMethod(), new RouteClosure()
                        {
                            @Override
                            public Response handle(Application application, RequestBag request)
                            {
                                try
                                {
                                    return (Response) method.invoke(routes, application, request);
                                } catch (IllegalAccessException | InvocationTargetException e)
                                {
                                    e.printStackTrace();
                                }

                                return null;
                            }
                        }));
                    }
                } else
                {
                    logger.warn(method.getName() + " in " + routesClass.getSimpleName() + " is an invalid route: ROUTE NOT ADDED.");
                }
            }
        } catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

    }


    /**
     * This is where you can remove a route from the container.
     *
     * @param name   - This is the name of the route you wish to remove.
     * @param method - This is the method of the route you wish to remove.
     */
    public void removeRoute(String name, RequestMethod method)
    {
        if (!routes.containsKey(name + ":" + method))
        {
            logger.error("The route specified can not be found in the container.", new BabbleBotException());
        } else
        {
            routes.remove(name);
        }
    }

    /**
     * This is where you can grab the route fro that url that is specified as the name.
     *
     * @param name   - The url of the route.
     * @param method - The method of the route.
     * @return RouteMethod
     */
    public Route getRoute(String name, RequestMethod method)
    {
        if (!routes.containsKey(name + ":" + method))
        {
            logger.error("The route specified can not be found in the container.", new BabbleBotException());
        } else
        {
            return routes.get(name + ":" + method);
        }
        return null;
    }

    /**
     * This is the toString of the class will show the classes contents.
     *
     * @return String
     */
    @Override
    public String toString()
    {
        return "RoutingContainer{" +
                "routes=" + routes +
                '}';
    }

    public Collection<Route> getRoutes()
    {
        return routes.values();
    }
}
