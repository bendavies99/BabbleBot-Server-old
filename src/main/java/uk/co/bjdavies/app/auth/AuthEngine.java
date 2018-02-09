package uk.co.bjdavies.app.auth;

import uk.co.bjdavies.app.db.models.User;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: AuthEngine.java
 * Compiled Class Name: AuthEngine.class
 * Date Created: 08/02/2018
 */

public interface AuthEngine
{

    /**
     * This will attempt to authenticate a user based on credentials.
     *
     * @param username - The username.
     * @param password - The password.
     * @param remember - If the user wishes to remember there sign in.
     * @return User | null
     */
    User authenticateWithCredentials(String username, String password, boolean remember);


    /**
     * This will attempt to authenticate a user based on a remember token.
     *
     * @param token - The remember token.
     * @return User | null
     */
    User authenticateWithToken(String token);
}
