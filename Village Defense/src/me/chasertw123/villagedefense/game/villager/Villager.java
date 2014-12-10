package me.chasertw123.villagedefense.game.villager;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

public abstract class Villager {

	private org.bukkit.entity.Villager vil = null;
	private Location loc;
	
	/**
	 * 
	 * @param Location of Villager
	 */
	public Villager(Location loc) {
		this.loc = loc;
	}

	/**
	 * Spawn him, use this when spawning him, updates getVil()
	 */
	public void spawnVillager() {
		setVil(placeVillagerInWorld());
	}
	
	/**
	 * Spawn him, use this when you manually update getVil()
	 * @return {@link org.bukkit.entity.Villager} instance of spawned entity
	 */
	protected abstract org.bukkit.entity.Villager placeVillagerInWorld();
	
	/**
	 * 
	 * @return Inventory used when rightclicking
	 */
	public abstract Inventory makeInventory();

	/**
	 * 
	 * @return {@link Location} of {@link Villager}
	 */
	public Location getLoc() {
		return loc;
	}

	/**
	 * 
	 * @param Set new {@link Location}
	 */
	public void setLoc(Location loc) {
		this.loc = loc;
	}

	/**
	 * 
	 * @return {@link org.bukkit.entity.Villager} Entity
	 */
	public org.bukkit.entity.Villager getVil() {
		return vil;
	}

	/**
	 * Update {@link org.bukkit.entity.Villager} entity.
	 * @param vil {@link org.bukkit.entity.Villager}
	 */
	public void setVil(org.bukkit.entity.Villager vil) {
		this.vil = vil;
	}
}
