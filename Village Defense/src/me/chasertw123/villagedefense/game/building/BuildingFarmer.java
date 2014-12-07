package me.chasertw123.villagedefense.game.building;

import java.util.ArrayList;

import me.chasertw123.villagedefense.game.villager.Villager;
import me.chasertw123.villagedefense.game.villager.VillagerFarmer;

import org.bukkit.Location;

public class BuildingFarmer extends Building {

	public BuildingFarmer(Location center) {
		super(BuildingType.FARMER, center, new ArrayList<Villager>());
	}

	@Override
	public void levelUp() {
		// Build new tier, kill villagers, spawn new villagers from arraylist, update menus

		// Cleanup
		for (Villager v : getVillagers())
			if (v.getVil() != null && v.getVil().getHealth() > 0)
				v.getVil().setHealth(0);
		getVillagers().clear();
		
		// Generate structure
		
		// Recreate villagers
		getVillagers().add(new VillagerFarmer(getCenter().add(0, 0, 10)));
		
		for (Villager v : getVillagers())
			v.spawnVillager();
	}

	@Override
	public void buildFirstTier() {
		// Generates arena, spawn villagers, add villagers to arraylist

		// Generate structure
		
		// Create villagers
		getVillagers().add(new VillagerFarmer(getCenter().add(0, 0, 10)));
		
		for (Villager v : getVillagers())
			v.spawnVillager();
	}
}
