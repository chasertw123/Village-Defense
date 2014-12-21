package me.chasertw123.villagedefense.game.villager;

import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.building.Building;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;

public abstract class Villager {

    private org.bukkit.entity.Villager vil = null;
    private Building building;
    private Location loc;

    /**
     * @param Location of Villager
     */
    public Villager(Location loc) {
        this.loc = loc;
    }

    /**
     * @return {@link Location} of {@link Villager}
     */
    public Location getLoc() {
        return loc;
    }

    /**
     * @param Set new {@link Location}
     */
    public void setLoc(Location loc) {
        this.loc = loc;
    }

    /**
     * @return {@link org.bukkit.entity.Villager} Entity
     */
    public org.bukkit.entity.Villager getVil() {
        return vil;
    }

    /**
     * Update {@link org.bukkit.entity.Villager} entity.
     * 
     * @param vil {@link org.bukkit.entity.Villager}
     */
    public void setVil(org.bukkit.entity.Villager vil) {
        this.vil = vil;
    }

    /**
     * @return the {@link Building} this {@link Villager} is with
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * @param building the {@link Building} to set with this {@link Villager}
     */
    public void setBuilding(Building building) {
        this.building = building;
    }

    /**
     * Spawn him, use this when spawning him, updates getVil()
     */
    public void spawnVillager() {
        if (getVil() != null) {
            for (Entity e : getVil().getNearbyEntities(1, 1, 1))
                if (e.getPassenger() == getVil())
                    e.remove();

            getVil().remove();
        }

        setVil(placeVillagerInWorld());
    }

    /**
     * Spawn him, use this when you manually update getVil()
     * 
     * @return {@link org.bukkit.entity.Villager} instance of spawned entity
     */
    protected abstract org.bukkit.entity.Villager placeVillagerInWorld();

    /**
     * Make an {@link Inventory} for a specific {@link GamePlayer}
     * 
     * @param player the {@link GamePlayer} opening the {@link Inventory}
     * @return Inventory used when rightclicking
     */
    public abstract Inventory makeInventory(GamePlayer player);
}
