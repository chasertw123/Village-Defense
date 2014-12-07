package me.chasertw123.villagedefense.game.abilities;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.AbilityCreationException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NoAbility extends Ability {

	public NoAbility(AbilityType abilityType) throws AbilityCreationException {
		super("No Ability", 1, new int[] {1}, new int[] {1}, abilityType, new ItemStack(Material.INK_SACK, 1, (short) 8), "This is a blank ability slot.");
	}

	@Override
	public void play(Main plugin, Object... args) {
		((Player) args[0]).sendMessage(plugin.getPrefix() + ChatColor.GRAY + "This is not an ability.");
	}
}
