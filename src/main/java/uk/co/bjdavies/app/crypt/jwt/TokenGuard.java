package uk.co.bjdavies.app.crypt.jwt;

import uk.co.bjdavies.app.Application;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: TokenGuard.java
 * Compiled Class Name: TokenGuard.class
 * Date Created: 08/02/2018
 */

public class TokenGuard implements TokenEngine
{
    /**
     * The application instance.
     */
    private Application application;

    /**
     * TokenGuard construction.
     *
     * @param application - The application instance.
     */
    public TokenGuard(Application application)
    {
        this.application = application;
    }

    /**
     * create a token.
     *
     * @param payload - The token's content.
     * @return Token
     */
    @Override
    public Token create(String payload)
    {
        return new Token().create(payload, application);
    }

    /**
     * validate a token.
     *
     * @param tokenCode - The token code.
     * @return TokenError | null
     */
    @Override
    public TokenError validate(String tokenCode)
    {
        return new Token().validate(tokenCode, application);
    }
}
