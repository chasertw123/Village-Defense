package me.chasertw123.villagedefense.game.building;

import java.io.File;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.BuildingCreationException;
import me.chasertw123.villagedefense.exceptions.BuildingSpawnException;
import me.chasertw123.villagedefense.game.villager.VillagerFarmer;
import me.chasertw123.villagedefense.utils.SchematicUtil;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BuildingFarmer extends Building {

    public BuildingFarmer(Location center) throws BuildingCreationException {
        super(BuildingType.FARMER, center, new VillagerFarmer(center), 3, new ItemStack(Material.COOKIE), "This building supplies you with food to keep you regenerating hearts! The higher level the building the better the food!");
    }

    @Override
    public void levelUp(Main plugin) {

        if (getTier() >= getMaxTier())
            return;

        setTier(getTier() + 1);

        try {
            SchematicUtil.build(new File(plugin.getDataFolder() + File.separator + "schematics", "Farmer" + getTier() + ".schematic"), this, true);
        } catch (BuildingSpawnException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buildFirstTier(Main plugin) {
        try {
            SchematicUtil.build(new File(plugin.getDataFolder() + File.separator + "schematics", "Farmer" + getTier() + ".schematic"), this, false);
        } catch (BuildingSpawnException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int costToUpgrade(int tier) {
        return tier == 1 ? 0 : ((tier - 1) * 1500) - 250;
    }
}
