package uk.co.bjdavies.app.variables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.bjdavies.app.annotations.Variable;
import uk.co.bjdavies.app.exceptions.BabbleBotException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: VariableContainer.java
 * Compiled Class Name: VariableContainer.class
 * Date Created: 30/01/2018
 */

public class VariableContainer
{
    /**
     * This is the implementation of the logger on this class.
     */
    private Logger logger = LoggerFactory.getLogger(VariableContainer.class);


    /**
     * This is the Map for all the field variables.
     */
    private Map<String, Field> variableFields;


    /**
     * This is the Map for all the method variables.
     */
    private Map<String, Method> variableMethods;


    /**
     * This is where the Maps get initialized with the HashMap implementation of Map.
     */
    public VariableContainer()
    {
        variableFields = new HashMap<>();
        variableMethods = new HashMap<>();
    }


    /**
     * This method will add all the variables (@Variable.class) that are in that class to the 2 Maps.
     *
     * @param clazz - The class you want to insert them from.
     */
    public void addAllFrom(Class<?> clazz)
    {
        for (Method method : clazz.getMethods())
        {
            if (method.isAnnotationPresent(Variable.class)) addMethod(method.getName(), method);
        }

        for (Field field : clazz.getFields())
        {
            if (field.isAnnotationPresent(Variable.class)) variableFields.put(field.getName(), field);
        }
    }

    /**
     * This will add a method to the container.
     *
     * @param name   - the name of the variable.
     * @param method - the method for the variable.
     */
    public void addMethod(String name, Method method)
    {
        if (variableMethods.containsKey(name) || variableMethods.containsValue(method))
        {
            logger.error("The key or method is already in the container.", new BabbleBotException());
        } else
        {
            variableMethods.put(name, method);
        }
    }


    /**
     * This will add a field to the container.
     *
     * @param name  - the name of the variable.
     * @param field - the field for the variable.
     */
    public void addField(String name, Field field)
    {
        if (variableFields.containsKey(name) || variableFields.containsValue(field))
        {
            logger.error("The key or field is already in the container.", new BabbleBotException());
        } else
        {
            variableFields.put(name, field);
        }
    }

    /**
     * This will remove one from the container based on the name.
     *
     * @param name - The name of the variable.
     */
    public void remove(String name)
    {
        if (variableFields.containsKey(name))
        {
            variableFields.remove(name);
        } else if (variableMethods.containsKey(name))
        {
            variableMethods.remove(name);
        } else
        {
            logger.error("The name specified cannot be found inside this container.", new BabbleBotException());
        }
    }

    /**
     * This will return a field based on the name specified.
     *
     * @param name - the name of the Field you want to receive.
     * @return Field
     */
    public Field getFieldVariable(String name)
    {
        if (!variableFields.containsKey(name))
        {
            logger.error("Field cannot be found with the name specified in this container.", new BabbleBotException());
        } else
        {
            return variableFields.get(name);
        }
        return null;
    }

    /**
     * This will return a method based on the name specified.
     *
     * @param name - the name of the Field you want to receive.
     * @return Method
     */
    public Method getMethodVariable(String name)
    {
        if (!variableMethods.containsKey(name))
        {
            logger.error("Method cannot be found with the name specified in this container.", new BabbleBotException());
        } else
        {
            return variableMethods.get(name);
        }
        return null;
    }

    /**
     * This checks whether the variable exists in this container.
     *
     * @param name - the name of the variable.
     * @return Boolean
     */
    public boolean exists(String name)
    {
        if (variableFields.containsKey(name))
        {
            return true;
        } else if (variableMethods.containsKey(name))
        {
            return true;
        }
        return false;
    }

}
