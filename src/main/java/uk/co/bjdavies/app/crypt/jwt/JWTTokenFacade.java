package uk.co.bjdavies.app.crypt.jwt;

import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.binding.Bindable;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: JWTTokenFacade.java
 * Compiled Class Name: JWTTokenFacade.class
 * Date Created: 08/02/2018
 */

public class JWTTokenFacade implements Bindable
{
    /**
     * The engine for tokens
     */
    private TokenEngine tokenEngine;

    /**
     * JWTTokenFacade Construction.
     *
     * @param application - The application instance.
     */
    public JWTTokenFacade(Application application)
    {
        tokenEngine = new TokenGuard(application);
    }

    /**
     * This will create a new token.
     *
     * @param payload - the content of the token.
     * @return Token
     */
    public Token createNewToken(String payload)
    {
        return tokenEngine.create(payload);
    }

    /**
     * This will validate a token.
     *
     * @param tokenCode - the token code.
     * @return TokenError
     */
    public TokenError validateToken(String tokenCode)
    {
        return tokenEngine.validate(tokenCode);
    }
}
