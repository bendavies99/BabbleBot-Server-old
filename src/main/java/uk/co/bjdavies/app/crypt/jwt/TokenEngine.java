package uk.co.bjdavies.app.crypt.jwt;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: TokenEngine.java
 * Compiled Class Name: TokenEngine.class
 * Date Created: 08/02/2018
 */

public interface TokenEngine
{
    /**
     * create a token.
     *
     * @param payload - The token's content.
     * @return Token
     */
    Token create(String payload);

    /**
     * validate a token.
     *
     * @param tokenCode - The token code.
     * @return TokenError | null
     */
    TokenError validate(String tokenCode);
}
