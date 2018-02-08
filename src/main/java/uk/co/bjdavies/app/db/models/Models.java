package uk.co.bjdavies.app.db.models;

import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.db.DB;

import java.util.ArrayList;
import java.util.Collection;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: Models.java
 * Compiled Class Name: Models.class
 * Date Created: 07/02/2018
 */

public class Models
{

    /**
     * This will return all the models in a given table.
     *
     * @param modelClass  - The model class you want to get all for.
     * @param application - The application instance.
     * @return Collection
     */
    public static Collection<Model> all(Class<? extends Model> modelClass, Application application)
    {

        try
        {
            Model model = modelClass.newInstance();
            DB dbInstance = DB.table(model.getTableName(), application);
            return dbInstance.getModels(modelClass);
        } catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * This will return all the models based on a where clause.
     *
     * @param modelClass  - The model class.
     * @param application - The application instance.
     * @param key         - The key in which to search for.
     * @param value       - The value of the key.
     * @return Collection of Models
     */
    public static Collection<Model> whereAll(Class<? extends Model> modelClass, Application application, String key, String value)
    {
        return whereAll(modelClass, application, key, "=", value);
    }

    /**
     * This will return all the models based on a where clause.
     *
     * @param modelClass  - The model class.
     * @param application - The application instance.
     * @param key         - The key in which to search for.
     * @param operator    - the operator for the where clause.
     * @param value       - The value of the key.
     * @return Collection of Models
     */
    public static Collection<Model> whereAll(Class<? extends Model> modelClass, Application application, String key, String operator, String value)
    {
        try
        {
            Model model = modelClass.newInstance();
            DB dbInstance = DB.table(model.getTableName(), application).where(key, operator, value);
            return dbInstance.getModels(modelClass);
        } catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * This will return one Model from the database the first one found.
     *
     * @param modelClass  - The model class.
     * @param application - The application instance.
     * @param key         - The key in which to search for.
     * @param value       - The value of the key.
     * @return Model
     */
    public static Model whereFirst(Class<? extends Model> modelClass, Application application, String key, String value)
    {
        return whereFirst(modelClass, application, key, "=", value);
    }

    /**
     * This will return one Model from the database the first one found.
     *
     * @param modelClass  - The model class.
     * @param application - The application instance.
     * @param key         - The key in which to search for.
     * @param operator    - the operator for the where clause.
     * @param value       - The value of the key.
     * @return Model
     */
    public static Model whereFirst(Class<? extends Model> modelClass, Application application, String key, String operator, String value)
    {
        try
        {
            Model model = modelClass.newInstance();
            DB dbInstance = DB.table(model.getTableName(), application).where(key, operator, value);
            return dbInstance.firstModel(modelClass);
        } catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
