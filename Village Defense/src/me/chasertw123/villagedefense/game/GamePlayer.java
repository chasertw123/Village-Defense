package me.chasertw123.villagedefense.game;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.chasertw123.villagedefense.game.role.Role;

public class GamePlayer {

	private Role role;
	private UUID player_uuid;
	private int gold = 0, mana = 0, dr = 100, sb = 100;
	
	public GamePlayer(Role role, UUID player_uuid) {
		this.role = role;
		this.player_uuid = player_uuid;
	}
	
	public void setPlayer(UUID player) {
		this.player_uuid=player;
	}
	
	public void setPlayer(Player p) {
		setPlayer(p.getUniqueId());
	}
	
	public UUID getPlayerUUID() {
		return player_uuid;
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(player_uuid);
	}

	public String getPlayerName() {
		return getPlayer().getName();
	}
	
	public String getPlayerDisplayName() {
		return getPlayer().getDisplayName();
	}
	
	public void setRole(Role role) {
		this.role = role;
		
		dr = role.getBaseDamageReduction();
		sb = role.getBaseSpeedBoost();
		mana = role.getBaseMana();
	}
	
	public Role getRole() {
		return role;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public void incrementGold(int gold) {
		setGold(getGold() + gold);
	}
	
	public void decrementGold(int gold) {
		setMana(getMana() - mana);
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
		updateManaBar();
	}
	
	public void incrementMana(int mana) {
		setMana(getMana() + mana);
	}
	
	public void decrementMana(int mana) {
		setMana(getMana() - mana);
	}

	public int getDamageReduction() {
		return dr;
	}

	public void setDamageReduction(int dr) {
		this.dr = dr;
	}

	public int getSpeedBoost() {
		return sb;
	}

	public void setSpeedBoost(int sb) {
		this.sb = sb;
	}
	
	public void applySpeedBoost() {
		getPlayer().setWalkSpeed(0.2F * (sb / 100F));
	}
	
	public void updateManaBar() {
		getPlayer().setLevel(mana);
	}
}
