package me.chasertw123.villagedefense.game.arena;

import java.util.ArrayList;

import me.chasertw123.villagedefense.game.building.Building;

import org.bukkit.Location;

public class Arena {

    private ArrayList<Building> buildings;
    private Location centerSpawnLocation;

    /**
     * @param buildings List of buildings
     * @param spawnLocation Center of player spawns
     */
    public Arena(ArrayList<Building> buildings, Location spawnLocation) {
        setCenterSpawnLocation(spawnLocation);
        this.setBuildings(buildings);

        for (Building b : buildings)
            b.buildFirstTier();
    }

    /**
     * @return buildings of this arena
     */
    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    /**
     * @param buildings of this arena
     */
    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    /**
     * @return Center spawn {@link Location}
     */
    public Location getCenterSpawnLocation() {
        return centerSpawnLocation.clone();
    }

    /**
     * @return Random spawn {@link Location}
     */
    public Location getRandomLocation() {
        return getCenterSpawnLocation().add((Math.random() * 10) - 5, 0, (Math.random() * 10) - 5);
    }

    /**
     * @param centerSpawnLocation New center spawn {@link Location}
     */
    public void setCenterSpawnLocation(Location centerSpawnLocation) {
        this.centerSpawnLocation = centerSpawnLocation;
    }
}
