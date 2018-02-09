package uk.co.bjdavies.app.auth;

import uk.co.bjdavies.app.db.models.User;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: AuthStrategy.java
 * Compiled Class Name: AuthStrategy.class
 * Date Created: 08/02/2018
 */

public interface AuthStrategy
{
    /**
     * This will try to authenticate the user.
     *
     * @return User | null
     */
    User authenticate();
}
