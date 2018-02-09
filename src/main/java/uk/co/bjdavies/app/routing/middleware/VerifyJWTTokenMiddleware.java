package uk.co.bjdavies.app.routing.middleware;

import uk.co.bjdavies.app.crypt.jwt.JWTTokenFacade;
import uk.co.bjdavies.app.crypt.jwt.Token;
import uk.co.bjdavies.app.crypt.jwt.TokenError;
import uk.co.bjdavies.app.routing.JSONResponse;
import uk.co.bjdavies.app.routing.RequestBag;
import uk.co.bjdavies.app.routing.Route;
import uk.co.bjdavies.app.routing.RouteHandler;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: VerifyJWTTokenMiddleware.java
 * Compiled Class Name: VerifyJWTTokenMiddleware.class
 * Date Created: 09/02/2018
 */

public class VerifyJWTTokenMiddleware implements Middleware
{

    /**
     * This will do a middleware check.
     *
     * @param route        - The route being checked.
     * @param request      - The request bag.
     * @param routeHandler - The route handler instance.
     * @return boolean
     */
    @Override
    public boolean run(Route route, RequestBag request, RouteHandler routeHandler)
    {
        if (route.getName().equals("/ping") || route.getName().equals("/login")) return true;

        if (request.hasParameter("authToken"))
        {

            JWTTokenFacade tokenFacade = (JWTTokenFacade) request.getApplication().getBindingContainer().getBinding("jwt");

            TokenError error = tokenFacade.validateToken(request.getParameter("authToken"));

            if (error == null)
            {
                Token token = new Token(request.getParameter("authToken"), request.getApplication());
                token.blacklist();
                request.setParameter("authToken", tokenFacade.createNewToken(token.getPayload()).getTokenCode());
                return true;
            }

            JSONResponse jsonResponse = new JSONResponse(request.getExchange()).add("error", "403").add("reason", error.getReason());

            routeHandler.respond(jsonResponse, request.getExchange(), 403);
            return false;

        }

        return false;
    }
}
