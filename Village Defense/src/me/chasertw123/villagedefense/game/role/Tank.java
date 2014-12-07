package me.chasertw123.villagedefense.game.role;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Tank extends Role {

	public Tank() {
		super("Tank", 50, 85, null, null, null, null, new ItemStack(Material.DIAMOND_CHESTPLATE), 
				"This role acts as the main damage taker of the team. You have a medium amount of attack damage, tons of mana,"
				+ " and are highly resistant to damage, but you are a little slow.");
	}

}
