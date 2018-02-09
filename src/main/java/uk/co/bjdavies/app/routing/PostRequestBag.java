package uk.co.bjdavies.app.routing;

import com.sun.net.httpserver.HttpExchange;
import uk.co.bjdavies.app.Application;

import java.util.Map;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: PostRequestBag.java
 * Compiled Class Name: PostRequestBag.class
 * Date Created: 09/02/2018
 */

public class PostRequestBag extends RequestBag
{
    /**
     * The inputted keys and values from the request body.
     */
    private Map<String, String> inputs;

    /**
     * PostRequestBag Construction
     *
     * @param parameters  - The url parameters.
     * @param exchange    - The exchange.
     * @param application - The application instance.
     * @param inputs      - The inputted keys and values from the request body.
     */
    public PostRequestBag(Map<String, String> parameters, HttpExchange exchange, Application application, Map<String, String> inputs)
    {
        super(parameters, exchange, application);

        this.inputs = inputs;
    }


    /**
     * Return a input.
     *
     * @param inputName - the name of the input.
     * @return String
     */
    public String getInput(String inputName)
    {
        return inputs.get(inputName);
    }


    /**
     * Does the input exist.
     *
     * @param inputName - The name of the input.
     * @return boolean
     */
    public boolean hasInput(String inputName)
    {
        return inputs.containsKey(inputName);
    }

}
