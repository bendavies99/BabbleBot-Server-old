package uk.co.bjdavies.app.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.bjdavies.app.Application;
import uk.co.bjdavies.app.db.Table.Blueprint;
import uk.co.bjdavies.app.db.Table.TableBuilder;
import uk.co.bjdavies.app.db.models.Model;
import uk.co.bjdavies.app.exceptions.BabbleBotException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: DB.java
 * Compiled Class Name: DB.class
 * Date Created: 07/02/2018
 */

public class DB
{

    /**
     * Current instance / current connection.
     */
    private static DB instance;
    /**
     * This is the logger implementation for this class.
     */
    private Logger logger = LoggerFactory.getLogger(DB.class);
    /**
     * This will determine the table that gets edited.
     */
    private String tableName = "";
    /**
     * This is the instance of connection that allows us to query the database.
     */
    private Connection connection;
    /**
     * This will be set when the query has been executed.
     */
    private ResultSet resultSet;
    /**
     * This is the query string that will be executed.
     */
    private String queryString = "";
    /**
     * This will hold attribs that will be processed at execution.
     */
    private Map<String, String> bindAttribs = new HashMap<>();
    /**
     * This is how many where statements are in the query.
     */
    private int whereCount = 0;
    /**
     * This is the where statement of the first where.
     */
    private String whereStatement = "";
    /**
     * This determines if the query has been executed.
     */
    private boolean hasExecuted = false;
    /**
     * The cache of the results once processed for optimization.
     */
    private Collection<DBRow> results;
    /**
     * This is the statment that will get executed.
     */
    private Statement preparedStatement;

    /**
     * This is one of the constructors.
     *
     * @param tableName   - The table that we wish to use.
     * @param application - The application instance.
     */
    private DB(String tableName, Application application)
    {
        this(application);
        this.tableName = tableName;
    }


