package me.chasertw123.villagedefense.game.building;

import java.io.File;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.BuildingCreationException;
import me.chasertw123.villagedefense.game.villager.VillagerFarmer;
import me.chasertw123.villagedefense.utils.SchematicUtil;

import org.bukkit.Location;

public class BuildingFarmer extends Building {

    public BuildingFarmer(Location center) throws BuildingCreationException {
        super(BuildingType.FARMER, center, new VillagerFarmer(center), 3);
    }

    @Override
    public void levelUp(Main plugin) {
        // Build new tier, kill villagers, spawn new villager, update menus/
        if (getTier() >= getMaxTier())
            return;

        setTier(getTier() + 1);

        // Generate structure and spawn villagers
        SchematicUtil.build(new File(plugin.getDataFolder(), "Farmer" + getTier() + ".schematic"), this);

    }

    @Override
    public void buildFirstTier(Main plugin) {
        // Generate structure and spawn villagers
        SchematicUtil.build(new File(plugin.getDataFolder(), "Farmer" + getTier() + ".schematic"), this);
    }
}
