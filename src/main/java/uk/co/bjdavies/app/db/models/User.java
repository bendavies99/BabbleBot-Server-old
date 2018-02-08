package uk.co.bjdavies.app.db.models;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: User.java
 * Compiled Class Name: User.class
 * Date Created: 07/02/2018
 */

public class User extends Model
{

    private String username;
    private String email;
    private String password;
    private int activated;

    @Override
    protected String[] getHiddenItemsFromJson()
    {
        return new String[]{"password"};
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public int getActivated()
    {
        return activated;
    }
}
