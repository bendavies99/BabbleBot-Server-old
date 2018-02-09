package uk.co.bjdavies.app.services;

import com.sun.net.httpserver.HttpServer;
import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.routing.RouteHandler;
import uk.co.bjdavies.app.routing.middleware.Middleware;
import uk.co.bjdavies.app.routing.middleware.VerifyJWTTokenMiddleware;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: RoutingService.java
 * Compiled Class Name: RoutingService.class
 * Date Created: 08/02/2018
 */

public class RoutingService implements Service
{
    private Application application;
    private HttpServer httpServer;
    private ExecutorService executor;

    @Override
    public String getThreadName()
    {
        return "WEB-SERVER-ROUTING-SERVICE";
    }

    @Override
    public boolean boot(Application application)
    {
        this.application = application;
        return true;
    }

    @Override
    public boolean shutdown()
    {
        httpServer.stop(1);
        executor.shutdownNow();
        return true;
    }

    @Override
    public void run()
    {
        try
        {
            httpServer = HttpServer.create(new InetSocketAddress(80), 30);

            List<Middleware> middleware = new ArrayList<>();
            middleware.add(new VerifyJWTTokenMiddleware());

            httpServer.createContext("/", new RouteHandler(application, middleware));

            httpServer.setExecutor(executor = Executors.newFixedThreadPool(30));
            httpServer.start();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
