package me.chasertw123.villagedefense.game.role;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Archer extends Role {

	public Archer() {
		super("Archer", 100, 110, 220, null, null, null, null, new ItemStack(Material.BOW), 
				"This role acts as a marksman, dealing lots of damage from long distances. You have medium attack damage,"
				+ " a medium amount of mana, and a small speed buff.");
	}

}
