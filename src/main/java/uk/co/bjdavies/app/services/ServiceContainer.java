package uk.co.bjdavies.app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.exceptions.BabbleBotException;

import java.util.HashMap;
import java.util.Map;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: ServiceContainer.java
 * Compiled Class Name: ServiceContainer.class
 * Date Created: 30/01/2018
 */

public class ServiceContainer
{
    /**
     * This is the logger implementation for this class.
     */
    private Logger logger = LoggerFactory.getLogger(ServiceContainer.class);


    /**
     * This is the Map that the services will be stored and used.
     */
    private Map<String, Service> services;


    /**
     * This is the Map that will hold all the Threads of started threads.
     */
    private Map<String, Thread> startedThreads;


    /**
     * This is where the services map will be initialized to a HashMap.
     */
    public ServiceContainer()
    {
        services = new HashMap<>();
        startedThreads = new HashMap<>();
    }


    /**
     * This is where you can add a service to the container.
     *
     * @param name    - This is the name of the service.
     * @param service - This is the service you want to add to the container.
     */
    public void addService(String name, Service service)
    {
        if (services.containsKey(name) || services.containsValue(service))
        {
            logger.error("The key or Service is already in this container.", new BabbleBotException());
        } else
        {
            services.put(name, service);
        }
    }


    /**
     * This is where you can remove a service from the container.
     *
     * @param name - This is the name of the service.
     */
    public void removeService(String name)
    {
        if (!services.containsKey(name))
        {
            logger.error("The service does not exist in this container so it cannot be removed.", new BabbleBotException());
        } else
        {
            services.remove(name);
        }
    }


    /**
     * This is where you can start a service that is in the container.
     *
     * @param name        - This is the name of the thread.
     * @param application - This is the application instance.
     */
    public void startService(String name, Application application)
    {
        if (!services.containsKey(name))
        {
            logger.error("Service does not exist in this container so cannot be started.", new BabbleBotException());
        } else if (startedThreads.containsKey(name))
        {
            logger.warn("Service has already been started.");
        } else
        {
            Service service = services.get(name);
            service.boot(application);
            Thread thread = new Thread(service, service.getThreadName());
            startedThreads.put(name, thread);
            thread.start();
        }
    }

    /**
     * This is a helper method to stop all services running.
     */
    public void stopAllServices()
    {
        services.keySet().stream().forEach(this::stopService);
    }


    /**
     * This is where you can stop the service.
     *
     * @param name - The name of the service you wish to stop.
     */
    public void stopService(String name)
    {
        if (!services.containsKey(name))
        {
            logger.error("Service does not exist in this container so cannot be stopped.", new BabbleBotException());
        } else if (!startedThreads.containsKey(name))
        {
            logger.warn("Service has not been started.");

        } else
        {
            Thread thread = startedThreads.get(name);
            try
            {
                thread.join(100);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            services.get(name).shutdown();
            startedThreads.remove(name);
        }
    }
}