    /**
     * This will get called on constructions of this class.
     *
     * @param application - The application instance.
     */
    private DB(Application application)
    {
        if (instance != null) instance.close();
        instance = this;
        File f = new File(application.getConfig().getSystemConfig().getDBLocation());

        if (!f.exists())
        {
            try
            {
                boolean created = f.createNewFile();
                if (created)
                {

                    TableBuilder builder = new TableBuilder("users", new Blueprint()
                    {
                        @Override
                        public void setup()
                        {
                            this.increments("id");
                            this.string("username", 50);
                            this.string("email", 255);
                            this.string("password", 255);
                            this.integer("activated").defaultValue(0).nullable();

                            this.primaryKey("id");
                            this.uniqueKeys("username", "email");
                        }
                    });

                    String sql = builder.build();
                    DB.sql(sql, application);

                    builder = new TableBuilder("ignores", new Blueprint()
                    {
                        @Override
                        public void setup()
                        {
                            this.increments("id");
                            this.string("guildID", 255);
                            this.string("channelID", 255);
                            this.string("ignoredBy", 255);

                            this.primaryKey("id");
                            this.uniqueKeys("channelID");
                        }
                    });

                    sql = builder.build();
                    DB.sql(sql, application);

                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }


        String dbdet = "jdbc:sqlite:" + application.getConfig().getSystemConfig().getDBLocation();

        try
        {
            connection = DriverManager.getConnection(dbdet);
        } catch (SQLException e)
        {
            logger.error("An error occurred when connecting to the database.");
            e.printStackTrace();
        }
    }


    /**
     * Factory method for making a database connection.
     *
     * @param tableName   - The table you wish to use.
     * @param application - The application instance.
     * @return DB
     */
    public static DB table(String tableName, Application application)
    {
        DB db = new DB(tableName, application);
        db.queryString = "SELECT * FROM " + tableName;
        return db;
    }


    /**
     * Factory method for making a database connection.
     *
     * @param sql         - Custom sql you wish to execute.
     * @param application - The application instance.
     * @return DB
     */
    public static DB sql(String sql, Application application)
    {

        DB db = new DB(application);
        db.queryString = sql;
        db.execute();

        return db;
    }


    /**
     * This will close all connections.
     */
    private void close()
    {
        try
        {
            if (preparedStatement != null)
            {
                preparedStatement.close();
            }

            connection.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * This will run the query and set the result set variable.
     *
     * @return boolean
     */
    private boolean execute()
    {
        if (hasExecuted) return false;
        String toExecute = prepareQuery();
        try
        {
            preparedStatement = connection.createStatement();
            if (toExecute.contains("INSERT INTO") || toExecute.contains("UPDATE") || toExecute.contains("DELETE") || toExecute.contains("CREATE TABLE"))
            {
                preparedStatement.execute(toExecute);

                if (!toExecute.contains("CREATE TABLE"))
                    resultSet = preparedStatement.executeQuery("SELECT * FROM " + tableName);

            } else
            {
                resultSet = preparedStatement.executeQuery(toExecute);
            }

            hasExecuted = true;
            return true;

        } catch (SQLException e)
        {
            logger.warn("An error occurred when executing the query.");
            e.printStackTrace();
        }

        return false;

    }


    /**
     * Prepares the query for execution.
     *
     * @return String
     */
    private String prepareQuery()
    {
        bindAttribs.entrySet().stream().forEach(e -> queryString = queryString.replace(":" + e.getKey(), "'" + e.getValue() + "'"));
        return queryString;
    }


    /**
     * This will return a Collection of the results found.
     *
     * @return Collection of DBRow
     */
    public Collection<DBRow> get()
    {
        if (!hasExecuted) execute();
        if (results == null)
        {
            List<DBRow> rows = new ArrayList<>();
            try
            {
                ResultSetMetaData metaData = resultSet.getMetaData();
                while (resultSet.next())
                {
                    Map<String, Object> objectMap = new HashMap<>();
                    for (int i = 1; i < metaData.getColumnCount() + 1; i++)
                    {
                        String columnName = metaData.getColumnName(i);
                        Object columnValue = resultSet.getObject(columnName);
                        objectMap.put(columnName, columnValue);
                    }

                    rows.add(new DBRow(objectMap));

                    results = rows;
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            return rows;
        } else
        {
            return results;
        }


    }


    /**
     * This will return the results found in JSON form.
     *
     * @return String
     */
    public String getInJson()
    {
        return toJson(get().toArray(new DBRow[get().size()]));
    }


    /**
     * This will convert an Array of DBRows and turn it into a JSON String.
     *
     * @param rowsToConvert - The array of DBRows.
     * @return String
     */
    private String toJson(DBRow... rowsToConvert)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"rows\":[");
        List<DBRow> dbRows = new ArrayList<>();
        dbRows.addAll(Arrays.asList(rowsToConvert));
        dbRows.stream().forEach(e -> {
            stringBuilder.append(e.toJsonString());
            int index = dbRows.indexOf(e);
            if (!(index == dbRows.size() - 1))
            {
                stringBuilder.append(",");
            }
        });
        stringBuilder.append("]}");

        return stringBuilder.toString();
    }


    /**
     * This will return the first element in the results found.
     *
     * @return DBRow
     */
    public DBRow first()
    {

        if (get().stream().findFirst().isPresent()) return get().stream().findFirst().get();

        return null;
    }


    /**
     * This will return the first row in JSON form.
     *
     * @return String
     */
    public String firstInJson()
    {
        return toJson(first());
    }


    /**
     * This will return the last element in the results found.
     *
     * @return DBRow
     */
    public DBRow last()
    {
        Iterator<DBRow> iterator = get().iterator();
        DBRow row = null;
        while (iterator.hasNext())
        {
            row = iterator.next();
        }
        return row;
    }


    /**
     * This will return the last row in JSON form.
     *
     * @return String
     */
    public String lastInJson()
    {
        return toJson(last());
    }


    /**
     * This will return a Collection of Models based on a Model Class.
     *
     * @param modelClass - The class that extends a model.
     * @return Collection
     */
    public Collection<Model> getModels(Class<? extends Model> modelClass)
    {
        Collection<DBRow> rows = get();
        List<Model> models = new ArrayList<>();
        rows.stream().forEach(e -> {
            Model model = new Gson().fromJson(e.toJsonString(), modelClass);
            models.add(model);
        });

        return models;
    }


    /**
     * This will return one model based on the first row in the results found.
     *
     * @param modelClass - The class that extends a model.
     * @return Model
     */
    public Model firstModel(Class<? extends Model> modelClass)
    {
        return (first() != null) ? new Gson().fromJson(first().toJsonString(), modelClass) : null;
    }


    /**
     * This will return the amount of rows in the results found.
     *
     * @return int
     */
    public int count()
    {
        return get().size();
    }


    /**
     * This will return the columns in the table.
     *
     * @return Collection
     */
    public Collection<String> getTableColumns()
    {
        List<String> names = new ArrayList<>();
        try
        {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            for (int i = 0; i < resultSetMetaData.getColumnCount(); i++)
            {
                String columnName = resultSetMetaData.getColumnName(i);
                names.add(columnName);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return names;
    }


    /**
     * This will add a where clause to your query.
     *
     * @param key      - The column name.
     * @param operator - The type of operator (=,<,>,like)
     * @param value    - The value that your looking for.
     * @return DB
     */
    public DB where(String key, String operator, String value)
    {
        String random = RandomStringUtils.randomAlphanumeric(26);
        String statement = key + " " + operator + " :" + random;
        bindAttribs.put(random, value);
        if (whereCount > 0)
        {
            queryString = queryString + " OR " + statement;
        } else
        {
            whereStatement = "WHERE " + statement;
            queryString = queryString + " WHERE " + statement;
        }
        whereCount++;
        return this;
    }


    /**
     * This will do the same as the other where method but default the operator to '='
     *
     * @param key   - The column name.
     * @param value - The value that your looking for.
     * @return DB
     */
    public DB where(String key, String value)
    {
        return where(key, "=", value);
    }

    /**
     * This will insert into the table based on a Map of keys and values.
     *
     * @param insertMap - The Map.
     * @return DB
     */
    public DB insert(Map<String, String> insertMap)
    {
        executeInsert(insertMap.keySet(), insertMap.entrySet());
        return this;
    }

    /**
     * This will insert into the table based on a JSON String.
     *
     * @param json - The JSON String.
     * @return DB
     */
    public DB insert(String json)
    {
        Type type = new TypeToken<Map<String, String>>()
        {
        }.getType();
        Map<String, String> insertMap = new Gson().fromJson(json, type);
        return insert(insertMap);
    }

    /**
     * This will execute the insert and add the row to the table.
     *
     * @param columns  - The columns that are going to be inserted into.
     * @param toInsert - The values of the insert.
     */
    private void executeInsert(Set<String> columns, Set<Map.Entry<String, String>> toInsert)
    {
        queryString = "INSERT INTO " + tableName + " (";
        columns.stream().forEach(e -> {
            int index = getIndex(columns, e);
            if (index == columns.size() - 1)
            {
                queryString = queryString + e + ") VALUES (";
            } else
            {
                queryString = queryString + e + ",";
            }
        });

        toInsert.stream().forEach(e -> {
            int index = getIndex(toInsert, e);
            String random = RandomStringUtils.randomAlphanumeric(26);
            if (index == toInsert.size() - 1)
            {
                queryString = queryString + ":" + random + ")";
            } else
            {
                queryString = queryString + ":" + random + ",";
            }
            bindAttribs.put(random, e.getValue());
        });

        execute();
    }


    /**
     * This will insert into the table based on a Map of keys and values and get the id back.
     *
     * @param insertMap    - The Map.
     * @param idColumnName - The column name of the id.
     * @return String
     */
    public String insertGetId(Map<String, String> insertMap, String idColumnName)
    {
        insert(insertMap);
        return last().getString(idColumnName);
    }


    /**
     * This will insert into the table based on a JSON String and get the id back.
     *
     * @param json - The JSON String.
     * @return String
     */
    public String insertGetId(String json)
    {
        Type type = new TypeToken<Map<String, String>>()
        {
        }.getType();
        Map<String, String> insertMap = new Gson().fromJson(json, type);
        return insertGetId(insertMap);
    }


    /**
     * This will insert into the table based on a Map of keys and values and get the id back but will use id as the column name.
     *
     * @param insertMap - The Map.
     * @return String
     */
    public String insertGetId(Map<String, String> insertMap)
    {
        return insertGetId(insertMap, "id");
    }


    /**
     * This will update a row in the table.
     *
     * @param updateMap - A map to update the table.
     * @return boolean
     */
    public boolean update(Map<String, String> updateMap)
    {

        queryString = "UPDATE " + tableName + " SET ";

        if (whereCount == 1)
        {
            updateMap.entrySet().stream().forEach(e -> {
                int index = getIndex(updateMap.entrySet(), e);
                String random = RandomStringUtils.randomAlphanumeric(26);
                if (index == updateMap.entrySet().size() - 1)
                {
                    queryString = queryString + e.getKey() + "=:" + random;
                } else
                {
                    queryString = queryString + e.getKey() + "=:" + random + ",";
                }
                bindAttribs.put(random, e.getValue());
            });

            return execute();
        } else
        {
            logger.error("Invalid Update statement. Maybe you added more than one WHERE or NONE at all", new BabbleBotException());
        }
        return false;
    }

    /**
     * This will update a row in the table using JSON.
     *
     * @param json - The json string.
     * @return boolean
     */
    public boolean update(String json)
    {
        Type type = new TypeToken<Map<String, String>>()
        {
        }.getType();
        Map<String, String> updateMap = new Gson().fromJson(json, type);
        return update(updateMap);
    }

    /**
     * This will order the rows by a column name.
     *
     * @param columnName - The column name
     * @return DB
     */
    public DB orderBy(String columnName)
    {
        queryString = queryString + " ORDER BY " + columnName;
        return this;
    }


    /**
     * This will order the rows by id.
     *
     * @return DB
     */
    public DB orderByID()
    {
        return orderBy("id");
    }


    /**
     * This will order the rows ascending
     *
     * @return DB
     */
    public DB sortAscending()
    {
        queryString = queryString + " ASC";
        return this;
    }

    /**
     * This will order the rows descending
     *
     * @return DB
     */
    public DB sortDescending()
    {
        queryString = queryString + " DESC";
        return this;
    }

    /**
     * This will only take a certain amount of rows from the table.
     *
     * @param amount - The amount of rows.
     * @return DB
     */
    public DB take(int amount)
    {
        queryString = queryString + " LIMIT " + amount;
        return this;
    }

    /**
     * This is used either to deleteAll rows or a certain depending if you use a where clause before the function.
     *
     * @return boolean
     */
    public boolean delete()
    {
        queryString = "DELETE FROM " + tableName;
        if (whereCount == 1)
        {
            queryString = queryString + " " + whereStatement;
        } else
        {
            queryString = "DELETE * FROM" + tableName;
        }
        return execute();
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

}
