package me.chasertw123.villagedefense.game.arena;

import java.util.ArrayList;
import java.util.Arrays;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.ArenaCreationException;
import me.chasertw123.villagedefense.game.building.Building;

import org.bukkit.Location;

public class Arena {

    private ArrayList<Building> buildings;
    private ArrayList<Location> enemySpawnPoints;
    private Location centerSpawnLocation, lobbySpawnLocation;
    private String name;

    /**
     * @param buildings List of buildings
     * @param spawnLocation Center of player spawns
     * @throws ArenaCreationException When arena failed to create
     */
    public Arena(String name, ArrayList<Building> buildings, ArrayList<Location> enemySpawnPoints, Location spawnLocation, Location lobbyLocation, Main plugin) throws ArenaCreationException {

        this.name = name;

        Boolean[] bools = new Boolean[Building.buildingClasses.size()];
        Arrays.fill(bools, false);

        for (Building building : buildings)
            for (int i = 0; i < Building.buildingClasses.size(); i++) {
                Class<? extends Building> b = Building.buildingClasses.get(i);

                if (building.getClass().equals(b))
                    if (bools[i])
                        throw new ArenaCreationException("Duplicate building " + b.getSimpleName());
                    else
                        bools[i] = true;
            }

        for (int i = 0; i < bools.length; i++)
            if (!bools[i])
                throw new ArenaCreationException("Missing " + Building.buildingClasses.get(i).getSimpleName());

        if (spawnLocation == null)
            throw new ArenaCreationException("The arena spawn location cannot be null");

        if (lobbyLocation == null)
            throw new ArenaCreationException("The arena lobby spawn location cannot be null");

        if (enemySpawnPoints.size() < 1)
            throw new ArenaCreationException("There needs to be atleast 1 enemy spawnpoint set");

        this.setLobbySpawnLocation(spawnLocation);
        this.setCenterSpawnLocation(spawnLocation);
        this.setBuildings(buildings);
        this.setEnemySpawnPoints(enemySpawnPoints);

        for (Building b : buildings)
            b.buildFirstTier(plugin);
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
        return getCenterSpawnLocation().clone().add((Math.random() * 10) - 5, 0, (Math.random() * 10) - 5);
    }

    /**
     * @param centerSpawnLocation New center spawn {@link Location}
     */
    public void setCenterSpawnLocation(Location centerSpawnLocation) {
        this.centerSpawnLocation = centerSpawnLocation;
    }

    /**
     * @return the enemySpawnPoints
     */
    public ArrayList<Location> getEnemySpawnPoints() {
        return enemySpawnPoints;
    }

    /**
     * @param enemySpawnPoints the enemySpawnPoints to set
     */
    public void setEnemySpawnPoints(ArrayList<Location> enemySpawnPoints) {
        this.enemySpawnPoints = enemySpawnPoints;
    }

    /**
     * @return the {@link Location} of lobbySpawnLocation
     */
    public Location getLobbySpawnLocation() {
        return lobbySpawnLocation;
    }

    /**
     * @param lobbySpawnLocation the {@link Location} to set
     */
    public void setLobbySpawnLocation(Location lobbySpawnLocation) {
        this.lobbySpawnLocation = lobbySpawnLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
