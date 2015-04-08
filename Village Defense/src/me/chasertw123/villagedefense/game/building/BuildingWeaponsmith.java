package me.chasertw123.villagedefense.game.building;

import java.io.File;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.BuildingCreationException;
import me.chasertw123.villagedefense.exceptions.BuildingSpawnException;
import me.chasertw123.villagedefense.game.villager.VillagerWeaponsmith;
import me.chasertw123.villagedefense.utils.SchematicUtil;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BuildingWeaponsmith extends Building {

    public BuildingWeaponsmith(Location center) throws BuildingCreationException {
        super(BuildingType.WEAPONSMITH, center, new VillagerWeaponsmith(center), 1, new ItemStack(Material.BOW), "This building allows you and your teammates to upgrade your weapons so they deal more damage! The higher level the building the stronger the weapons you can buy.");
    }

    @Override
    public void levelUp(Main plugin) {

        if (getTier() >= getMaxTier())
            return;

        setTier(getTier() + 1);

        try {
            SchematicUtil.build(new File(plugin.getDataFolder() + File.separator + "schematics", "Weaponsmith" + getTier() + ".schematic"), this, true);
        } catch (BuildingSpawnException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void buildFirstTier(Main plugin) {
        try {
            SchematicUtil.build(new File(plugin.getDataFolder() + File.separator + "schematics", "Weaponsmith" + getTier() + ".schematic"), this, false);
        } catch (BuildingSpawnException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int costToUpgrade(int tier) {
        return tier == 1 ? 0 : ((tier - 1) * 2000) - 500;
    }
}
