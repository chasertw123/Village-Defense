package me.chasertw123.villagedefense.game.villager;

import me.chasertw123.villagedefense.game.GamePlayer;

import org.bukkit.Location;
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
        org.bukkit.entity.Villager vil = (org.bukkit.entity.Villager) getLoc().getWorld().spawnEntity(getLoc(), EntityType.VILLAGER);

        vil.setAdult();
        vil.setProfession(Profession.FARMER);
        vil.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 100, true));
        vil.setCustomName("Rob");
        vil.setCustomNameVisible(true);

        return vil;
    }

    @Override
    public Inventory makeInventory(GamePlayer player) {
        // TODO Auto-generated method stub
        return null;
    }

}
