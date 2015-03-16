package me.chasertw123.villagedefense.game.villager;

import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.utils.FancyItemStack;
import me.chasertw123.villagedefense.utils.StringUtils;

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

public class VillagerFarmer extends Villager {

    public VillagerFarmer(Location loc) {
        super(loc);
    }

    @Override
    protected org.bukkit.entity.Villager placeVillagerInWorld() {

        ArmorStand as = (ArmorStand) getLoc().getWorld().spawnEntity(getLoc().clone().subtract(0, 1.4812500178814, 0), EntityType.ARMOR_STAND);
        org.bukkit.entity.Villager vil = (org.bukkit.entity.Villager) getLoc().getWorld().spawnEntity(getLoc(), EntityType.VILLAGER);

        vil.setAdult();
        vil.setProfession(Profession.FARMER);
        vil.setCustomName("Rob");
        vil.setCustomNameVisible(true);
        as.setPassenger(vil);
        as.setVisible(false);
        as.setGravity(false);
        as.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255));

        return vil;
    }

    @Override
    public Inventory makeInventory(GamePlayer player) {

        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.UNDERLINE + "Food Upgrade Menu");

        if (getBuilding().getTier() == 1) {
            inv.setItem(0, getFoodItem(Material.APPLE, 1, 1, 25, 2, 0.6));
            inv.setItem(2, getFoodItem(Material.APPLE, 2, 1, 25, 2, 0.6));
            inv.setItem(4, getFoodItem(Material.APPLE, 4, 1, 25, 2, 0.6));
            inv.setItem(6, getFoodItem(Material.APPLE, 6, 1, 25, 2, 0.6));
            inv.setItem(8, getFoodItem(Material.APPLE, 8, 1, 25, 2, 0.6));
        }

        else if (getBuilding().getTier() == 2) {
            inv.setItem(0, getFoodItem(Material.COOKED_CHICKEN, 1, 2, 45, 3, 1.2));
            inv.setItem(2, getFoodItem(Material.COOKED_CHICKEN, 2, 2, 45, 3, 1.2));
            inv.setItem(4, getFoodItem(Material.COOKED_CHICKEN, 4, 2, 45, 3, 1.2));
            inv.setItem(6, getFoodItem(Material.COOKED_CHICKEN, 6, 2, 45, 3, 1.2));
            inv.setItem(8, getFoodItem(Material.COOKED_CHICKEN, 8, 2, 45, 3, 1.2));
        }

        else if (getBuilding().getTier() == 3) {
            inv.setItem(0, getFoodItem(Material.COOKED_BEEF, 1, 3, 75, 4, 1.6));
            inv.setItem(2, getFoodItem(Material.COOKED_BEEF, 2, 3, 75, 4, 1.6));
            inv.setItem(4, getFoodItem(Material.COOKED_BEEF, 4, 3, 75, 4, 1.6));
            inv.setItem(6, getFoodItem(Material.COOKED_BEEF, 6, 3, 75, 4, 1.6));
            inv.setItem(8, getFoodItem(Material.COOKED_BEEF, 8, 3, 75, 4, 1.6));
        }

        else if (getBuilding().getTier() == 4) {
            inv.setItem(0, getFoodItem(Material.GOLDEN_APPLE, 1, 4, 100, 2, 2.4));
            inv.setItem(2, getFoodItem(Material.GOLDEN_APPLE, 2, 4, 100, 2, 2.4));
            inv.setItem(4, getFoodItem(Material.GOLDEN_APPLE, 4, 4, 100, 2, 2.4));
            inv.setItem(6, getFoodItem(Material.GOLDEN_APPLE, 6, 4, 100, 2, 2.4));
            inv.setItem(8, getFoodItem(Material.GOLDEN_APPLE, 8, 4, 100, 2, 2.4));
        }

        return inv;
    }

    private FancyItemStack getFoodItem(Material material, int amount, int tier, int priceperunit, double foodheal, double nourishment) {
        return new FancyItemStack(material, amount, ChatColor.GREEN + "" + ChatColor.BOLD + "Food" + StringUtils.fancyTierString(tier)).addLore("", ChatColor.GOLD + "Food Bars Filled: " + ChatColor.BLUE + foodheal, ChatColor.GOLD + "Saturation: " + ChatColor.BLUE + nourishment, ChatColor.GOLD + "Amount: " + ChatColor.BLUE + amount, "", ChatColor.GOLD + "Price: " + ChatColor.BLUE + (amount * priceperunit));
    }
}
