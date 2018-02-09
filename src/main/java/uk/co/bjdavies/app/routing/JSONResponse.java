package uk.co.bjdavies.app.routing;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: JSONResponse.java
 * Compiled Class Name: JSONResponse.class
 * Date Created: 08/02/2018
 */

public class JSONResponse implements Response
{

    /**
     * The map of key and values for the json response.
     */
    private Map<String, Object> objects = new HashMap<>();

    /**
     * JSONResponse construction.
     *
     * @param objects      - The objects to be converted to json.
     * @param httpExchange - the exchange.
     */
    public JSONResponse(Map<String, Object> objects, HttpExchange httpExchange)
    {
        this.objects = objects;
        httpExchange.getResponseHeaders().add("Content-Type", String.format("application/json; charset=%s", StandardCharsets.UTF_8));
    }

    /**
     * This will create a empty JSONResponse.
     *
     * @param httpExchange - the exchange.
     */
    public JSONResponse(HttpExchange httpExchange)
    {
        httpExchange.getResponseHeaders().add("Content-Type", String.format("application/json; charset=%s", StandardCharsets.UTF_8));
    }

    /**
     * Add key and value to response.
     *
     * @param key   - The key.
     * @param value - The value.
     * @return JSONResponse
     */
    public JSONResponse add(String key, String value)
    {
        objects.put(key, value);
        return this;
    }

    /**
     * Return the response data.
     *
     * @return String
     */
    @Override
    public Object getResponseData()
    {
        return new Gson().toJson(objects);
    }
}
