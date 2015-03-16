package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.GameState;
import me.chasertw123.villagedefense.game.role.Role;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    private Main plugin;

    public PlayerInteract(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

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

                            if (gp.getPlayer().getHealth() >= gp.getPlayer().getMaxHealth()) {
                                gp.sendMessage(plugin.getPrefix() + "You already have full health!");
                                return;
                            }

                            role.getPrimaryAbility().play(plugin, gp.getPlayer(), gp.getPlayer());
                            return;
                        }
                    }
                }
            }
        }
    }
}
