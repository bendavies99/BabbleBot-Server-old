package uk.co.bjdavies.app.auth;

import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.crypt.Password;
import uk.co.bjdavies.app.db.DB;
import uk.co.bjdavies.app.db.models.User;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: CredentialsStrategy.java
 * Compiled Class Name: CredentialsStrategy.class
 * Date Created: 08/02/2018
 */

public class CredentialsStrategy implements AuthStrategy
{
    /**
     * username.
     */
    private String username;

    /**
     * password.
     */
    private String password;

    /**
     * The application instance.
     */
    private Application application;


    /**
     * CredentialStrategy construction.
     *
     * @param username    - The username
     * @param password    - The password
     * @param application - The application instance.
     */
    public CredentialsStrategy(String username, String password, Application application)
    {
        this.username = username;
        this.password = password;
        this.application = application;
    }

    /**
     * Attempt to authenticate the user.
     *
     * @return User | null
     */
    @Override
    public User authenticate()
    {
        User user = (User) DB.table("users", application).where("username", username).firstModel(User.class);
        return (user != null) ? (Password.verify(password, user.getPassword())) ? user : null : null;
    }
}
