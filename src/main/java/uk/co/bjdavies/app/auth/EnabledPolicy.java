package uk.co.bjdavies.app.auth;

import uk.co.bjdavies.app.db.models.User;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: EnabledPolicy.java
 * Compiled Class Name: EnabledPolicy.class
 * Date Created: 08/02/2018
 */

public class EnabledPolicy implements AuthPolicy
{
    /**
     * validate if user is activated.
     *
     * @param user - The user
     * @return boolean
     */
    @Override
    public boolean validate(User user)
    {
        return user.getActivated() == 1;
    }
}
