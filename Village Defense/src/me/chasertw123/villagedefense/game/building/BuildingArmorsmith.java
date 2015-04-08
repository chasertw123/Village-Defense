package me.chasertw123.villagedefense.game.building;

import java.io.File;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.BuildingCreationException;
import me.chasertw123.villagedefense.exceptions.BuildingSpawnException;
import me.chasertw123.villagedefense.game.villager.VillagerArmorsmith;
import me.chasertw123.villagedefense.utils.SchematicUtil;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BuildingArmorsmith extends Building {

    public BuildingArmorsmith(Location center) throws BuildingCreationException {
        super(BuildingType.ARMORSMITH, center, new VillagerArmorsmith(center), 1, new ItemStack(Material.IRON_CHESTPLATE), "This building allows you and your teammates to upgrade your armor so you can absorb more damage! The higher level the building the stringer armor you can buy.");
    }

    @Override
    public void levelUp(Main plugin) {

        if (getTier() >= getMaxTier())
            return;

        setTier(getTier() + 1);

        try {
            SchematicUtil.build(new File(plugin.getDataFolder() + File.separator + "schematics", "Armorsmith" + getTier() + ".schematic"), this, true);
        } catch (BuildingSpawnException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buildFirstTier(Main plugin) {
        try {
            SchematicUtil.build(new File(plugin.getDataFolder() + File.separator + "schematics", "Armorsmith" + getTier() + ".schematic"), this, false);
        } catch (BuildingSpawnException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int costToUpgrade(int tier) {
        return tier == 1 ? 0 : ((tier - 1) * 2000) - 500;
    }
}
