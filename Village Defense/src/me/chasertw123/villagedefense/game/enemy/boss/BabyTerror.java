package me.chasertw123.villagedefense.game.enemy.boss;

import me.chasertw123.villagedefense.game.enemy.Enemy;
import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BabyTerror extends Enemy {

    public BabyTerror() {
        super(EntityType.ZOMBIE, 100, 100, 250);

        super.setCustomName("Baby Terror");
        super.setBaby(true);
        super.setArmorContents(new FancyItemStack(Material.GOLD_HELMET).addUnsafeEnchant(Enchantment.DURABILITY, 20), new FancyItemStack(Material.GOLD_HELMET).addUnsafeEnchant(Enchantment.DURABILITY, 20), new FancyItemStack(Material.GOLD_HELMET).addUnsafeEnchant(Enchantment.DURABILITY, 20), new FancyItemStack(Material.GOLD_HELMET).addUnsafeEnchant(Enchantment.DURABILITY, 20));
        super.setWeaponItemStack(new FancyItemStack(Material.IRON_SWORD).addUnsafeEnchant(Enchantment.DAMAGE_ALL, 3).addUnsafeEnchant(Enchantment.KNOCKBACK, 5));
        super.setPotionEffects(new PotionEffect[] { new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3), new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2) });
    }
}
