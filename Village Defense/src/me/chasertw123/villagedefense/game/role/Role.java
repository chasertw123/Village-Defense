package me.chasertw123.villagedefense.game.role;

import me.chasertw123.villagedefense.game.abilities.Ability;
import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public abstract class Role {

	private String name;
	private int bdr, bsb;
	private Ability primaryAbility, secondaryAbility, tertiaryAbility, ultraAbility;
	private ItemStack itemStack;
	
	public Role(String name, int bdr, int bsb, Ability primaryAbility, Ability secondaryAbility,
			Ability tertiaryAbility, Ability ultraAbility, ItemStack itemStack, String description) {
		
		this.name = name;
		this.bdr = bdr;
		this.bsb = bsb;
		this.primaryAbility = primaryAbility;
		this.secondaryAbility = secondaryAbility;
		this.tertiaryAbility = tertiaryAbility;
		this.ultraAbility = ultraAbility;
		
		FancyItemStack is = new FancyItemStack(itemStack);
		is.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + name);
		is.addFancyLore(description, ChatColor.WHITE.toString()).addLore("", ChatColor.WHITE + primaryAbility.getName(),
				ChatColor.WHITE + secondaryAbility.getName(), ChatColor.WHITE + tertiaryAbility.getName(), ChatColor.WHITE 
				+ ultraAbility.getName(), ChatColor.LIGHT_PURPLE + "Shift click for more information.");
		
		this.itemStack = is;
	}
	
	/**
	 * 
	 * @return Name of role
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return Base damage reduction in percent
	 */
	public int getBaseDamageReduction() {
		return bdr;
	}
	
	/**
	 * 
	 * @return Base speed of ability in percent
	 */
	public int getBaseSpeedBoost() {
		return bsb;
	}
	
	/**
	 * 
	 * @return Role's primary ability
	 */
	public Ability getPrimaryAbility() {
		return primaryAbility;
	}
	
	/**
	 * 
	 * @return Role's secondary ability
	 */
	public Ability getSecondaryAbility() {
		return secondaryAbility;
	}
	
	/**
	 * 
	 * @return Role's tertiary ability
	 */
	public Ability getTertiaryAbility() {
		return tertiaryAbility;
	}
	
	/**
	 * 
	 * @return Role's ultra ability
	 */
	public Ability getUltraAbility() {
		return ultraAbility;
	}
	
	/**
	 * 
	 * @return ItemStack representing role
	 */
	public ItemStack getItemStack() {
		return itemStack;
	}
}
