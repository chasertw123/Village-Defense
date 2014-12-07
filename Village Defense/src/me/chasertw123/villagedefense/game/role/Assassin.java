package me.chasertw123.villagedefense.game.role;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Assassin extends Role {

	public Assassin() {
		super("Assassin", 90, 140, 150, null, null, null, null, new ItemStack(Material.NETHER_STAR), 
				"This role acts has a high damage dealer taking down a majority of enemies. You have low mana, high attack, a huge speed boost,"
				+ " but you can't take much damage before you die.");
	}

}
