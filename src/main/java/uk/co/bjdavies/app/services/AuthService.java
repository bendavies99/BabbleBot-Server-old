package uk.co.bjdavies.app.services;

import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.auth.AuthFacade;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: AuthService.java
 * Compiled Class Name: AuthService.class
 * Date Created: 08/02/2018
 */

public class AuthService implements Service
{
    private Application application;

    @Override
    public String getThreadName()
    {
        return "AUTH-01";
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
        application.getBindingContainer().addBinding("authFacade", new AuthFacade(application));
    }
}
