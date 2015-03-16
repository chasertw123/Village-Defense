package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.GameState;
import me.chasertw123.villagedefense.game.building.Building;
import me.chasertw123.villagedefense.game.role.Role;
import me.chasertw123.villagedefense.game.role.RoleSelect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntity implements Listener {

    private Main plugin;

    public PlayerInteractEntity(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {

        if (plugin.getGame().getGameState() == GameState.INGAME) {

            if (event.getPlayer().getItemInHand() != null) {

                GamePlayer gp = plugin.getGame().getGamePlayer(event.getPlayer());
                Role role = gp.getRole();

                if (role == null)
                    return;

                if (role.getName().equals("Healer")) {

                    if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(role.getPrimaryAbility().getItemStack().getItemMeta().getDisplayName())) {

                        if (!role.getPrimaryAbility().canUseAbility()) {
                            gp.sendMessage(plugin.getPrefix() + ChatColor.BLUE + role.getPrimaryAbility().getName() + ChatColor.YELLOW + " is still on cooldown for " + ChatColor.BLUE + role.getPrimaryAbility().getTimeRemaining() + ChatColor.YELLOW + " seconds!");
                            return;
                        }

                        if (gp.getMana() < role.getPrimaryAbility().getManaCost()) {
                            gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "Not enough mana!");
                            gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1F, 1F);
                            return;
                        }

                        if (!(event.getRightClicked() instanceof Player))
                            return;

                        Player healed = (Player) event.getRightClicked();

                        if (healed.getHealth() >= healed.getMaxHealth()) {
                            gp.sendMessage(plugin.getPrefix() + healed.getName() + " already has full health!");
                            return;
                        }

                        role.getPrimaryAbility().play(plugin, gp.getPlayer(), healed);
                        return;
                    }
                }

            }
        }

        /* Upgrading buildings, debug code */
        for (Building b : plugin.getGame().getArena().getBuildings())
            if (event.getRightClicked() == b.getVillager().getVil()) {

                for (GamePlayer gp : plugin.getGame().getPlayers())
                    if (gp.isEqualToPlayer(event.getPlayer())) {
                        gp.getPlayer().openInventory(b.getVillager().makeInventory(gp));
                    }

                if (b.getTier() < b.getMaxTier()) {
                    b.levelUp(plugin);
                    Bukkit.broadcastMessage(plugin.getPrefix() + event.getPlayer().getName() + " upgarded " + b.getName() + " to tier " + b.getTier());
                }

                event.setCancelled(true);
            }

        if (plugin.getGame().getGameState() == GameState.LOBBY || plugin.getGame().getGameState() == GameState.STARTING)
            for (RoleSelect rs : RoleSelect.roleSelectObjects)
                if (event.getRightClicked().equals(rs.getEntity()))
                    try {
                        for (GamePlayer gp : plugin.getGame().getPlayers())
                            if (gp.isEqualToPlayer(event.getPlayer())) {

                                Role role = rs.getRole().newInstance();

                                if (gp.getRole() == null || !gp.getRole().getName().equals(role.getName())) {
                                    gp.setRole(role);
                                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 1F, 1F);
                                    event.getPlayer().sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You have selected the " + ChatColor.BLUE + role.getName() + ChatColor.YELLOW + " role!");
                                }

                                else {
                                    gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You already have the role " + ChatColor.BLUE + role.getName() + ChatColor.YELLOW + " selected!");
                                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.CREEPER_HISS, 1F, 1F);
                                }
                            }
                    } catch (InstantiationException | IllegalAccessException e) {
                    }
    }
}
