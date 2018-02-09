package uk.co.bjdavies.app.auth;

import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.db.DB;
import uk.co.bjdavies.app.db.DBRow;
import uk.co.bjdavies.app.db.models.Models;
import uk.co.bjdavies.app.db.models.User;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: TokenStrategy.java
 * Compiled Class Name: TokenStrategy.class
 * Date Created: 08/02/2018
 */

public class TokenStrategy implements AuthStrategy
{

    /**
     * remember token.
     */
    private String token;

    /**
     * The application instance.
     */
    private Application application;

    /**
     * TokenStrategy construction.
     *
     * @param token       - The remember token.
     * @param application - The application instance.
     */
    public TokenStrategy(String token, Application application)
    {
        this.token = token;
        this.application = application;
    }

    /**
     * tries to authenticate user based on token.
     *
     * @return User | null
     */
    @Override
    public User authenticate()
    {
        DBRow row = DB.table("remember_tokens", application).where("token", token).first();

        if (row != null)
        {
            return (User) Models.whereFirst(User.class, application, "id", row.getString("userId"));
        }

        return null;
    }
}
