package uk.co.bjdavies.app.binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.bjdavies.app.exceptions.BabbleBotException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: BindingContainer.java
 * Compiled Class Name: BindingContainer.class
 * Date Created: 30/01/2018
 */

public class BindingContainer
{
    /**
     * This is the implementation of the logger for this class.
     */
    Logger logger = LoggerFactory.getLogger(BindingContainer.class);


    /**
     * This is the Map of all the bindings in the container.
     */
    private Map<String, Bindable> bindings;


    /**
     * This is the Map of all the listeners for a binding.
     */
    private Map<BindingEventListener, Bindable> listeners;


    /**
     * This is where the Maps will get initialized.
     */
    public BindingContainer()
    {
        bindings = new HashMap<>();
        listeners = new HashMap<>();
    }


    /**
     * This is where you can add a binding to the container.
     *
     * @param name     - The name of the binding.
     * @param bindable - The bindable objectyou want to bind.
     */
    public void addBinding(String name, Bindable bindable)
    {
        if (bindings.containsKey(name) || bindings.containsValue(bindable))
        {
            logger.error("The key or bindable is already in this container.", new BabbleBotException());
        } else
        {
            bindings.put(name, bindable);
        }
    }

    /**
     * This will determine id the binding container holds a binding.
     *
     * @param name - The name of the binding.
     * @return boolean
     */
    public boolean hasBinding(String name)
    {
        return bindings.containsKey(name);
    }

    /**
     * This is where you can attach listeners to a binded object.
     *
     * @param listener - This is the listener for the binded object.
     * @param bindable -  This is the binded object.
     */
    public void addListener(BindingEventListener listener, Bindable bindable)
    {
        if (!bindings.containsValue(bindable))
        {
            logger.error("The bindable object is not binded to this container.", new BabbleBotException());
        } else
        {
            listeners.put(listener, bindable);
        }
    }


    /**
     * This is where you can remove a listener from a binded object.
     *
     * @param listener - This is the listener you wish to remove.
     */
    public void removeListener(BindingEventListener listener)
    {
        if (!listeners.containsKey(listener))
        {
            logger.error("The listener cannot be removed because it is not in use within this container.", new BabbleBotException());
        } else
        {
            listeners.remove(listener);
        }
    }


    /**
     * This is where you can get the binding from the container.
     *
     * @param name - The name of the binding.
     * @return Bindable
     */
    public Bindable getBinding(String name)
    {
        if (!bindings.containsKey(name))
        {
            logger.error("This container does not hold the binding that you have requested.", new BabbleBotException());
        } else
        {
            return bindings.get(name);
        }
        return null;
    }


    /**
     * This is where you can remove a binding from the container.
     *
     * @param name - This is the binding you wish to remove.
     */
    public void removeBinding(String name)
    {
        if (!bindings.containsKey(name))
        {
            logger.error("The binding cannot be removed because it is not in use within this container.");
        } else
        {
            listeners.entrySet().stream().filter(e -> e.getValue() == bindings.get(name)).forEach(e -> listeners.remove(e.getKey()));
            bindings.remove(name);
        }
    }


    /**
     * This is where you can update a binding in the container.
     *
     * @param name     - The name of the binding
     * @param bindable - The new value for the binding.
     */
    public void updateBinding(String name, Bindable bindable)
    {
        if (!bindings.containsKey(name))
        {
            logger.error("The binding cannot be updated because it is not in use within this container.");
        } else
        {
            List<BindingEventListener> oldListeners = new ArrayList<>();
            listeners.entrySet().stream().filter(e -> e.getValue() == bindings.get(name)).forEach(e -> oldListeners.add(e.getKey()));

            removeBinding(name);
            addBinding(name, bindable);

            for (BindingEventListener listener : oldListeners)
            {
                addListener(listener, bindable);
            }

            onUpdate(name);
        }
    }


    /**
     * This gets called when a Binding gets updated and all the listeners will run the onUpdate method.
     *
     * @param name - THe name of the binding.
     */
    private void onUpdate(String name)
    {
        listeners.entrySet().stream().filter(e -> e.getValue() == bindings.get(name)).forEach(e -> e.getKey().onUpdate(e.getValue()));
    }


}
