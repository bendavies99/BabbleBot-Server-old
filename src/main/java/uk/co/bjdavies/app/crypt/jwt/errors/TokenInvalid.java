package uk.co.bjdavies.app.crypt.jwt.errors;

import uk.co.bjdavies.app.crypt.jwt.TokenError;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: TokenInvalid.java
 * Compiled Class Name: TokenInvalid.class
 * Date Created: 08/02/2018
 */

public class TokenInvalid implements TokenError
{
    /**
     * This will return that the token is invalid.
     *
     * @return String
     */
    @Override
    public String getReason()
    {
        return "The token requested is invalid.";
    }
}
