package me.chasertw123.villagedefense.game.role;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Mage extends Role {

	public Mage() {
		super("Mage", 50, 110, 280, null, null, null, null, new ItemStack(Material.BLAZE_ROD),
				"This role acts as a high damage dealer taking dowm large portions of enimies. You have high attack, high mana,"
				+ " a small speed boost but you cannot take many hits");
	}

}
