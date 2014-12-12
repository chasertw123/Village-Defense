package me.chasertw123.villagedefense.game.abilities;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.AbilityCreationException;
import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public abstract class Ability {

    private String name;
    private int tier = 1, maxTier, timeRemaining = 0;
    private int[] manaCost, cooldown;
    private AbilityType abilityType;
    private ItemStack itemStack;

    public Ability(String name, int maxTier, int[] manaCost, int[] cooldown, AbilityType abilityType, ItemStack itemStack, String description) throws AbilityCreationException {
        this.name = name;
        this.maxTier = maxTier;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.abilityType = abilityType;
        this.itemStack = new FancyItemStack(itemStack).addFancyLore(description, ChatColor.WHITE.toString());

        if (manaCost.length != maxTier)
            throw new AbilityCreationException("Mana cost lenght is not valid for " + name + "! Manacost (" + manaCost.length + ") != max tier (" + maxTier + ")");

        if (cooldown.length != maxTier)
            throw new AbilityCreationException("Cooldown lenght is not valid for " + name + "! Cooldown (" + cooldown.length + ") != max tier (" + maxTier + ")");
    }

    /**
     * @return Name of ability
     */
    public String getName() {
        return name;
    }

    /**
     * @return Get current tier of ability
     */
    public int getTier() {
        return tier;
    }

    /**
     * @param New tier
     */
    public void setTier(int tier) {
        this.tier = tier;
    }

    /**
     * @return Max tier of ability
     */
    public int getMaxTier() {
        return maxTier;
    }

    /**
     * @return Cooldown time per ability use
     */
    public int getCooldown() {
        return cooldown[tier - 1];
    }

    /**
     * @return Mana Cost per ability use
     */
    public int getManaCost() {
        return manaCost[tier - 1];
    }

    /**
     * @return AbilityType representing ability
     */
    public AbilityType getAbilityType() {
        return abilityType;
    }

    /**
     * @return ItemStack representing ability
     */
    public ItemStack getItemStack() {

        FancyItemStack is = new FancyItemStack(itemStack);

        is.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + getName() + ChatColor.BLUE + "" + ChatColor.BOLD + " [" + ChatColor.GOLD + ChatColor.BOLD + "Level " + getTier() + ChatColor.BLUE + "" + ChatColor.BOLD + "]");
        is.addLore("", ChatColor.BLUE + "Level: " + ChatColor.GOLD + getTier() + "/" + getMaxTier(), "", ChatColor.BLUE + "Mana Cost: " + ChatColor.GOLD + getManaCost(), ChatColor.BLUE + "Cooldown: " + ChatColor.GOLD + getCooldown());

        return is;
    }

    /**
     * decrements cooldown
     * 
     * @return decremented cooldown
     */
    public int decrementCooldown() {
        if (timeRemaining > 0)
            return --timeRemaining;
        else
            return timeRemaining;
    }

    /**
     * Resets cooldown.
     * 
     * @return cooldown
     */
    public int resetCooldown() {
        return timeRemaining = getCooldown();
    }

    /**
     * @return boolean if player can use ability
     */
    public boolean canUseAbility() {
        return timeRemaining == 0;
    }

    /**
     * @description Plays the ability
     */
    public abstract void play(Main plugin, Object... args);

}
