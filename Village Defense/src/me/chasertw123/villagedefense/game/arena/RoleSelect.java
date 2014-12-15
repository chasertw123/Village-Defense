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
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RoleSelect {

    private EntityType entityType;
    private Location spawnLocation;
    private Role role;
    private LivingEntity entity;

    public static ArrayList<RoleSelect> roleSelectObjects = new ArrayList<RoleSelect>();

    /**
     * Create a new instance of {@link RoleSelect}
     * 
     * @param spawnLocation {@link Location} for {@link Entity} to spawn
     * @param entityType {@link EntityType} for the spawning {@link Entity}
     * @param role the {@link Role} selected when the {@link Entity} is
     * interacted with
     */
    public RoleSelect(Location spawnLocation, EntityType entityType, Role role) {
        this.spawnLocation = spawnLocation;
        this.entityType = entityType;
        this.role = role;

        roleSelectObjects.add(this);
    }

    /**
     * 
     * @return the {@link Role} of this {@link RoleSelect} instance
     */
    public Role getRole() {
        return role;
    }

    /**
     * 
     * @return the spawn {@link Location} of this {@link RoleSelect} instance
     */
    public Location getSpawnLocation() {
        return spawnLocation;
    }

    /**
     * 
     * @return the {@link LivingEntity} of this {@link RoleSelect} instance
     */
    public LivingEntity getEntity() {
        return entity;
    }

    /**
     * Spawn {@link Entity} with {@link FixedMetadataValue}
     * 
     * @param plugin {@link Plugin} for setting {@link FixedMetadataValue}
     */
    @Deprecated
    public void spawnEntity(Main plugin) {

        Entity e = spawnLocation.getWorld().spawnEntity(spawnLocation, entityType);

        if (!(e instanceof LivingEntity))
            e.remove();

        LivingEntity entity = (LivingEntity) e;

        this.entity = entity;

        /*
         * Most likly not needed because we are tracking the entity
         * entity.setMetadata("roleselect", new FixedMetadataValue(plugin, role.getName()));
         */

        entity.setCustomName(ChatColor.LIGHT_PURPLE + role.getName());
        entity.setCustomNameVisible(true);

        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255));
    }

    /**
     * 
     * Spawn {@link Entity} without {@link FixedMetadataValue}
     */
    public void spawnEntity() {

        Entity e = spawnLocation.getWorld().spawnEntity(spawnLocation, entityType);

        if (!(e instanceof LivingEntity))
            e.remove();

        LivingEntity entity = (LivingEntity) e;

        this.entity = entity;

        entity.setCustomName(ChatColor.LIGHT_PURPLE + role.getName());
        entity.setCustomNameVisible(true);

        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255));
    }

}
