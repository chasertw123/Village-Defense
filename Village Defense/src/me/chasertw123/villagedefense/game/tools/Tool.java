package me.chasertw123.villagedefense.game.tools;

import org.bukkit.inventory.ItemStack;

public class Tool {

    private ItemStack itemStack;
    private int cost, buildingTier;

    public Tool(ItemStack itemStack, int cost, int buildingTier) {
        this.itemStack = itemStack;
        this.cost = cost;
        this.buildingTier = buildingTier;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getCost() {
        return cost;
    }

    public int getRequiredBuildingTier() {
        return buildingTier;
    }
}
