package me.chasertw123.villagedefense.game.building;

import java.io.File;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.BuildingCreationException;
import me.chasertw123.villagedefense.game.villager.VillagerWeaponsmith;
import me.chasertw123.villagedefense.utils.SchematicUtil;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BuildingWeaponsmith extends Building {

    public BuildingWeaponsmith(Location center) throws BuildingCreationException {
        super(BuildingType.WEAPONSMITH, center, new VillagerWeaponsmith(center), 1, new ItemStack(Material.IRON_SWORD), "This building allows you and your teammates to upgrade your weapons so they deal more damage! The higher level the building the stronger the weapons you can buy.");
    }

    @Override
    public void levelUp(Main plugin) {
        // Build new tier, kill villagers, spawn new villager, update menus/
        if (getTier() >= getMaxTier())
            return;

        setTier(getTier() + 1);

        // Generate structure and spawn villagers
        SchematicUtil.build(new File(plugin.getDataFolder() + File.separator + "schematics", "Weaponsmith" + getTier() + ".schematic"), this, true);

    }

    @Override
    public void buildFirstTier(Main plugin) {
        // Generate structure and spawn villagers
        SchematicUtil.build(new File(plugin.getDataFolder() + File.separator + "schematics", "Weaponsmith" + getTier() + ".schematic"), this, false);
    }

    @Override
    public int costToUpgrade(int tier) {
        return tier == 1 ? 0 : ((tier - 1) * 2000) - 500;
    }
}
