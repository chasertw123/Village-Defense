package me.chasertw123.villagedefense.game;

import java.util.HashMap;
import java.util.UUID;

import me.chasertw123.villagedefense.game.role.Role;
import me.chasertw123.villagedefense.game.tools.ToolType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GamePlayer {

    private Role role;
    private UUID player_uuid;
    private int gold = 0, mana = 0;
    private HashMap<ToolType, Integer> toolTiers;
    private boolean dead = false, arrow = false;

    /**
     * Makes a new {@link GamePlayer} instance
     * 
     * @param role {@link Role} of the new {@link GamePlayer} instance
     * @param player_uuid {@link UUID} of the new {@link GamePlayer} instance
     */
    public GamePlayer(Role role, UUID player_uuid) {
        setRole(role);
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

        if (role != null) {
            getPlayer().getInventory().clear();
            getPlayer().getInventory().setArmorContents(null);
            getPlayer().getInventory().setHelmet(role.getBanner());

            mana = role.getMana();
        }
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
        setGold(getGold() - gold);
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
     * Apply speedboost to player
     * 
     * @see {@link #getSpeedBoost()} to get and {@link #setSpeedBoost(int)} to
     * set value
     */
    public void applySpeedBoost() {
        if (role != null)
            getPlayer().setWalkSpeed(0.2F * (getRole().getSpeedBoost() / 100F));
    }

    /**
     * Update mana bar
     * 
     * @see {@link #getMana()} to get and {@link #setMana(int)} to set value
     */
    public void updateManaBar() {

        float percent = (float) getMana() / getRole().getMana();

        if (percent >= 1)
            percent = 0.9999F;

        getPlayer().setExp(percent);
        getPlayer().setLevel(getMana());
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
     * 
     * @return {@link Boolean} if player needs arrow
     */
    public boolean needsArrow() {
        return arrow;
    }

    /**
     * Set if the {@link GamePlayer} has an arrow in their {@link Inventory}
     * 
     * @param arrow {@link Boolean} if {@link GamePlayer} needs an arrow in
     * their {@link Inventory}ory
     */
    public void setNeedsArrow(boolean arrow) {
        this.arrow = arrow;
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
     * Send the {@link GamePlayer} a message without getting the {@link Player}
     * 
     * @param message the {@link String} message you wish to send the
     * {@link GamePlayer}
     */
    public void sendMessage(String message) {
        getPlayer().sendMessage(message);
    }

    /**
     * Tell if a {@link GamePlayer} is equal to a {@link Player}
     * 
     * @param player {@link Player} to compare to {@link GamePlayer}
     * @return {@link Boolean} of if {@link GamePlayer} is equal to a
     * {@link Player}
     */
    public boolean isEqualToPlayer(Player player) {
        return getPlayer().getName().equals(player.getName());
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
