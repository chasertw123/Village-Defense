package me.chasertw123.villagedefense.game.building;

import java.util.ArrayList;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.BuildingCreationException;
import me.chasertw123.villagedefense.game.villager.Villager;
import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public abstract class Building {

    private int tier = 1, maxTier;
    private BuildingType type;
    private Location center;
    private Villager villager;
    private String name, description;
    private ItemStack itemStack;

    public static ArrayList<Class<? extends Building>> buildingClasses = new ArrayList<Class<? extends Building>>();
    public static ArrayList<Building> buildingObjects = new ArrayList<Building>();

    public Building(BuildingType type, Location center, Villager villager, int maxTier, ItemStack itemStack, String description) throws BuildingCreationException {

        if (maxTier < 1)
            throw new BuildingCreationException("A building's max tier is lower than one!");

        if (!this.getClass().getSimpleName().startsWith("Building"))
            throw new BuildingCreationException("A building's class name is not setup properly!");

        this.type = type;
        this.center = center;
        this.villager = villager;
        this.maxTier = maxTier;
        this.name = this.getClass().getSimpleName().replaceAll("Building", "") + " Building";
        this.description = description;
        this.itemStack = itemStack;

        villager.setBuilding(this);
    }

    /**
     * @return Tier of {@link Building}
     */
    public int getTier() {
        return tier;
    }

    /**
     * Update tier of {@link Building}
     * 
     * @param tier , new tier of {@link Building}
     */
    public void setTier(int tier) {
        this.tier = tier;
    }

    /**
     * @return maxTier of {@link Building}
     */
    public int getMaxTier() {
        return maxTier;
    }

    /**
     * @return {@link BuildingType} of {@link Building}
     */
    public BuildingType getType() {
        return type;
    }

    /**
     * @return Center {@link Location} of {@link Building}
     */
    public Location getCenter() {
        return center;
    }

    /**
     * Level up an {@link Building} after setting the new tier.
     * 
     * @param plugin Instance of main class
     */
    public abstract void levelUp(Main plugin);

    /**
     * Initial startup tier, called on start of arena
     */
    public abstract void buildFirstTier(Main plugin);

    /**
     * Get the cost to upgrade to a tier
     * 
     * @param tier the tier you wish to get the cost to upgrade to
     * @return the amount it cost to upgrade to tier
     */
    public abstract int costToUpgrade(int tier);

    /**
     * @return the villager
     */
    public Villager getVillager() {
        return villager;
    }

    /**
     * @param villager the villager to set
     */
    public void setVillager(Villager villager) {
        this.villager = villager;
    }

    /**
     * Get name of {@link Building}
     * 
     * @return name of {@link Building}
     */
    public String getName() {
        return name;
    }

    /**
     * Get the {@link ItemStack} representing the {@link Building}
     * 
     * @return {@link ItemStack} representing the {@link Building}
     */
    public ItemStack getItemStack() {

        FancyItemStack is = new FancyItemStack(itemStack);

        is.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + name);

        is.addFancyLore(description, ChatColor.WHITE.toString());
        is.addLore("", ChatColor.BLUE + "Level: " + ChatColor.GOLD + tier + "/" + maxTier);

        return is;
    }

    /**
     * Add a {@link Building} to an {@link ArrayList}
     * 
     * @param building the {@link Class} to add to {@link Building}
     * {@link ArrayList}
     */
    public static void registerBuilding(Class<? extends Building> building) {
        buildingClasses.add(building);

        System.out.println("Registered building: " + building.getSimpleName());
    }
}
