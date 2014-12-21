package me.chasertw123.villagedefense.game.villager;

import me.chasertw123.villagedefense.game.GamePlayer;

import org.bukkit.Location;
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
        // TODO Auto-generated method stub
        return null;
    }

}
