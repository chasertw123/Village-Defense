package me.chasertw123.villagedefense.game.enemy;

import java.util.ArrayList;
import java.util.Random;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.InvalidEnemySpawnExcpetion;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;

public abstract class Enemy {

    private EntityType entityType;
    private String customName = "";
    private boolean boss;
    private int minDroppedGold, maxDroppedGold, difficulty;

    private ItemStack weapon = null;
    private ItemStack[] armor = null;
    private PotionEffect[] potionEffects = null;

    public static ArrayList<Enemy> enemyObjects = new ArrayList<Enemy>();
    public static ArrayList<Enemy> bossEnemyObjects = new ArrayList<Enemy>();

    /**
     * Makes a new {@link Enemy} instance
     * 
     * @param entityType {@link EntityType} of the new {@link Enemy} instance
     * @param minDroppedGold amount of gold dropped per kill
     * @param maxDroppedGold amount of gold dropped per kill
     */
    public Enemy(EntityType entityType, int difficulty, int minDroppedGold, int maxDroppedGold, boolean boss) {
        this.entityType = entityType;
        this.difficulty = difficulty;
        this.minDroppedGold = minDroppedGold;
        this.maxDroppedGold = maxDroppedGold;
        this.boss = boss;

        if (boss)
            bossEnemyObjects.add(this);

        else
            enemyObjects.add(this);
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
     * @return {@link Boolean} of boss
     */
    public boolean isBoss() {
        return boss;
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
     * @param armor a 4 {@link ItemStack} array representing the armor
     */
    public void setArmorContents(ItemStack[] armor) {
        this.armor = armor;
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
     * @return {@link LivingEntity} spawned by this method
     * @throws InvaildEnemySpawnException when is not a {@link LivingEntity}
     */
    public LivingEntity spawnEntity(Location spawnLocation, Main plugin) throws InvalidEnemySpawnExcpetion {

        Entity e = spawnLocation.getWorld().spawnEntity(spawnLocation, entityType);

        if (!(e instanceof LivingEntity)) {
            e.remove();

            throw new InvalidEnemySpawnExcpetion("A " + entityType.toString() + " is not a Living entity. It must be" + " a living entity in order to spawn properly.");
        }

        Random random = new Random();
        LivingEntity entity = (LivingEntity) e;

        entity.setMetadata("gold", new FixedMetadataValue(plugin, (Math.max(minDroppedGold, random.nextInt(maxDroppedGold) + 1))));

        entity.setCustomName(customName);
        entity.setCustomNameVisible(true);

        if (armor != null)
            entity.getEquipment().setArmorContents(armor.clone());

        if (weapon != null)
            entity.getEquipment().setItemInHand(weapon.clone());

        entity.getEquipment().setHelmetDropChance(0F);
        entity.getEquipment().setChestplateDropChance(0F);
        entity.getEquipment().setLeggingsDropChance(0F);
        entity.getEquipment().setBootsDropChance(0F);
        entity.getEquipment().setItemInHandDropChance(0F);

        if (potionEffects != null)
            for (PotionEffect effect : potionEffects)
                entity.addPotionEffect(effect);

        entity.setFireTicks(0);
        entity.setHealth(entity.getMaxHealth());
        entity.setCanPickupItems(false);

        return entity;
    }
}
