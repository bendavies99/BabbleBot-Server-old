package uk.co.bjdavies.app.services;

import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.crypt.jwt.JWTTokenFacade;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: JWTTokenService.java
 * Compiled Class Name: JWTTokenService.class
 * Date Created: 08/02/2018
 */

public class JWTTokenService implements Service
{

    private Application application;

    @Override
    public String getThreadName()
    {
        return "JWT-Service";
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
        return true;
    }

    @Override
    public void run()
    {
        application.getBindingContainer().addBinding("jwt", new JWTTokenFacade(application));
    }
}
