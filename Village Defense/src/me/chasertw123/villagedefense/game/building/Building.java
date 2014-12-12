package me.chasertw123.villagedefense.game.building;

import me.chasertw123.villagedefense.exceptions.BuildingCreationException;
import me.chasertw123.villagedefense.game.villager.Villager;

import org.bukkit.Location;

public abstract class Building {

    private int tier = 1, maxTier;
    private BuildingType type;
    private Location center;
    private Villager villager;

    public Building(BuildingType type, Location center, Villager villager, int maxTier) throws BuildingCreationException {
        this.type = type;
        this.center = center;
        this.villager = villager;
        this.maxTier = maxTier;

        if (maxTier < 1)
            throw new BuildingCreationException("A building's max tier is lower than one!");
    }

    /** @return Tier of {@link Building} */
    public int getTier() {
        return tier;
    }

    /** Update tier of {@link Building}
     * 
     * @param tier
     *            , new tier of {@link Building} */
    public void setTier(int tier) {
        this.tier = tier;
    }

    /** @return maxTier of {@link Building} */
    public int getMaxTier() {
        return maxTier;
    }

    /** @return {@link BuildingType} of {@link Building} */
    public BuildingType getType() {
        return type;
    }

    /** @return Center {@link Location} of {@link Building} */
    public Location getCenter() {
        return center;
    }

    /** Level up an {@link Building} after setting the new tier. */
    public abstract void levelUp();

    /** Initial startup tier, called on start of arena */
    public abstract void buildFirstTier();

    /** @return the villager */
    public Villager getVillager() {
        return villager;
    }

    /** @param villager
     *            the villager to set */
    public void setVillager(Villager villager) {
        this.villager = villager;
    }
}
