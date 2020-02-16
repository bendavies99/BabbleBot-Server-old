package uk.co.bjdavies.app.crypt;


import org.apache.commons.lang3.RandomStringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: Hasher.java
 * Compiled Class Name: Hasher.class
 * Date Created: 08/02/2018
 */

public class Hasher
{
    /**
     * This will make a hashed string.
     *
     * @param rawString - The string to be hashed.
     * @return String
     */
    public static String make(String rawString)
    {
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            byte[] hashed = messageDigest.digest(rawString.getBytes("UTF-8"));

            return new String(hashed);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * This will verify a hashed string.
     *
     * @param rawString    - The string that isn't hashed.
     * @param hashedString - The hashed string.
     * @return boolean
     */
    public static boolean verify(String rawString, String hashedString)
    {
        return make(rawString).equals(hashedString);
    }

    /**
     * This will make a random string.
     *
     * @param amount -  the amount of characters in the string.
     * @return String
     */
    public static String make(int amount)
    {
        return RandomStringUtils.randomAlphanumeric(amount);
    }
}
