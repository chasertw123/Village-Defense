package me.chasertw123.villagedefense.game.role;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RoleSelect {

    private EntityType entityType;
    private Location spawnLocation;
    private Class<? extends Role> role;
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
    public RoleSelect(Location spawnLocation, EntityType entityType, Class<? extends Role> role) {
        this.spawnLocation = spawnLocation;
        this.entityType = entityType;
        this.role = role;

        roleSelectObjects.add(this);
    }

    /**
     * 
     * @return the {@link Role} of this {@link RoleSelect} instance
     */
    public Class<? extends Role> getRole() {
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
     * 
     * Spawn {@link Entity} without {@link FixedMetadataValue}
     */
    public void spawnEntity() {

        ArmorStand as = (ArmorStand) spawnLocation.getWorld().spawnEntity(spawnLocation.clone().subtract(0, 1.4812500178814, 0), EntityType.ARMOR_STAND);
        Entity e = spawnLocation.getWorld().spawnEntity(spawnLocation.clone(), entityType);

        if (!(e instanceof LivingEntity))
            e.remove();

        LivingEntity entity = (LivingEntity) e;

        this.entity = entity;

        if (entity instanceof Monster) {
            ((Monster) entity).getEquipment().setHelmet(new ItemStack(Material.STONE_BUTTON));
            ((Monster) entity).getEquipment().setHelmetDropChance(0F);
        }

        entity.setCustomName(ChatColor.LIGHT_PURPLE + role.getSimpleName());
        entity.setCustomNameVisible(true);

        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255));

        entity.setRemoveWhenFarAway(false);
        as.setRemoveWhenFarAway(false);

        as.setPassenger(entity);
        as.setVisible(false);
        as.setGravity(false);
        as.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255));
    }

}
