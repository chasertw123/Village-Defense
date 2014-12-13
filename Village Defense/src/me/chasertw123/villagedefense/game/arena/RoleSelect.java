package me.chasertw123.villagedefense.game.arena;

import java.util.ArrayList;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.role.Role;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RoleSelect {

    private EntityType entityType;
    private Location spawnLocation;
    private Role role;
    private LivingEntity entity;

    public static ArrayList<RoleSelect> roleSelectObjects = new ArrayList<RoleSelect>();

    public RoleSelect(Location spawnLocation, EntityType entityType, Role role) {
        this.spawnLocation = spawnLocation;
        this.entityType = entityType;
        this.role = role;

        roleSelectObjects.add(this);
    }

    public void spawnEntity(Main plugin) {

        Entity e = spawnLocation.getWorld().spawnEntity(spawnLocation, entityType);

        if (!(e instanceof LivingEntity))
            e.remove();

        LivingEntity entity = (LivingEntity) e;

        this.entity = entity;

        entity.setMetadata("roleselect", new FixedMetadataValue(plugin, role.getName()));

        entity.setCustomName(ChatColor.LIGHT_PURPLE + role.getName());
        entity.setCustomNameVisible(true);

        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255));
    }

    /**
     * @return the entity
     */
    public LivingEntity getEntity() {
        return entity;
    }
}
