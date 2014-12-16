package me.chasertw123.villagedefense.game;

import java.util.HashMap;
import java.util.UUID;

import me.chasertw123.villagedefense.game.role.Role;
import me.chasertw123.villagedefense.game.tools.ToolType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GamePlayer {

    private Role role;
    private UUID player_uuid;
    private int gold = 0, mana = 0, maxMana = 0, dr = 100, sb = 100;
    private HashMap<ToolType, Integer> toolTiers;
    private boolean dead;

    /**
     * Makes a new {@link GamePlayer} instance
     * 
     * @param role {@link Role} of the new {@link GamePlayer} instance
     * @param player_uuid {@link UUID} of the new {@link GamePlayer} instance
     */
    public GamePlayer(Role role, UUID player_uuid) {
        this.role = role;
        this.player_uuid = player_uuid;

        toolTiers = new HashMap<>();

        for (ToolType type : ToolType.values())
            toolTiers.put(type, 1);
    }

    /**
     * Makes a new {@link GamePlayer} instance
     * 
     * @param role {@link Role} of the new {@link GamePlayer} instance
     * @param player {@link Player} of the new {@link GamePlayer} instance
     */
    public GamePlayer(Role role, Player player) {
        this(role, player.getUniqueId());
    }

    /**
     * Update UUID
     * 
     * @param player {@link UUID} of this {@link GamePlayer} instance
     */
    public void setPlayer(UUID player) {
        this.player_uuid = player;
    }

    /**
     * Update Player
     * 
     * @param p {@link Player} of this {@link GamePlayer} instance
     */
    public void setPlayer(Player p) {
        setPlayer(p.getUniqueId());
    }

    /**
     * @return {@link UUID} of this {@link GamePlayer} instance
     */
    public UUID getPlayerUUID() {
        return player_uuid;
    }

    /**
     * @return {@link Player} of {@link GamePlayer}
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(player_uuid);
    }

    /**
     * Same as <code>gp.getPlayer().getName()</code>
     * 
     * @return Name of {@link Player} of this {@link GamePlayer} instance
     */
    public String getPlayerName() {
        return getPlayer().getName();
    }

    /**
     * Same as <code>gp.getPlayer().getDisplayName()</code>
     * 
     * @return Displayname of {@link Player} of this {@link GamePlayer} instance
     */
    public String getPlayerDisplayName() {
        return getPlayer().getDisplayName();
    }

    /**
     * Updates role of {@link GamePlayer}
     * 
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
     * @return {@link Role} of this {@link GamePlayer} instance
     * @see #setRole(Role)
     */
    public Role getRole() {
        return role;
    }

    /**
     * @return Current gold of this {@link GamePlayer} instance
     * @see #setGold(int)
     */
    public int getGold() {
        return gold;
    }

    /**
     * Update gold.
     * 
     * @param gold New gold amount
     * @see #getGold()
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * Increment gold with specified amount same as
     * <code>setGold(getGold() + amount)</code>
     * 
     * @param gold to add to total
     * @see #setGold(int)
     */
    public void incrementGold(int gold) {
        setGold(getGold() + gold);
    }

    /**
     * Decrement gold with specified amount same as
     * <code>setGold(getGold() - amount)</code>
     * 
     * @param gold to remove from total
     * @see #setGold(int)
     */
    public void decrementGold(int gold) {
        setMana(getMana() - mana);
    }

    /**
     * Get max mana of {@link GamePlayer}
     * 
     * @return the max mana
     * @see #setMaxMana(int)
     */
    public int getMaxMana() {
        return maxMana;
    }

    /**
     * Update max mana
     * 
     * @param mana new max mana amount
     * @see #getMana()
     */
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    /**
     * Increment max mana with specified amount same as
     * <code>setMaxMana(getMaxMana() + amount)</code>
     * 
     * @param mana to add to total max
     * @see #setMaxMana(int)
     */
    public void incrementMaxMana(int maxMana) {
        setMaxMana(getMaxMana() + maxMana);
    }

    /**
     * Decrement max mana with specified amount same as
     * <code>setMaxMana(getMaxMana() - amount)</code>
     * 
     * @param mana to add to total max
     * @see #setMaxMana(int)
     */
    public void decrementMaxMana(int maxMana) {
        setMaxMana(getMaxMana() - maxMana);
    }

    /**
     * Get mana of {@link GamePlayer}
     * 
     * @return the mana
     * @see #setMana(int)
     */
    public int getMana() {
        return mana;
    }

    /**
     * Update mana
     * 
     * @param mana new mana amount
     * @see #getMana()
     */
    public void setMana(int mana) {
        this.mana = mana;
        updateManaBar();
    }

    /**
     * Increment mana with specified amount same as
     * <code>setMana(getMana() + amount)</code>
     * 
     * @param mana to add to total
     * @see #setMana(int)
     */
    public void incrementMana(int mana) {
        setMana(getMana() + mana);
    }

    /**
     * Decrement mana with specified amount same as
     * <code>setMana(getMana() - amount)</code>
     * 
     * @param mana to add to total
     * @see #setMana(int)
     */
    public void decrementMana(int mana) {
        setMana(getMana() - mana);
    }

    /**
     * Get damagereduction amount, 100 = default
     * 
     * @return Damage reduction amount in %
     * @see {@link #setDamageReduction(int)} to set value
     */
    public int getDamageReduction() {
        return dr;
    }

    /**
     * Set damage reduction
     * 
     * @param dr new amount in %
     * @see {@link #getDamageReduction()} to get value
     */
    public void setDamageReduction(int dr) {
        this.dr = dr;
    }

    /**
     * Get speedboost, 100 = default
     * 
     * @return Speed boost amount in %
     * @see {@link #setSpeedBoost(int)} to set and {@link #applySpeedBoost()} to
     * apply value
     */
    public int getSpeedBoost() {
        return sb;
    }

    /**
     * Set speedboost, 100 = default
     * 
     * @param sb Speed boost amount in %
     * @see {@link #getSpeedBoost()} to get and {@link #applySpeedBoost()} to
     * apply value
     */
    public void setSpeedBoost(int sb) {
        this.sb = sb;
    }

    /**
     * Apply speedboost to player
     * 
     * @see {@link #getSpeedBoost()} to get and {@link #setSpeedBoost(int)} to
     * set value
     */
    public void applySpeedBoost() {
        getPlayer().setWalkSpeed(0.2F * (sb / 100F));
    }

    /**
     * Update mana bar
     * 
     * @see {@link #getMana()} to get and {@link #setMana(int)} to set value
     */
    public void updateManaBar() {
        getPlayer().setLevel(mana);
    }

    /**
     * 
     * @return dead if player is death
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Update if the player is alive
     * 
     * @param dead the dead to set
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Get upgrade tier of specified {@link ToolType}
     * 
     * @param type {@link ToolType} to get tool tier of
     * @return tool tier
     */
    public int getToolTier(ToolType type) {
        return toolTiers.get(type);
    }

    /**
     * Get toolTiers {@link HashMap}
     * 
     * @return toolTiers {@link HashMap}
     */
    public HashMap<ToolType, Integer> getToolTiers() {
        return toolTiers;
    }

    /**
     * Set a upgrade tier
     * 
     * @param type {@link ToolType} type to set
     * @param tier tier of
     */
    public void setToolTier(ToolType type, int tier) {
        toolTiers.put(type, tier);

        switch (type) {

            case CHESTPLATE:
                getPlayer().getInventory().setChestplate(role.getItemStack(type, tier));
                break;

            case LEGGINGS:
                getPlayer().getInventory().setLeggings(role.getItemStack(type, tier));
                break;

            case BOOTS:
                getPlayer().getInventory().setBoots(role.getItemStack(type, tier));
                break;

            case WEAPON:
                getPlayer().getInventory().setItem(0, role.getItemStack(type, tier));
                break;

            default:
                break;
        }
    }

    /**
     * Set {@link HashMap} upgradetiers
     * 
     * @param toolTiers the toolTiers to set
     */
    public void setToolTiers(HashMap<ToolType, Integer> toolTiers) {
        this.toolTiers = toolTiers;

        for (ToolType type : ToolType.values()) {

            int tier = toolTiers.get(type);

            switch (type) {

                case CHESTPLATE:
                    getPlayer().getInventory().setChestplate(role.getItemStack(type, tier));
                    break;

                case LEGGINGS:
                    getPlayer().getInventory().setLeggings(role.getItemStack(type, tier));
                    break;

                case BOOTS:
                    getPlayer().getInventory().setBoots(role.getItemStack(type, tier));
                    break;

                case WEAPON:
                    getPlayer().getInventory().setItem(0, role.getItemStack(type, tier));
                    break;

                default:
                    break;
            }
        }
    }
}
