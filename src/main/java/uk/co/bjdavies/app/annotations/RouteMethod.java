package uk.co.bjdavies.app.annotations;

import uk.co.bjdavies.app.routing.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: RouteMethod.java
 * Compiled Class Name: RouteMethod.class
 * Date Created: 08/02/2018
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RouteMethod
{
    /**
     * This is the name of the route which is the url of the route.
     *
     * @return String
     */
    String getName();


    /**
     * This is the method of request that the route accepts.
     *
     * @return RequestMethod
     */
    RequestMethod getMethod();
}
