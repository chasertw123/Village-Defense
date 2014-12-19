package me.chasertw123.villagedefense.game.role;

import java.util.ArrayList;
import java.util.HashMap;

import me.chasertw123.villagedefense.exceptions.AbilityCreationException;
import me.chasertw123.villagedefense.exceptions.RoleCreationException;
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
    private int bdr, bsb, bm;
    private ArrayList<ToolSet> toolSets;
    private Ability primaryAbility, secondaryAbility, tertiaryAbility, ultraAbility;
    private ItemStack itemStack;

    public static HashMap<Class<? extends Role>, EntityType> roleClasses = new HashMap<Class<? extends Role>, EntityType>();

    /**
     * Create a role instance
     * 
     * @param name of {@link Role}
     * @param bdr base damage reduction of {@link Role}
     * @param bsb base speed boost of {@link Role}
     * @param bm base mana of {@link Role}
     * @param primaryAbility Primary {@link Ability} of {@link Role}
     * @param secondaryAbility Secondary {@link Ability} of {@link Role}
     * @param tertiaryAbility Tertiary {@link Ability} of {@link Role}
     * @param ultraAbility Ultra {@link Ability} of {@link Role}
     * @param toolSets {@link ArrayList} of {@link ToolSet} for {@link Role}
     * @param itemStack {@link ItemStack} logo
     * @param description Description of {@link Role}
     * 
     * @throws RoleCreationException when failed to create.
     */
    public Role(String name, int bdr, int bsb, int bm, Ability primaryAbility, Ability secondaryAbility, Ability tertiaryAbility, Ability ultraAbility, ArrayList<ToolSet> toolSets, ItemStack itemStack, String description) throws RoleCreationException {

        this.name = name;
        this.bdr = bdr;
        this.bsb = bsb;
        this.bm = bm;
        this.toolSets = toolSets;

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

        if (chestplate != 1 || leggings != 1 || boots != 1 || weapon != 1)
            throw new RoleCreationException("Too many or too little ToolSets have been set!");

        if (primaryAbility != null)
            this.primaryAbility = primaryAbility;

        else
            try {
                this.primaryAbility = new NoAbility(AbilityType.PRIMARY);
            } catch (AbilityCreationException e) {
                e.printStackTrace();
            }

        if (secondaryAbility != null)
            this.secondaryAbility = secondaryAbility;

        else
            try {
                this.secondaryAbility = new NoAbility(AbilityType.SECONDARY);
            } catch (AbilityCreationException e) {
                e.printStackTrace();
            }

        if (tertiaryAbility != null)
            this.tertiaryAbility = tertiaryAbility;

        else
            try {
                this.tertiaryAbility = new NoAbility(AbilityType.TERTIARY);
            } catch (AbilityCreationException e) {
                e.printStackTrace();
            }

        if (ultraAbility != null)
            this.ultraAbility = ultraAbility;

        else
            try {
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
     * @return Base damage reduction in percent
     */
    public int getBaseDamageReduction() {
        return bdr;
    }

    /**
     * @return Base speed of ability in percent
     */
    public int getBaseSpeedBoost() {
        return bsb;
    }

    /**
     * @return Base mana of ability
     */
    public int getBaseMana() {
        return bm;
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
