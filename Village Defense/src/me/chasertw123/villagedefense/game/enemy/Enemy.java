package me.chasertw123.villagedefense.game.enemy;

import java.util.Random;

import me.chasertw123.villagedefense.exceptions.InvaildEnemySpawnException;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public abstract class Enemy {

	private EntityType entityType;
	private int minDroppedGold = 0, maxDroppedGold = 0;
	
	private ItemStack weapon = null;
	private ItemStack[] armor = null;
	private PotionEffect[] potionEffects = null;
	
	public Enemy(EntityType entityType, int minDroppedGold, int maxDroppedGold) {
		this.entityType = entityType;
		this.minDroppedGold = minDroppedGold;
		this.maxDroppedGold = maxDroppedGold;
	}
	
	public EntityType getEntityType() {
		return entityType;
	}
	
	public ItemStack getWeaponItemStack() {
		return weapon;
	}
	
	public void setWeaponItemStack(ItemStack weapon) {
		this.weapon = weapon;
	}
	
	public ItemStack[] getArmorContents() {
		return armor;
	}
	
	public void setArmorContents(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
		this.armor = new ItemStack[] {helmet, chestplate, leggings, boots};
	}
	
	public PotionEffect[] getPotionEffects() {
		return potionEffects;
	}
	
	public void setPotionEffects(PotionEffect[] potionEffects) {
		this.potionEffects = potionEffects;
	}
	
	public void spawnEntity(Location spawnLocation) throws InvaildEnemySpawnException {
		
		Entity e = spawnLocation.getWorld().spawnEntity(spawnLocation, entityType);
		
		if (!(e instanceof LivingEntity)) {
			e.remove();
			
			throw new InvaildEnemySpawnException("A " + entityType.toString() + " is not a Living entity. It must be"
					+ " a living entity in order to spawn properly.");
		}
			
		LivingEntity entity = (LivingEntity) e;
		
		entity.setCustomName(Math.max(minDroppedGold, new Random().nextInt(maxDroppedGold) + 1) + "");
		entity.setCustomNameVisible(false);
		
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
