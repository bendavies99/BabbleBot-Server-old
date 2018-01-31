package uk.co.bjdavies.app.modules;

import uk.co.bjdavies.app.annotations.CommandFunction;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: AudioModule.java
 * Compiled Class Name: AudioModule.class
 * Date Created: 30/01/2018
 */

public class AudioModule extends Module
{
    @Override
    public String getName()
    {
        return "AudioModule";
    }

    @Override
    public String getVersion()
    {
        return "1";
    }

    @Override
    public String getAuthor()
    {
        return "Ben Davies";
    }

    @CommandFunction
    public void play()
    {

    }

}
