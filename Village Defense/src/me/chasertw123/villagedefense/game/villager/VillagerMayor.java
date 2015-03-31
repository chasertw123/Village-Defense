package me.chasertw123.villagedefense.game.villager;

import java.util.Random;

import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.building.Building;
import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.Bukkit;
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

        int total = Building.buildingObjects.size();
        int totalRows = (int) Math.ceil(total / 4.0);

        Inventory inv = Bukkit.createInventory(null, totalRows * 9, ChatColor.UNDERLINE + "Building Upgrade Menu");

        int count = 0;
        for (Building b : Building.buildingObjects) {

            int currentRow = (int) Math.floor(count / 4.0);
            int collumn = 0;

            if (currentRow == totalRows) {

                if (total % 4 == 0)
                    collumn = (count % 4) * 2 + 1;

                else if (total % 4 == 1)
                    collumn = 5;

                else if (total % 4 == 2)
                    if (count % 4 == 1)
                        collumn = 4;
                    else
                        collumn = 6;

                else if (count % 4 == 1)
                    collumn = 3;

                else if (count % 4 == 1)
                    collumn = 5;

                else
                    collumn = 7;

            } else {
                collumn = (count % 4) * 2 + 1;
            }

            int slot = currentRow * 9 + collumn;

            FancyItemStack is = new FancyItemStack(b.getItemStack());

            if (b.getTier() != b.getMaxTier()) {
                is.addLore(ChatColor.BLUE + "Cost to Upgrade: " + ChatColor.GOLD + b.costToUpgrade(b.getTier() + 1), "");

                if (b.getTier() >= this.getBuilding().getTier() + 1)
                    is.addLore(ChatColor.RED + "Not Upgrabeable");

                else
                    is.addLore(ChatColor.GREEN + "Upgradeable");
            }

            inv.setItem(slot, is);

            count++;
        }

        return inv;
    }
}
