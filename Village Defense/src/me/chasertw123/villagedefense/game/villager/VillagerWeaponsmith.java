package me.chasertw123.villagedefense.game.villager;

import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.tools.ToolType;
import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VillagerWeaponsmith extends Villager {

    public VillagerWeaponsmith(Location loc) {
        super(loc);
    }

    @Override
    protected org.bukkit.entity.Villager placeVillagerInWorld() {
        ArmorStand as = (ArmorStand) getLoc().getWorld().spawnEntity(getLoc().clone().subtract(0, 1.4812500178814, 0), EntityType.ARMOR_STAND);
        org.bukkit.entity.Villager vil = (org.bukkit.entity.Villager) getLoc().getWorld().spawnEntity(getLoc(), EntityType.VILLAGER);

        vil.setAdult();
        vil.setProfession(Profession.BLACKSMITH);
        vil.setCustomName("Lob");
        vil.setCustomNameVisible(true);
        as.setPassenger(vil);
        as.setVisible(false);
        as.setGravity(false);
        as.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255));

        return vil;
    }

    @Override
    public Inventory makeInventory(GamePlayer player) {

        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.UNDERLINE + "Weapon Upgrades" + ChatColor.RESET + " (" + player.getRole().getName() + ")");

        if (player.getToolTier(ToolType.WEAPON) >= player.getRole().getMaxTier(ToolType.WEAPON))
            for (int slot = 0; slot < inv.getSize(); slot++)
                inv.setItem(slot, new FancyItemStack(Material.WOOL, 1, (short) 15, ChatColor.YELLOW + "[" + ChatColor.BLUE + "Weapon Level Maxed!" + ChatColor.YELLOW + "]"));

        else if (player.getRole().getBuildingTier(ToolType.WEAPON, player.getToolTier(ToolType.WEAPON) + 1) < getBuilding().getTier())
            for (int slot = 0; slot < inv.getSize(); slot++)
                inv.setItem(slot, new FancyItemStack(Material.WOOL, 1, (short) 15, ChatColor.YELLOW + "[" + ChatColor.BLUE + "Building Level Too Low!" + ChatColor.YELLOW + "]"));

        else {

            inv.setItem(1, new FancyItemStack(Material.WOOL, 1, (short) 15, ChatColor.DARK_PURPLE + "Current Item -->"));
            inv.setItem(5, new FancyItemStack(Material.WOOL, 1, (short) 15, ChatColor.DARK_PURPLE + "Current Item -->"));
            inv.setItem(3, new FancyItemStack(Material.WOOL, 1, (short) 15, ChatColor.DARK_PURPLE + "<-- Current Item"));
            inv.setItem(7, new FancyItemStack(Material.WOOL, 1, (short) 15, ChatColor.DARK_PURPLE + "<-- Current Item"));
            inv.setItem(2, player.getRole().getItemStack(ToolType.WEAPON, player.getToolTier(ToolType.WEAPON)));
            inv.setItem(6, new FancyItemStack(player.getRole().getItemStack(ToolType.WEAPON, player.getToolTier(ToolType.WEAPON) + 1)).setLore("", ChatColor.GOLD + "Price: " + ChatColor.AQUA + player.getRole().getCost(ToolType.WEAPON, player.getToolTier(ToolType.WEAPON) + 1)));
        }

        return inv;
    }
}
