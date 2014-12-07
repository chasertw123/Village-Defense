package me.chasertw123.villagedefense.game.role;

import me.chasertw123.villagedefense.exceptions.AbilityCreationException;
import me.chasertw123.villagedefense.game.abilities.Heal;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Healer extends Role {

	public Healer() throws AbilityCreationException {
		super("Healer", 100, 110, new Heal(), null, null, null, new ItemStack(Material.INK_SACK, 1, (short) 10),
				"This role acts as support healing and buffing their allies. You have high mana but low attack damage and a slight speed boost.");
	}

}
