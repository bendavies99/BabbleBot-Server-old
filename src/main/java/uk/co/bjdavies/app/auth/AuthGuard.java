package uk.co.bjdavies.app.auth;

import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.crypt.Hasher;
import uk.co.bjdavies.app.db.DB;
import uk.co.bjdavies.app.db.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: AuthGuard.java
 * Compiled Class Name: AuthGuard.class
 * Date Created: 08/02/2018
 */

public class AuthGuard implements AuthEngine
{
    /**
     * The Strategy to authenticate the user.
     */
    private AuthStrategy authStrategy;

    /**
     * The policies that user has to pass to be authenticated.
     */
    private List<AuthPolicy> authPolicies = new ArrayList<>();

    /**
     * The temporary authenticated user.
     */
    private User user;

    /**
     * The application instance.
     */
    private Application application;


    /**
     * AuthGuard construction.
     *
     * @param application - The application instance.
     */
    public AuthGuard(Application application)
    {
        this.application = application;
        authPolicies.add(new EnabledPolicy());
    }


    /**
     * This will attempt to authenticate a user based on credentials.
     *
     * @param username - The username.
     * @param password - The password.
     * @param remember - If the user wishes to remember there sign in.
     * @return User | null
     */
    @Override
    public User authenticateWithCredentials(String username, String password, boolean remember)
    {
        authStrategy = new CredentialsStrategy(username, password, application);
        this.user = authStrategy.authenticate();
        if (user != null)
        {
            boolean auth = validatePolicies();


            if (auth)
            {
                if (remember)
                {
                    DB.table("remember_tokens", application).insert("{'userId':'" + user.getID() + "', 'token': '" + Hasher.make(64) + "'}");
                }
                return user;
            }
        }

        return null;
    }

    /**
     * This will validate the policies against the user.
     *
     * @return boolean
     */
    private boolean validatePolicies()
    {
        for (AuthPolicy policy : authPolicies)
        {
            if (!policy.validate(user))
                return false;
        }

        return true;
    }


    /**
     * This will attempt to authenticate a user based on a remember token.
     *
     * @param token - The remember token.
     * @return User | null
     */
    @Override
    public User authenticateWithToken(String token)
    {
        authStrategy = new TokenStrategy(token, application);

        this.user = authStrategy.authenticate();
        if (user != null)
        {
            boolean auth = validatePolicies();


            if (auth)
            {
                return user;
            }

        }

        return null;
    }
}
