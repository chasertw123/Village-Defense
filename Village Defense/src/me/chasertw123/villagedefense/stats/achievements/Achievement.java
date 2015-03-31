package me.chasertw123.villagedefense.stats.achievements;

import me.chasertw123.villagedefense.Main;

import org.bukkit.entity.Player;

public class Achievement {

    private String id, name, friendlyname, description;
    public Main plugin;

    public Achievement(String id, String name, String friendlyname, String description, Main plugin) {
        super();
        this.id = id;
        this.name = name;
        this.friendlyname = friendlyname;
        this.description = description;
        this.plugin = plugin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFriendlyname() {
        return friendlyname;
    }

    public void setFriendlyname(String friendlyname) {
        this.friendlyname = friendlyname;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * For achievement wich require to have a minimum of X kills
     * 
     * Called on:
     * 
     * - Player kill // TODO
     * 
     * @param p
     * @return
     */
    public boolean check(Player p) {
        return false;
    }
}
