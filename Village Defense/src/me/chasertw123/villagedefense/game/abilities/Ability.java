package me.chasertw123.villagedefense.game.abilities;

import org.bukkit.inventory.ItemStack;

public interface Ability {

	/**
	 * 
	 * @return Name of ability
	 */
	public String getName();
	
	/**
	 * 
	 * @return Cooldown time per ability use
	 */
	public int getCooldown();
	
	/**
	 * 
	 * @return Mana Cost per ability use
	 */
	public int getManaCost();
	
	/**
	 * 
	 * @return AbilitySlot of ability
	 */
	public AbilitySlot getAbilitySlot();
	
	/**
	 * 
	 * @return ItemStack representing ability
	 */
	public ItemStack getItemStack();
	
}
