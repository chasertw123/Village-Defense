package me.chasertw123.villagedefense.game.villager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VillagerFarmer extends Villager {

	public VillagerFarmer(Location loc) {
		super(loc);
	}

	@Override
	protected org.bukkit.entity.Villager placeVillagerInWorld() {
		org.bukkit.entity.Villager vil = (org.bukkit.entity.Villager) getLoc().getWorld().spawnEntity(getLoc(), EntityType.VILLAGER);
		vil.setAdult();
		vil.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 100, true));
		return vil;
	}

	@Override
	public Inventory makeInventory() {
		Inventory i = Bukkit.createInventory(null, 18);
		return i;
	}

}
