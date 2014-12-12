package me.chasertw123.villagedefense.game.enemy;

import java.util.Random;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.InvalidEnemySpawnExcpetion;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;

public abstract class Enemy {

    private EntityType entityType;
    private String customName = "";
    private int minDroppedGold = 0, maxDroppedGold = 0, difficulty;

    private ItemStack weapon = null;
    private ItemStack[] armor = null;
    private PotionEffect[] potionEffects = null;

    /**
     * Makes a new {@link Enemy} instance
     * 
     * @param entityType {@link EntityType} of the new {@link Enemy} instance
     * @param minDroppedGold amount of gold dropped per kill
     * @param maxDroppedGold amount of gold dropped per kill
     */
    public Enemy(EntityType entityType, int difficulty, int minDroppedGold, int maxDroppedGold) {
        this.entityType = entityType;
        this.difficulty = difficulty;
        this.minDroppedGold = minDroppedGold;
        this.maxDroppedGold = maxDroppedGold;
    }

    /**
     * @return {@link EntityType} of this {@link Enemy} instance
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * @return the difficulty level of this {@link Enemy} instance
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * 
     * @return the name of this {@link Enemy} instance
     */
    public String getCustomName() {
        return customName;
    }

    /**
     * Update name above {@link Enemy} head
     * 
     * @param customName the new name
     */
    public void setCustomName(String customName) {
        this.customName = customName;
    }

    /**
     * @return {@link ItemStack} of the {@link Enemy} instance
     */
    public ItemStack getWeaponItemStack() {
        return weapon;
    }

    /**
     * Update Item in hand of this {@link Enemy} instance
     * 
     * @param weapon The new {@link ItemStack}
     */
    public void setWeaponItemStack(ItemStack weapon) {
        this.weapon = weapon;
    }

    /**
     * @return list of {@link ItemStack} that represent this {@link Enemy}
     * instance
     */
    public ItemStack[] getArmorContents() {
        return armor;
    }

    /**
     * Update the armor of this {@link Enemy} instance
     * 
     * @param helmet The {@link ItemStack} of the helmet
     * @param chestplate The {@link ItemStack} of the chestplate
     * @param leggings The {@link ItemStack} of the leggings
     * @param boots The {@link ItemStack} of the boots
     */
    public void setArmorContents(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        this.armor = new ItemStack[] { helmet, chestplate, leggings, boots };
    }

    /**
     * @return list of (@link PotionEffect} that represent this {@link Enemy}
     * instance
     */
    public PotionEffect[] getPotionEffects() {
        return potionEffects;
    }

    /**
     * Update the {@link PotionEffect} list of this {@link Enemy} instance
     * 
     * @param list of potionEffects that this (@link Enemy} instance will spawn
     * with
     */
    public void setPotionEffects(PotionEffect[] potionEffects) {
        this.potionEffects = potionEffects;
    }

    /**
     * Spawn the {@link Enemy} at the {@link Location}
     * 
     * @param spawnLocation the {@link Location} the {@link Enemy} will spawn
     * @throws InvaildEnemySpawnException when is not a {@link LivingEntity}
     */
    public void spawnEntity(Location spawnLocation, Main plugin) throws InvalidEnemySpawnExcpetion {

        Entity e = spawnLocation.getWorld().spawnEntity(spawnLocation, entityType);

        if (!(e instanceof LivingEntity)) {
            e.remove();

            throw new InvalidEnemySpawnExcpetion("A " + entityType.toString() + " is not a Living entity. It must be" + " a living entity in order to spawn properly.");
        }

        LivingEntity entity = (LivingEntity) e;

        entity.setMetadata("gold", new FixedMetadataValue(plugin, (Math.max(minDroppedGold, new Random().nextInt(maxDroppedGold) + 1))));
        for (MetadataValue mv : entity.getMetadata("gold"))
            Bukkit.broadcastMessage(mv.asString());

        entity.setCustomName(customName);
        entity.setCustomNameVisible(true);

        entity.getEquipment().setArmorContents(armor);
        entity.getEquipment().setHelmetDropChance(0F);
        entity.getEquipment().setChestplateDropChance(0F);
        entity.getEquipment().setLeggingsDropChance(0F);
        entity.getEquipment().setBootsDropChance(0F);

        entity.getEquipment().setItemInHand(weapon);
        entity.getEquipment().setItemInHandDropChance(0F);

        for (PotionEffect effect : potionEffects)
            entity.addPotionEffect(effect);

        entity.setFireTicks(0);
        entity.setHealth(entity.getMaxHealth());
        entity.setCanPickupItems(false);
    }
}
