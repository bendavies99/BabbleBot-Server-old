package uk.co.bjdavies.app.crypt.jwt;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: TokenError.java
 * Compiled Class Name: TokenError.class
 * Date Created: 08/02/2018
 */

public interface TokenError
{
    /**
     * Return a reason for not validating a token.
     *
     * @return String
     */
    String getReason();
}
