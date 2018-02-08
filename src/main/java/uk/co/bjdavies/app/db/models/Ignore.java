package uk.co.bjdavies.app.db.models;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: Ignore.java
 * Compiled Class Name: Ignore.class
 * Date Created: 08/02/2018
 */

public class Ignore extends Model
{
    private String guildID;
    private String channelID;
    private String ignoredBy;

    public String getGuildID()
    {
        return guildID;
    }

    public String getChannelID()
    {
        return channelID;
    }

    public String getIgnoredBy()
    {
        return ignoredBy;
    }

    @Override
    public String toString()
    {
        return "Ignore{" +
                "guildID='" + guildID + '\'' +
                ", channelID='" + channelID + '\'' +
                ", ignoredBy='" + ignoredBy + '\'' +
                '}';
    }
}
