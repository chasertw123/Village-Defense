package me.chasertw123.villagedefense.game.building;

import java.io.File;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.BuildingCreationException;
import me.chasertw123.villagedefense.exceptions.BuildingSpawnException;
import me.chasertw123.villagedefense.game.villager.VillagerMayor;
import me.chasertw123.villagedefense.utils.SchematicUtil;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BuildingTownhall extends Building {

    public BuildingTownhall(Location center) throws BuildingCreationException {
        super(BuildingType.TOWNHALL, center, new VillagerMayor(center), 1, new ItemStack(Material.NETHER_STAR), "This building allows you to upgrade all the buildings in the village including this one. This higher level this building the higher you can upgrade others.");
    }

    @Override
    public void levelUp(Main plugin) {

        if (getTier() >= getMaxTier())
            return;

        setTier(getTier() + 1);

        try {
            SchematicUtil.build(new File(plugin.getDataFolder() + File.separator + "schematics", "Townhall" + getTier() + ".schematic"), this, true);
        } catch (BuildingSpawnException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buildFirstTier(Main plugin) {
        try {
            SchematicUtil.build(new File(plugin.getDataFolder() + File.separator + "schematics", "Townhall" + getTier() + ".schematic"), this, false);
        } catch (BuildingSpawnException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int costToUpgrade(int tier) {
        return tier == 1 ? 0 : ((tier - 1) * 2500) - 500;
    }
}
