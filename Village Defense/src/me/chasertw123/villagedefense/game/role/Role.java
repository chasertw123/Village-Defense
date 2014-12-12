package me.chasertw123.villagedefense.game.role;

import me.chasertw123.villagedefense.exceptions.AbilityCreationException;
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
    private Ability primaryAbility, secondaryAbility, tertiaryAbility, ultraAbility;
    private ItemStack itemStack;

    public Role(String name, int bdr, int bsb, int bm, Ability primaryAbility, Ability secondaryAbility, Ability tertiaryAbility, Ability ultraAbility, ItemStack itemStack, String description) {

        this.name = name;
        this.bdr = bdr;
        this.bsb = bsb;

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

    /** @return Name of role */
    public String getName() {
        return name;
    }

    /** @return Base damage reduction in percent */
    public int getBaseDamageReduction() {
        return bdr;
    }

    /** @return Base speed of ability in percent */
    public int getBaseSpeedBoost() {
        return bsb;
    }

    /** @return Base mana of ability */
    public int getBaseMana() {
        return bm;
    }

    /** @return Role's primary ability */
    public Ability getPrimaryAbility() {
        return primaryAbility;
    }

    /** @return Role's secondary ability */
    public Ability getSecondaryAbility() {
        return secondaryAbility;
    }

    /** @return Role's tertiary ability */
    public Ability getTertiaryAbility() {
        return tertiaryAbility;
    }

    /** @return Role's ultra ability */
    public Ability getUltraAbility() {
        return ultraAbility;
    }

    /** @return ItemStack representing role */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /** Banner/Block to use on head.
     * 
     * @return {@link ItemStack} with banner, used in
     *         {@link PlayerInventory#setHelmet(ItemStack)} when ingame. */
    public abstract ItemStack getBanner();
}
