package uk.co.bjdavies.app.crypt.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.RandomStringUtils;
import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.crypt.jwt.errors.TokenBlacklisted;
import uk.co.bjdavies.app.crypt.jwt.errors.TokenInvalid;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: Token.java
 * Compiled Class Name: Token.class
 * Date Created: 08/02/2018
 */

public class Token
{
    /**
     * All of the tokens that have been blacklisted.
     */
    private static final List<String> blacklistedTokens = new ArrayList<>();
    /**
     * RandomSalt for security.
     */
    private static final String randomSalt = RandomStringUtils.randomAscii(16);
    /**
     * The token code.
     */
    private String tokenCode;
    /**
     * When the token will expire.
     */
    private Date expiry;
    /**
     * The token content.
     */
    private String payload;

    /**
     * Token construction.
     *
     * @param tokenCode   - The token code.
     * @param application - The application instance.
     */
    public Token(String tokenCode, Application application)
    {
        this.tokenCode = tokenCode;

        validate(tokenCode, application);
    }

    /**
     * Blank Token instance.
     */
    public Token()
    {
    }


    /**
     * This will create a token
     *
     * @param payload     - The token's contents.
     * @param expiry      - The token's expiry.
     * @param application - The application instance.
     */
    private Token(String payload, Date expiry, Application application)
    {
        this.expiry = expiry;
        this.payload = payload;

        try
        {
            Algorithm algorithm = Algorithm.HMAC256(application.getConfig().getSystemConfig().getJWTTokenPassword() + randomSalt);

            tokenCode = JWT.create()
                    .withIssuer("BabbleBot3.0")
                    .withClaim("content", payload)
                    .withExpiresAt(expiry)
                    .sign(algorithm);

        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Blacklist a token.
     *
     * @param token - The token code.
     */
    public static void blacklist(String token)
    {
        blacklistedTokens.add(token);
    }

    /**
     * Create a token.
     *
     * @param payload     - The token content.
     * @param application - The application instance.
     * @return Token
     */
    public Token create(String payload, Application application)
    {

        return new Token(payload, Date.from(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC)), application);
    }

    /**
     * Create a token no payload.
     *
     * @param application - The application instance.
     * @return Token
     */
    public Token create(Application application)
    {
        return create("", application);
    }

    /**
     * Blacklist current token.
     */
    public void blacklist()
    {
        blacklistedTokens.add(tokenCode);
    }

    /**
     * Validate token
     *
     * @param tokenCode   - The token code.
     * @param application - The application instance.
     * @return TokenError | null
     */
    public TokenError validate(String tokenCode, Application application)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(application.getConfig().getSystemConfig().getJWTTokenPassword() + randomSalt);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("BabbleBot3.0")
                    .build();

            try
            {

                if (blacklistedTokens.stream().filter(e -> e.equals(tokenCode)).findAny().isPresent())
                {
                    return new TokenBlacklisted();
                }

                DecodedJWT jwt = verifier.verify(tokenCode);

                payload = jwt.getClaim("content").asString();
                expiry = jwt.getExpiresAt();
                this.tokenCode = tokenCode;

            } catch (JWTVerificationException e)
            {
                return new TokenInvalid();
            }


        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Return token code
     *
     * @return String
     */
    public String getTokenCode()
    {
        return tokenCode;
    }

    /**
     * Return token expiry.
     *
     * @return Date
     */
    public Date getExpiry()
    {
        return expiry;
    }


    /**
     * Return token content.
     *
     * @return String
     */
    public String getPayload()
    {
        return payload;
    }
}
