package uk.co.bjdavies.app.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.bjdavies.app.exceptions.BabbleBotException;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: DBRow.java
 * Compiled Class Name: DBRow.class
 * Date Created: 07/02/2018
 */

public class DBRow
{
    /**
     * The map of objects for the row.
     */
    private final Map<String, Object> objects;
    /**
     * The logger implementation for this class.
     */
    Logger logger = LoggerFactory.getLogger(DBRow.class);


    /**
     * The constructor for the the row.
     *
     * @param objects - The columns and their values.
     */
    public DBRow(Map<String, Object> objects)
    {
        this.objects = objects;
    }

    /**
     * This will return the value based on a column name.
     *
     * @param columnName - The column name.
     * @return Object
     */
    public Object get(String columnName)
    {
        if (!objects.containsKey(columnName))
        {
            logger.error("Column does not exist in this table.", new BabbleBotException());
        } else
        {
            return objects.get(columnName);
        }

        return null;
    }


    /**
     * This will return a string based on column name.
     *
     * @param columnName - The column name.
     * @return String
     */
    public String getString(String columnName)
    {
        return String.valueOf(get(columnName));
    }


    /**
     * This will convert the row into a json string.
     *
     * @param hideColumnNames - The columns you want to hide from being added to the string.
     * @return String
     */
    public String toJsonString(String... hideColumnNames)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        objects.entrySet().stream().forEach(e -> {
            if (!Arrays.asList(hideColumnNames).stream().filter(e1 -> e1.toLowerCase().equals(e.getKey().toLowerCase())).findFirst().isPresent())
            {
                stringBuilder.append("\"").append(e.getKey()).append("\":").append((e.getValue() instanceof String) ? "\"" + e.getValue() + "\"" : e.getValue());

                int index = getIndex(objects.keySet(), e.getKey());
                if (!(index == objects.size() - 1))
                {
                    stringBuilder.append(",");
                }
            }
        });
        stringBuilder.append("}");
        return stringBuilder.toString();
    }


    /**
     * This will return the current index of a set.
     *
     * @param set   - The set to check.
     * @param value - The current set's indexes value.
     * @return int
     */
    private int getIndex(Set<?> set, Object value)
    {
        int result = 0;
        for (Object entry : set)
        {
            if (entry.equals(value)) return result;
            result++;
        }
        return -1;
    }

    /**
     * This will return a string representation of the DBRow class.
     *
     * @return String
     */
    @Override
    public String toString()
    {
        return "DBRow{" +
                "objects=" + objects +
                '}';
    }
}
