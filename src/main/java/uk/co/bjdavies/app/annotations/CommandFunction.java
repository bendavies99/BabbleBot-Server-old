package uk.co.bjdavies.app.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: CommandFunction.java
 * Compiled Class Name: CommandFunction.class
 * Date Created: 30/01/2018
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandFunction
{
    /**
     * The aliases of the command.
     *
     * @return String[]
     */
    String[] aliases();


    /**
     * The Description for the command.
     *
     * @return String
     */
    String description();

    /**
     * The Usage for the command.
     *
     * @return String
     */
    String usage();


    /**
     * The type of command (Terminal, Discord).
     *
     * @return String
     */
    String type() default "Discord";

    /**
     * A list of required params for the command.
     *
     * @return String[]
     */
    String[] requiredParams() default "";
}
