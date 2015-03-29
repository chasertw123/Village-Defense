package me.chasertw123.villagedefense.game.building;

import java.io.File;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.BuildingCreationException;
import me.chasertw123.villagedefense.game.villager.VillagerBrewer;
import me.chasertw123.villagedefense.utils.SchematicUtil;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BuildingBrewery extends Building {

    public BuildingBrewery(Location center) throws BuildingCreationException {
        super(BuildingType.BREWERY, center, new VillagerBrewer(center), 1, new ItemStack(Material.POTION), "This building allows you to buy one time useable items to help you in dire situations!");
    }

    @Override
    public void levelUp(Main plugin) {
        // Build new tier, kill villagers, spawn new villager, update menus/
        if (getTier() >= getMaxTier())
            return;

        setTier(getTier() + 1);

        // Generate structure and spawn villagers
        SchematicUtil.build(new File(plugin.getDataFolder() + File.separator + "schematics", "Brewery" + getTier() + ".schematic"), this, true);

    }

    @Override
    public void buildFirstTier(Main plugin) {
        // Generate structure and spawn villagers
        SchematicUtil.build(new File(plugin.getDataFolder() + File.separator + "schematics", "Brewery" + getTier() + ".schematic"), this, false);
    }

    @Override
    public int costToUpgrade(int tier) {
        return tier == 1 ? 0 : ((tier - 1) * 1500) - 500;
    }
}
