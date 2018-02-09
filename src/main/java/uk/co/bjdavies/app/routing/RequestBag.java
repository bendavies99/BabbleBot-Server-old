package uk.co.bjdavies.app.routing;

import com.sun.net.httpserver.HttpExchange;
import uk.co.bjdavies.app.Application;

import java.util.Map;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: RequestBag.java
 * Compiled Class Name: RequestBag.class
 * Date Created: 09/02/2018
 */

public class RequestBag
{

    /**
     * The url parameters.
     */
    private Map<String, String> parameters;

    /**
     * The exchange.
     */
    private HttpExchange exchange;

    /**
     * Application instance.
     */
    private Application application;

    /**
     * RequestBag construction.
     *
     * @param parameters  - The parameters from the url.
     * @param exchange    - The exchange.
     * @param application - The application instance.
     */
    public RequestBag(Map<String, String> parameters, HttpExchange exchange, Application application)
    {
        this.parameters = parameters;
        this.exchange = exchange;
        this.application = application;
    }

    /**
     * Return a parameter.
     *
     * @param parameter - The parameter name.
     * @return String
     */
    public String getParameter(String parameter)
    {
        return parameters.get(parameter);
    }

    /**
     * Set a Parameter
     *
     * @param parameter - parameter name.
     * @param value     - new value.
     */
    public void setParameter(String parameter, String value)
    {
        parameters.remove(parameter);
        parameters.put(parameter, value);
    }

    /**
     * does parameter exist?
     *
     * @param parameter - parameter name.
     * @return boolean
     */
    public boolean hasParameter(String parameter)
    {
        return parameters.containsKey(parameter);
    }

    /**
     * Return exchange.
     *
     * @return HttpExchange
     */
    public HttpExchange getExchange()
    {
        return exchange;
    }

    /**
     * Return application instance
     *
     * @return Application
     */
    public Application getApplication()
    {
        return application;
    }
}
