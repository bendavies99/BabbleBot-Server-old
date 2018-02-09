package uk.co.bjdavies.app.crypt.jwt.errors;

import uk.co.bjdavies.app.crypt.jwt.TokenError;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: TokenBlacklisted.java
 * Compiled Class Name: TokenBlacklisted.class
 * Date Created: 08/02/2018
 */

public class TokenBlacklisted implements TokenError
{
    /**
     * This will return that the token has been blacklisted.
     *
     * @return String
     */
    @Override
    public String getReason()
    {
        return "The token requested has been blacklisted.";
    }
}
