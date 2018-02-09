package uk.co.bjdavies.app.auth;

import uk.co.bjdavies.app.db.models.User;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: AuthPolicy.java
 * Compiled Class Name: AuthPolicy.class
 * Date Created: 08/02/2018
 */

public interface AuthPolicy
{
    /**
     * This will validate the policy against the user.
     *
     * @param user - The user
     * @return boolean
     */
    boolean validate(User user);
}
