package me.chasertw123.villagedefense.game.abilities;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.AbilityCreationException;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Heal extends Ability {

	public Heal() throws AbilityCreationException {
		super("Heal", 3, new int[] {1,2,3}, new int[] {1,2,3}, AbilityType.PRIMARY, new ItemStack(Material.INK_SACK, 1, (short) 10),
				"Right click a player to heal them or right click anything else to heal your self. This ability heals 1.5 hearts per level.");
	}

	@Override
	public void play(Main plugin, Object... args) {
		
		Player healer = (Player) args[0];
		
		for (Object  o : args) {
			Player healed = (Player) o;
			healed.setHealth(healed.getHealth() + (getTier() * 3));
		
			for (Player pl : Bukkit.getOnlinePlayers())
				pl.spigot().playEffect(healed.getLocation().clone().add(0, 1.8, 0), Effect.HEART, 0, 0, 0, 0, 0, 1, 5, 1);
		
			if (healer == healed)
				healer.sendMessage(plugin.getPrefix() + "You healed yourself");
			else
				healed.sendMessage(plugin.getPrefix() + healer.getName() + " healed you");
		}
	}
	
}
