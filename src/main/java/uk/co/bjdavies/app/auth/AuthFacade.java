package uk.co.bjdavies.app.auth;

import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.binding.Bindable;
import uk.co.bjdavies.app.db.models.User;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: AuthFacade.java
 * Compiled Class Name: AuthFacade.class
 * Date Created: 08/02/2018
 */

public class AuthFacade implements Bindable
{
    /**
     * The engine used to authenticate the user.
     */
    private AuthEngine engine;

    /**
     * The authenticatedUser.
     */
    private User authenticatedUser;

    /**
     * This is where the AuthFacade gets constructed.
     *
     * @param application - The application instance.
     */
    public AuthFacade(Application application)
    {
        this.engine = new AuthGuard(application);
    }

    /**
     * Attempt a login with credentials.
     *
     * @param username - The username.
     * @param password - The password.
     * @param remember - remember the auth?
     * @return User | null
     */
    public User attempt(String username, String password, boolean remember)
    {
        authenticatedUser = engine.authenticateWithCredentials(username, password, remember);
        return authenticatedUser;
    }


    /**
     * Attempt a login with a remember token.
     *
     * @param token - THe remember token.
     * @return User | null
     */
    public User attemptWithToken(String token)
    {
        authenticatedUser = engine.authenticateWithToken(token);
        return authenticatedUser;
    }

    /**
     * Is requester a guest?
     *
     * @return boolean
     */
    public boolean isGuest()
    {
        return authenticatedUser == null;
    }

}
