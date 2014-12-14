package me.chasertw123.villagedefense.game.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import me.chasertw123.villagedefense.exceptions.AbilityCreationException;
import me.chasertw123.villagedefense.exceptions.RoleCreationException;
import me.chasertw123.villagedefense.game.abilities.Ability;
import me.chasertw123.villagedefense.game.abilities.AbilityType;
import me.chasertw123.villagedefense.game.abilities.NoAbility;
import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public abstract class Role {

    private String name;
    private int bdr, bsb, bm;
    private HashMap<ToolType, Integer> maxTier;
    private Ability primaryAbility, secondaryAbility, tertiaryAbility, ultraAbility;
    private ItemStack itemStack;

    public static ArrayList<Class<? extends Role>> roleClasses = new ArrayList<Class<? extends Role>>();

    public static void registerRole(Class<? extends Role> role) {
        roleClasses.add(role);

        try {
            Role r = role.newInstance();
            System.out.println("Loaded role " + r.getName());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Role(String name, int bdr, int bsb, int bm, Ability primaryAbility, Ability secondaryAbility, Ability tertiaryAbility, Ability ultraAbility, HashMap<ToolType, Integer> maxTier, ItemStack itemStack, String description) throws RoleCreationException {

        this.name = name;
        this.bdr = bdr;
        this.bsb = bsb;
        this.maxTier = maxTier;

        for (Entry<ToolType, Integer> entry : maxTier.entrySet())
            if (entry.getValue() < 1)
                throw new RoleCreationException("Max tier of " + entry.getKey() + " is < 1");

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
     * Get an {@link ItemStack} for {@link ToolType} for specified tier
     * 
     * @param type {@link ToolType} of {@link ItemStack}
     * @param tier Tier of armor
     * @return {@link ItemStack} representing {@link ToolType} of tier
     */
    public abstract ItemStack getItemStack(ToolType type, int tier);

    /**
     * Get price of {@link ToolType} for specified tier
     * 
     * @param type {@link ToolType}
     * @param tier Tier of armor
     * @return price of {@link ToolType} for tier
     */
    public abstract int getCost(ToolType type, int tier);

    /**
     * Set max tier of specified {@link ToolType} to tier
     * 
     * @param type {@link ToolType} to set
     * @param tier new tier
     */
    public void setMaxTier(ToolType type, int tier) {
        maxTier.put(type, tier);
    }

    /**
     * Get max tier of specified {@link ToolType}
     * 
     * @param type {@link ToolType} to get max tier of
     * @return max tier of {@link ToolType}
     */
    public int getMaxTier(ToolType type) {
        return maxTier.get(type);
    }

    /**
     * Set max tiers {@link HashMap}
     * 
     * @param maxTiers {@link HashMap} map of maxtiers
     */
    public void setMaxTiers(HashMap<ToolType, Integer> maxTiers) {
        maxTier = maxTiers;
    }

    /**
     * Get maxtiers map
     * 
     * @return {@link HashMap} of {@link ToolType} and it's corrisponding max
     * tier
     */
    public HashMap<ToolType, Integer> getMaxTiers() {
        return maxTier;
    }

    public static HashMap<ToolType, Integer> allOneMaxTiers() {
        HashMap<ToolType, Integer> maxTier = new HashMap<>();

        for (ToolType type : ToolType.values())
            maxTier.put(type, 1);

        return maxTier;
    }

}
