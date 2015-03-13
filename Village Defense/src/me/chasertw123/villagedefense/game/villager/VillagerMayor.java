package me.chasertw123.villagedefense.game.villager;

import java.util.Random;

import me.chasertw123.villagedefense.game.GamePlayer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VillagerMayor extends Villager {

    private String[] names = { "Randy", "Jeffery", "Bill", "Billy", "John" };
    private String name;

    public VillagerMayor(Location loc) {
        super(loc);

        Random random = new Random();
        name = names[random.nextInt(names.length)];
    }

    @Override
    protected org.bukkit.entity.Villager placeVillagerInWorld() {

        ArmorStand as = (ArmorStand) getLoc().getWorld().spawnEntity(getLoc().clone().subtract(0, 1.4812500178814, 0), EntityType.ARMOR_STAND);
        org.bukkit.entity.Villager vil = (org.bukkit.entity.Villager) getLoc().getWorld().spawnEntity(getLoc(), EntityType.VILLAGER);

        vil.setAdult();
        vil.setProfession(Profession.LIBRARIAN);
        vil.setCustomName(ChatColor.BLUE + "Mayor " + name);
        vil.setCustomNameVisible(true);
        as.setPassenger(vil);
        as.setVisible(false);
        as.setGravity(false);
        as.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255));

        return vil;
    }

    @Override
    public Inventory makeInventory(GamePlayer player) {
        // TODO Auto-generated method stub
        return null;
    }

}
