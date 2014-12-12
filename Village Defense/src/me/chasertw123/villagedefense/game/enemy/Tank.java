package me.chasertw123.villagedefense.game.enemy;

import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Tank extends Enemy {

    public Tank() {
        super(EntityType.IRON_GOLEM, 15, 8, 20);

        super.setPotionEffects(new PotionEffect[] { new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 2), new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1), new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1) });
    }

}
