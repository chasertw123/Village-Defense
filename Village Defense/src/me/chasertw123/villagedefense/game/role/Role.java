package me.chasertw123.villagedefense.game.role;

import java.util.ArrayList;
import java.util.HashMap;

import me.chasertw123.villagedefense.exceptions.AbilityCreationException;
import me.chasertw123.villagedefense.exceptions.RoleCreationException;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.abilities.Ability;
import me.chasertw123.villagedefense.game.abilities.AbilityType;
import me.chasertw123.villagedefense.game.abilities.NoAbility;
import me.chasertw123.villagedefense.game.tools.Tool;
import me.chasertw123.villagedefense.game.tools.ToolSet;
import me.chasertw123.villagedefense.game.tools.ToolType;
import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public abstract class Role {

    private String name;
    private int mana, manaRegen, speedBoost, damageReduction, experience, level = 1, availableUpgrades = 0;
    private ArrayList<ToolSet> toolSets;
    private Ability primaryAbility, secondaryAbility, tertiaryAbility, ultraAbility;
    private ItemStack itemStack;

    public static HashMap<Class<? extends Role>, EntityType> roleClasses = new HashMap<Class<? extends Role>, EntityType>();

    public Role(String name, int baseMana, int baseManaRegen, int baseSpeedBoost, int baseDamageReduction, Ability primaryAbility, Ability secondaryAbility, Ability tertiaryAbility, Ability ultraAbility, ArrayList<ToolSet> toolSets, ItemStack itemStack, String description) throws RoleCreationException {
        this.name = name;
        this.mana = baseMana;
        this.manaRegen = baseManaRegen;
        this.speedBoost = baseSpeedBoost;
        this.damageReduction = baseDamageReduction;
        this.toolSets = toolSets;
        this.primaryAbility = primaryAbility;
        this.secondaryAbility = secondaryAbility;
        this.tertiaryAbility = tertiaryAbility;
        this.ultraAbility = ultraAbility;

        int chestplate = 0, leggings = 0, boots = 0, weapon = 0;

        for (ToolSet toolSet : toolSets) {

            if (toolSet.getToolType() == ToolType.CHESTPLATE)
                ++chestplate;

            else if (toolSet.getToolType() == ToolType.LEGGINGS)
                ++leggings;

            else if (toolSet.getToolType() == ToolType.BOOTS)
                ++boots;

            else if (toolSet.getToolType() == ToolType.WEAPON)
                ++weapon;
        }

        if (chestplate < 1 || leggings < 1 || boots < 1 || weapon < 1)
            throw new RoleCreationException("Too little ToolSets have been set!");

        try {

            if (this.primaryAbility == null)
                this.primaryAbility = new NoAbility(AbilityType.PRIMARY);

            if (this.secondaryAbility == null)
                this.secondaryAbility = new NoAbility(AbilityType.SECONDARY);

            if (this.tertiaryAbility == null)
                this.tertiaryAbility = new NoAbility(AbilityType.TERTIARY);

            if (this.ultraAbility == null)
                this.ultraAbility = new NoAbility(AbilityType.ULTRA);

        } catch (AbilityCreationException e) {
            e.printStackTrace();
        }

        FancyItemStack is = new FancyItemStack(itemStack);
        is.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + name);
        is.addFancyLore(description, ChatColor.WHITE.toString()).addLore("", ChatColor.BLUE + "Primary Ability: " + ChatColor.GOLD + this.primaryAbility.getName(), ChatColor.BLUE + "Secondary Ability: " + ChatColor.GOLD + this.secondaryAbility.getName(), ChatColor.BLUE + "Tertiary Ability: " + ChatColor.GOLD + this.tertiaryAbility.getName(), ChatColor.BLUE + "Ultra Ability: " + ChatColor.GOLD + this.ultraAbility.getName(), "", ChatColor.LIGHT_PURPLE + "Shift right click for more info.");

        this.itemStack = is;
    }

    /**
     * @return Name of role
     */
    public String getName() {
        return name;
    }

    /**
     * @return level of {@link Role}
     */
    public int getLevel() {
        return level;
    }

    /**
     * Set the level of {@link Role}
     * 
     * @param level the new level of the {@link Role}
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the amount of times a {@link GamePlayer} can upgrade any of their
     * {@link Ability}
     */
    public int getAvailableUpgrades() {
        return availableUpgrades;
    }

    /**
     * Set the amount of times a {@link GamePlayer} can upgrade any or their
     * {@link Ability}
     * 
     * @param availableUpgrades amount of times a {@link GamePlayer} can upgrade
     * any or their {@link Ability}
     */
    public void setAvailbleUpgrades(int availableUpgrades) {
        this.availableUpgrades = availableUpgrades;
    }

    /**
     * Level up the {@link Role}
     */
    public void levelUp() {
        setLevel(getLevel() + 1);
        setAvailbleUpgrades(getAvailableUpgrades() + 1);
        setSpeedBoost(getSpeedBoost() + 2);
        setDamageReduction(getDamageReduction() + 2);
    }

    /**
     * Get Max Level for {@link Role}
     * 
     * @return the max level for {@link Role}
     */
    public int getMaxLevel() {
        return (getPrimaryAbility().getMaxTier() + getSecondaryAbility().getMaxTier() + getTertiaryAbility().getMaxTier() + getUltraAbility().getMaxTier()) - 4;
    }

    /**
     * Get the Mana Regen per second for {@link Role} level
     * 
     * @return Mana regen per seconf for {@link Role}
     */
    public int getManaRegen() {
        return manaRegen;
    }

    /**
     * Set the Mana Regen per second for {@link Role}
     * 
     * @param manaRegen Mana regen this {@link Role} can have
     */
    public void setManaRegen(int manaRegen) {
        this.manaRegen = manaRegen;
    }

    /**
     * Get the Max Mana for {@link Role} level
     * 
     * @return Max man {@link Role} has
     */
    public int getMana() {
        return mana;
    }

    /**
     * Set the Max Mana for {@link Role}
     * 
     * @param mana Max mana this {@link Role} can have
     */
    public void setMana(int mana) {
        this.mana = mana;
    }

    /**
     * @return Speed boost of {@link Role} in percent
     */
    public int getSpeedBoost() {
        return speedBoost;
    }

    /**
     * Set the speed boost of the {@link Role} in percent
     * 
     * @param speedBoost Speed increase in percent (100 is normal speed)
     */
    public void setSpeedBoost(int speedBoost) {
        this.speedBoost = speedBoost;
    }

    /**
     * @return Damage reduction of {@link Role} in percent
     */
    public int getDamageReduction() {
        return damageReduction;
    }

    /**
     * Set the damage reduction of the {@link Role} in percent
     * 
     * @param damageReduction Damage reduction increase in percent (100 is
     * normal speed)
     */
    public void setDamageReduction(int damageReduction) {
        this.damageReduction = damageReduction;
    }

    /**
     * Get the current experience for {@link Role}
     * 
     * @return amount of experience {@link Role} has
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Set the current experience for {@link Role}
     * 
     * @param roleExperience amount of experience {@link Role} has
     */
    public void setExperience(int experience) {
        this.experience = experience;
    }

    /**
     * Get the amount of experience required for a level
     * 
     * @param level the level you want the experience from
     * @return the amount of experience for level
     */
    public int getLevelUpExperience(int level) {

        if (level <= 1)
            return 0;

        return (int) Math.floor(level + 300 * Math.pow(2, level / 7));
    }

    /**
     * @return Role's primary ability
     */
    public Ability getPrimaryAbility() {
        return primaryAbility;
    }

    /**
     * @return Role's secondary ability
     */
    public Ability getSecondaryAbility() {
        return secondaryAbility;
    }

    /**
     * @return Role's tertiary ability
     */
    public Ability getTertiaryAbility() {
        return tertiaryAbility;
    }

    /**
     * @return Role's ultra ability
     */
    public Ability getUltraAbility() {
        return ultraAbility;
    }

    /**
     * @return ItemStack representing role
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Banner/Block to use on head.
     * 
     * @return {@link ItemStack} with banner, used in
     * {@link PlayerInventory#setHelmet(ItemStack)} when ingame.
     */
    public abstract ItemStack getBanner();

    /**
     * Get the {@link ToolSet} {@link ArrayList} of this {@link Role}
     * 
     * @return {@link ToolSet} {@link ArrayList} of this {@link Role} instance
     */
    public ArrayList<ToolSet> getToolSets() {
        return toolSets;
    }

    /**
     * Get the {@link ToolSet} for specified {@link ToolType}
     * 
     * @param toolType {@link ToolType} of {@link ToolSet}
     * @return {@link ToolSet} of the specified {@link ToolType}
     */
    public ToolSet getToolSet(ToolType toolType) {
        for (ToolSet toolSet : toolSets)
            if (toolType == toolSet.getToolType())
                return toolSet;

        return null;
    }

    /**
     * Get the max tier {@link Integer} for a {@link ToolType}
     * 
     * @param toolType {@link ToolType} to get {@link Integer} max tier of
     * @return max tier {@link Integer} for {@link ToolType}
     */
    public int getMaxTier(ToolType toolType) {
        return getToolSet(toolType).getMaxTier();
    }

    /**
     * Get {@link ItemStack} of a certain tier {@link Integer} {@link Tool} of a
     * certain {@link ToolType}
     * 
     * @param toolType {@link ToolType} of the {@link Tool} you want to get
     * @param toolTier {@link Integer} of the {@link Tool} you want to get
     * @return {@link ItemStack} of a certain tier {@link Integer} {@link Tool}
     * of a certain {@link ToolType}
     */
    public ItemStack getItemStack(ToolType toolType, int toolTier) {
        return getToolSet(toolType).getTool(toolTier).getItemStack();
    }

    /**
     * Get cost {@link Integer} of a certain tier {@link Integer} {@link Tool}
     * of a certain {@link ToolType}
     * 
     * @param toolType {@link ToolType} of the {@link Tool} you want to get
     * @param toolTier {@link Integer} of the {@link Tool} you want to get
     * @return cost {@link Integer} of a certain tier {@link Integer}
     * {@link Tool} of a certain {@link ToolType}
     */
    public int getCost(ToolType toolType, int toolTier) {
        return getToolSet(toolType).getTool(toolTier).getCost();
    }

    /**
     * Get required building tier {@link Integer} of a certain tier
     * {@link Integer} {@link Tool} of a certain {@link ToolType}
     * 
     * @param toolType {@link ToolType} of the {@link Tool} you want to get
     * @param toolTier {@link Integer} of the {@link Tool} you want to get
     * @return required building tier {@link Integer} of a certain tier
     * {@link Integer} {@link Tool} of a certain {@link ToolType}
     */
    public int getBuildingTier(ToolType toolType, int toolTier) {
        return getToolSet(toolType).getTool(toolTier).getRequiredBuildingTier();
    }

    /**
     * Add a {@link Role} to an {@link ArrayList}
     * 
     * @param role the {@link Class} to add to {@link Role} {@link ArrayList}
     */
    public static void registerRole(Class<? extends Role> role, EntityType type) {
        roleClasses.put(role, type);

        try {
            Role r = role.newInstance();
            System.out.println("Loaded role: " + r.getName());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
