package me.chasertw123.villagedefense.game.building;

import java.util.ArrayList;

import me.chasertw123.villagedefense.game.villager.Villager;

import org.bukkit.Location;

public abstract class Building {

	private int tier = 1;
	private BuildingType type;
	private Location center;
	private ArrayList<Villager> villagers;
	
	public Building(BuildingType type, Location center, ArrayList<Villager> villagers) {
		this.type = type;
		this.center = center;
		this.villagers = villagers;
	}
	
	/**
	 * 
	 * @return Tier of {@link Building}
	 */
	public int getTier() {
		return tier;
	}

	/**
	 * 
	 * @param tier, new tier of {@link Building}
	 */
	public void setTier(int tier) {
		this.tier = tier;
	}

	/**
	 * 
	 * @return {@link BuildingType} of {@link Building}
	 */
	public BuildingType getType() {
		return type;
	}

	/**
	 * 
	 * @return Center {@link Location} of {@link Building}
	 */
	public Location getCenter() {
		return center;
	}
	
	/**
	 * Level up an {@link Building} after setting the new tier.
	 */
	public abstract void levelUp();
	
	/**
	 * Initial startup tier, called on start of arena
	 */
	public abstract void buildFirstTier();

	/**
	 * @return the villagers
	 */
	public ArrayList<Villager> getVillagers() {
		return villagers;
	}

	/**
	 * @param villagers the villagers to set
	 */
	public void setVillagers(ArrayList<Villager> villagers) {
		this.villagers = villagers;
	}
}
