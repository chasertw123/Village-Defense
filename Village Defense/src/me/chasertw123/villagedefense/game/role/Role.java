package me.chasertw123.villagedefense.game.role;

import me.chasertw123.villagedefense.game.abilities.Ability;

import org.bukkit.inventory.ItemStack;

public interface Role {

	/**
	 * 
	 * @return Name of role
	 */
	public String getName();
	
	/**
	 * 
	 * @return Base damage reduction in percent
	 */
	public int getBaseDamageReduction();
	
	/**
	 * 
	 * @return Base speed of ability in percent
	 */
	public int getBaseSpeedBoost();
	
	/**
	 * 
	 * @return Role's primary ability
	 */
	public Ability getPrimaryAbility();
	
	/**
	 * 
	 * @return Role's secondary ability
	 */
	public Ability getSecondaryAbility();
	
	/**
	 * 
	 * @return Role's tertiary ability
	 */
	public Ability getTertiaryAbility();
	
	/**
	 * 
	 * @return Role's ultra ability
	 */
	public Ability getUltraAbility();
	
	/**
	 * 
	 * @return ItemStack representing role
	 */
	public ItemStack getItemStack();
}
