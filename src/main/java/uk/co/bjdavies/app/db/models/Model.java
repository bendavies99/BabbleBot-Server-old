package uk.co.bjdavies.app.db.models;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: Model.java
 * Compiled Class Name: Model.class
 * Date Created: 07/02/2018
 */

public abstract class Model
{
    /**
     * This is the primary key that all models should follow.
     */
    private int id;

    /**
     * This will return the date the model was created.
     */
    private String dateCreated;

    /**
     * This will return when the model was updated.
     */
    private String dateUpdated;

    /**
     * This will return the table name that will used to get the model from the database.
     *
     * @return String
     */
    protected String getTableName()
    {
        return pluralize(this.getClass().getSimpleName());
    }

    /**
     * This will hide the columns when the model get converted to json.
     *
     * @return String[]
     */
    protected String[] getHiddenItemsFromJson()
    {
        return new String[0];
    }

    /**
     * This will return the primary key.
     *
     * @return String
     */
    protected String getPrimaryKey()
    {
        return "id";
    }

    /**
     * This will return the ID of the model.
     *
     * @return int
     */
    public int getID()
    {
        return id;
    }

    /**
     * This will return when the model was created.
     *
     * @return String
     */
    public String getDateCreated()
    {
        return dateCreated;
    }


    /**
     * This will return when the model was updated.
     *
     * @return String
     */
    public String getDateUpdated()
    {
        return dateUpdated;
    }

    /**
     * This will return a plural version of a word.
     *
     * @param singular - The singular version of the word.
     * @return String
     */
    private String pluralize(String singular)
    {
        char lastLetter = singular.charAt(singular.length() - 1);
        switch (lastLetter)
        {
            case 'y':
                return singular.substring(0, singular.length() - 1) + "ies";
            case 's':
                return singular + "es";
            default:
                return singular + "s";
        }
    }
}
