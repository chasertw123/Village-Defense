package me.chasertw123.villagedefense.game.enemy;

import org.bukkit.entity.EntityType;

public class Villager extends Enemy {

    public Villager() {
        super(EntityType.ZOMBIE, 1, 3, 2, 4);
        super.setZombieVillager(true);
    }

}
