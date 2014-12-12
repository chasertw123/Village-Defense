package me.chasertw123.villagedefense.game.building;

import java.io.File;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.BuildingCreationException;
import me.chasertw123.villagedefense.game.villager.VillagerFarmer;
import me.chasertw123.villagedefense.utils.SchematicUtil;

import org.bukkit.Location;

public class BuildingFarmer extends Building {

    public BuildingFarmer(Location center) throws BuildingCreationException {
        super(BuildingType.FARMER, center, new VillagerFarmer(center), 1);
    }

    @Override
    public void levelUp() {
        // Build new tier, kill villagers, spawn new villager, update menus/

        // Generate structure and spawn villagers
        SchematicUtil.build(new File(Main.getInstance().getDataFolder(), "Farmer" + getTier() + ".schematic"), this);

    }

    @Override
    public void buildFirstTier() {
        // Generate structure and spawn villagers
        SchematicUtil.build(new File(Main.getInstance().getDataFolder(), "Farmer" + getTier() + ".schematic"), this);
    }
}
