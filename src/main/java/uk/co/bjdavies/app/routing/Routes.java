package uk.co.bjdavies.app.routing;

import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.annotations.RouteMethod;
import uk.co.bjdavies.app.auth.AuthFacade;
import uk.co.bjdavies.app.crypt.jwt.JWTTokenFacade;
import uk.co.bjdavies.app.db.models.User;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: Routes.java
 * Compiled Class Name: Routes.class
 * Date Created: 08/02/2018
 */

public class Routes
{
    @RouteMethod(getName = "/ping", getMethod = RequestMethod.GET)
    public Response ping(Application application, RequestBag request)
    {
        return new JSONResponse(request.getExchange()).add("response", "pong");
    }

    @RouteMethod(getName = "/login", getMethod = RequestMethod.POST)
    public Response login(Application application, RequestBag requestBag)
    {
        PostRequestBag postRequestBag = (PostRequestBag) requestBag;

        if (postRequestBag.hasInput("username") && postRequestBag.hasInput("password"))
        {
            JWTTokenFacade tokenFacade = (JWTTokenFacade) application.getBindingContainer().getBinding("jwt");
            AuthFacade authFacade = (AuthFacade) application.getBindingContainer().getBinding("authFacade");

            User user = authFacade.attempt(postRequestBag.getInput("username"), postRequestBag.getInput("password"), false);

            if (user != null)
                return new JSONResponse(postRequestBag.getExchange()).add("response", "You have logged in.").add("authToken", tokenFacade.createNewToken(String.valueOf(user.getID())).getTokenCode());
            else
                return new JSONResponse(postRequestBag.getExchange()).add("error", "401").add("reason", "Invalid credentials.");
        } else
        {
            return new JSONResponse(postRequestBag.getExchange()).add("error", "500").add("reason", "Invalid arguments supplied.");
        }
    }
}
