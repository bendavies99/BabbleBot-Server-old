package uk.co.bjdavies.app.crypt;

import org.mindrot.jbcrypt.BCrypt;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: Password.java
 * Compiled Class Name: Password.class
 * Date Created: 08/02/2018
 */

public class Password
{

    /**
     * This will make a Bcrypt password.
     *
     * @param rawString - The raw password.
     * @return String
     */
    public static String make(String rawString)
    {

        return BCrypt.hashpw(rawString, BCrypt.gensalt(10));
    }

    /**
     * verify a password.
     *
     * @param rawString    - the raw password.
     * @param hashedString - hte hashed password.s
     * @return boolean
     */
    public static boolean verify(String rawString, String hashedString)
    {
        return BCrypt.checkpw(rawString, hashedString);
    }
}
