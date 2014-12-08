package me.chasertw123.villagedefense.game;

import java.util.UUID;

import me.chasertw123.villagedefense.game.role.Role;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GamePlayer {

	private Role role;
	private UUID player_uuid;
	private int gold = 0, mana = 0, dr = 100, sb = 100;
	
	/**
	 * Makes a new {@link GamePlayer} instance
	 * @param role {@link Role} of the new {@link GamePlayer} instance
	 * @param player_uuid {@link UUID} of the new {@link GamePlayer} instance
	 */
	public GamePlayer(Role role, UUID player_uuid) {
		this.role = role;
		this.player_uuid = player_uuid;
	}
	
	/**
	 * Makes a new {@link GamePlayer} instance
	 * @param role {@link Role} of the new {@link GamePlayer} instance
	 * @param player {@link Player} of the new {@link GamePlayer} instance
	 */
	public GamePlayer(Role role, Player player) {
		this(role, player.getUniqueId());
	}
	
	/**
	 * Update UUID
	 * @param player {@link UUID} of this {@link GamePlayer} instance
	 */
	public void setPlayer(UUID player) {
		this.player_uuid=player;
	}
	
	/**
	 * Update Player
	 * @param p {@link Player} of this {@link GamePlayer} instance
	 */
	public void setPlayer(Player p) {
		setPlayer(p.getUniqueId());
	}
	
	/**
	 * 
	 * @return {@link UUID} of this {@link GamePlayer} instance
	 */
	public UUID getPlayerUUID() {
		return player_uuid;
	}
	
	/**
	 * 
	 * @return {@link Player} of {@link GamePlayer}
	 */
	public Player getPlayer() {
		return Bukkit.getPlayer(player_uuid);
	}

	/**
	 * Same as 
	 * <code>gp.getPlayer().getName()</code>
	 * @return Name of {@link Player} of this {@link GamePlayer} instance
	 */
	public String getPlayerName() {
		return getPlayer().getName();
	}
	
	/**
	 * Same as 
	 * <code>gp.getPlayer().getDisplayName()</code>
	 * @return Displayname of {@link Player} of this {@link GamePlayer} instance
	 */
	public String getPlayerDisplayName() {
		return getPlayer().getDisplayName();
	}
	
	/**
	 * Updates role of {@link GamePlayer}
	 * @param role The new {@link Role}
	 * @see #getRole()
	 */
	public void setRole(Role role) {
		this.role = role;
		
		dr = role.getBaseDamageReduction();
		sb = role.getBaseSpeedBoost();
		mana = role.getBaseMana();
	}
	
	/**
	 * 
	 * @return {@link Role} of this {@link GamePlayer} instance
	 * @see #setRole(Role)
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * 
	 * @return Current gold of this {@link GamePlayer} instance
	 * @see #setGold(int)
	 */
	public int getGold() {
		return gold;
	}

	/**
	 * Update gold.
	 * @param gold New gold amount
	 * @see #getGold()
	 */
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	/**
	 * Increment gold with specified amount same as
	 * <code>setGold(getGold() + amount)</code>
	 * @param gold to add to total
	 * @see #setGold(int)
	 */
	public void incrementGold(int gold) {
		setGold(getGold() + gold);
	}

	/**
	 * Decrement gold with specified amount same as
	 * <code>setGold(getGold() - amount)</code>
	 * @param gold to remove from total
	 * @see #setGold(int)
	 */
	public void decrementGold(int gold) {
		setMana(getMana() - mana);
	}

	/**
	 * Get mana of {@link GamePlayer}
	 * @return the mana
	 * @see #setMana(int)
	 */
	public int getMana() {
		return mana;
	}

	/**
	 * Update mana
	 * @param mana new mana amound
	 * @see #getMana()
	 */
	public void setMana(int mana) {
		this.mana = mana;
		updateManaBar();
	}

	/**
	 * Increment mana with specified amount same as
	 * <code>setMana(getMana() + amount)</code>
	 * @param mana to add to total
	 * @see #setMana(int)
	 */
	public void incrementMana(int mana) {
		setMana(getMana() + mana);
	}

	/**
	 * Decrement mana with specified amount same as
	 * <code>setMana(getMana() - amount)</code>
	 * @param mana to add to total
	 * @see #setMana(int)
	 */
	public void decrementMana(int mana) {
		setMana(getMana() - mana);
	}

	/**
	 * Get damagereduction amount, 100 = default
	 * @return Damage reduction amount in %
	 * @see {@link #setDamageReduction(int)} to set value
	 */
	public int getDamageReduction() {
		return dr;
	}

	/**
	 * Set damage reduction
	 * @param dr new amount in %
	 * @see {@link #getDamageReduction()} to get value
	 */
	public void setDamageReduction(int dr) {
		this.dr = dr;
	}

	/**
	 * Get speedboost, 100 = default
	 * @return Speed boost amount in %
	 * @see {@link #setSpeedBoost(int)} to set and {@link #applySpeedBoost()} to apply value
	 */
	public int getSpeedBoost() {
		return sb;
	}

	/**
	 * Set speedboost, 100 = default
	 * @param sb Speed boost amount in %
	 * @see {@link #getSpeedBoost()} to get and {@link #applySpeedBoost()} to apply value
	 */
	public void setSpeedBoost(int sb) {
		this.sb = sb;
	}
	
	/**
	 * Apply speedboost to player
	 * @see {@link #getSpeedBoost()} to get and {@link #setSpeedBoost(int)} to set value
	 */
	public void applySpeedBoost() {
		getPlayer().setWalkSpeed(0.2F * (sb / 100F));
	}
	
	/**
	 * Update mana bar
	 * @see {@link #getMana()} to get and {@link #setMana(int)} to set value
	 */
	public void updateManaBar() {
		getPlayer().setLevel(mana);
	}
}
