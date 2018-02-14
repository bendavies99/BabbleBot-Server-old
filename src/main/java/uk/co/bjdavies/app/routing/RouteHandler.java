package uk.co.bjdavies.app.routing;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.routing.middleware.Middleware;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: RouteHandler.java
 * Compiled Class Name: RouteHandler.class
 * Date Created: 08/02/2018
 */

public class RouteHandler implements HttpHandler
{

    /**
     * Application instance.
     */
    private Application application;

    /**
     * The list of middleware.
     */
    private List<Middleware> middleware = new ArrayList<>();

    /**
     * Has the exchange responded back to the client.
     */
    private boolean hasResponded = false;

    /**
     * RouteHandler construction
     *
     * @param application - The application instance.
     * @param middleware  - The list of middleware.
     */
    public RouteHandler(Application application, List<Middleware> middleware)
    {
        this.application = application;
        this.middleware = middleware;
    }

    /**
     * This will handle the request that has been made to the server.
     *
     * @param httpExchange - The details of the request and method to respond.
     * @throws IOException - if a io error occurs.
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        hasResponded = false;
        final boolean[] routeFound = {false};
        application.getRoutingContainer().getRoutes().stream().filter(route -> route.getName().split("/").length == httpExchange.getRequestURI().getPath().split("/").length && route.getMethod().toString().toLowerCase().equals(httpExchange.getRequestMethod().toLowerCase())).forEach(route -> {
            String path = httpExchange.getRequestURI().getPath();
            String[] parts = route.getName().split("/");

            if (path.contains("?")) path = path.replace(path.substring(path.indexOf("?")), "");

            String[] pathParts = path.split("/");
            Map<String, String> parameters = new HashMap<>();
            boolean valid = false;
            for (int i = 0; i < parts.length; i++)
            {
                if (parts[i].toLowerCase().equals(pathParts[i].toLowerCase()))
                {
                    valid = true;
                } else
                {
                    if (parts[i].matches("\\{([a-zA-Z0-9]+)\\}"))
                    {
                        parameters.put(parts[i].replace("{", "").replace("}", ""), pathParts[i]);
                        valid = true;
                    } else
                    {
                        valid = false;
                    }
                }
            }

            if (valid)
            {

                try
                {


                    if (httpExchange.getRequestHeaders().containsKey("Bearer"))
                    {
                        parameters.put("authToken", httpExchange.getRequestHeaders().getFirst("Bearer"));
                    }

                    if (httpExchange.getRequestURI().getQuery() != null)
                    {
                        String[] queryParts = httpExchange.getRequestURI().getQuery().split("&");

                        for (String queryPart : queryParts)
                        {
                            String[] kvpair = queryPart.split("=");
                            parameters.put(kvpair[0], kvpair[1]);
                        }
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                RequestBag request;

                if (route.getMethod() != RequestMethod.POST)
                {
                    request = new RequestBag(parameters, httpExchange, application);
                } else
                {
                    Map<String, String> inputs = new HashMap<>();

                    InputStream is = httpExchange.getRequestBody();

                    StringBuilder requestBuffer = new StringBuilder();
                    int rByte;
                    try
                    {
                        while ((rByte = is.read()) != -1)
                        {
                            requestBuffer.append((char) rByte);
                        }

                        is.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    Type type = new TypeToken<Map<String, String>>()
                    {
                    }.getType();
                    inputs = new Gson().fromJson(requestBuffer.toString(), type);

                    request = new PostRequestBag(parameters, httpExchange, application, inputs);
                }

                boolean[] middlewareCheck = new boolean[]{true};

                routeFound[0] = true;
                middleware.stream().forEach(e -> {
                    try
                    {
                        if (!e.run(route, request, this)) middlewareCheck[0] = false;
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                });

                if (middlewareCheck[0])
                {

                    Response response = route.onRun(application, request);

                    if (response instanceof JSONResponse && !route.getName().equals("/ping") && !route.getName().equals("/login"))
                    {
                        ((JSONResponse) response).add("authToken", request.getParameter("authToken"));
                    }

                    httpExchange.getResponseHeaders().add("authToken", request.getParameter("authToken"));

                    respond(response, httpExchange);

                }


            }
        });

        if (!routeFound[0])
        {
            respond(new JSONResponse(httpExchange).add("error", "403").add("reason", "Invalid url."), httpExchange, 403);
        }
    }

    /**
     * This will respond to the client.
     *
     * @param response     - The response to respond to the client.
     * @param httpExchange - The exchange.
     * @param status       - The status code.
     */
    public void respond(Response response, HttpExchange httpExchange, int status)
    {
        try
        {
            if (!hasResponded)
            {
                String resString = String.valueOf(response.getResponseData());
                httpExchange.sendResponseHeaders(status, resString.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(resString.getBytes(StandardCharsets.UTF_8));
                os.close();
                hasResponded = true;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This will respond with the status code of 200
     *
     * @param response     - The response.
     * @param httpExchange - The exchange.
     */
    public void respond(Response response, HttpExchange httpExchange)
    {
        respond(response, httpExchange, 200);
    }
}
