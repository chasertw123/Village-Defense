package me.chasertw123.villagedefense.game.enemy.boss;

import me.chasertw123.villagedefense.game.enemy.Enemy;
import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlackDeath extends Enemy {

    public BlackDeath() {
        super(EntityType.SKELETON, 100, 100, 250);

        super.setCustomName("Black Death");
        super.setSkeletonType(SkeletonType.WITHER);
        super.setWeaponItemStack(new FancyItemStack(Material.IRON_SWORD).addUnsafeEnchant(Enchantment.KNOCKBACK, 2));
        super.setPotionEffects(new PotionEffect[] { new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1) });
    }

}
