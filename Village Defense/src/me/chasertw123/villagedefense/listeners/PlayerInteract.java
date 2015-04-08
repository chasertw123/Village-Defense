package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.GameState;
import me.chasertw123.villagedefense.game.role.Role;
import me.chasertw123.villagedefense.utils.ItemStackUtils;

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

            if (plugin.getGame() != null)
                if (plugin.getGame().getGameState() == GameState.INGAME) {

                    if (event.getPlayer().getItemInHand() != null) {

                        GamePlayer gp = plugin.getGame().getGamePlayer(event.getPlayer());
                        Role role = gp.getRole();

                        if (role == null)
                            return;

                        if (event.getPlayer().isSneaking() && role.getAvailableUpgrades() > 0) {

                            if (ItemStackUtils.areItemStacksSimilar(gp.getPlayer().getItemInHand(), role.getPrimaryAbility().getItemStack())) {

                                event.setCancelled(true);

                                if (role.getPrimaryAbility().getTier() < role.getPrimaryAbility().getMaxTier()) {
                                    role.getPrimaryAbility().setTier(role.getPrimaryAbility().getTier() + 1);
                                    gp.getPlayer().getInventory().setItemInHand(role.getPrimaryAbility().getItemStack());
                                    gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You have leveled up the " + ChatColor.BLUE + role.getPrimaryAbility().getName() + ChatColor.YELLOW + " ability to level " + ChatColor.BLUE + role.getPrimaryAbility().getTier() + ChatColor.YELLOW + "!");
                                    return;
                                }

                                gp.sendMessage(plugin.getPrefix() + ChatColor.BLUE + role.getPrimaryAbility().getName() + ChatColor.YELLOW + " is already at it's max level!");
                                gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1F, 1F);
                                return;
                            }

                            else if (ItemStackUtils.areItemStacksSimilar(gp.getPlayer().getItemInHand(), role.getSecondaryAbility().getItemStack())) {

                                event.setCancelled(true);

                                if (role.getSecondaryAbility().getTier() < role.getSecondaryAbility().getMaxTier()) {
                                    role.getSecondaryAbility().setTier(role.getSecondaryAbility().getTier() + 1);
                                    gp.getPlayer().getInventory().setItemInHand(role.getSecondaryAbility().getItemStack());
                                    gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You have leveled up the " + ChatColor.BLUE + role.getSecondaryAbility().getName() + ChatColor.YELLOW + " ability to level " + ChatColor.BLUE + role.getSecondaryAbility().getTier() + ChatColor.YELLOW + "!");
                                    return;
                                }

                                gp.sendMessage(plugin.getPrefix() + ChatColor.BLUE + role.getSecondaryAbility().getName() + ChatColor.YELLOW + " is already at it's max level!");
                                gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1F, 1F);
                                return;
                            }

                            else if (ItemStackUtils.areItemStacksSimilar(gp.getPlayer().getItemInHand(), role.getTertiaryAbility().getItemStack())) {

                                event.setCancelled(true);

                                if (role.getTertiaryAbility().getTier() < role.getTertiaryAbility().getMaxTier()) {
                                    role.getTertiaryAbility().setTier(role.getTertiaryAbility().getTier() + 1);
                                    gp.getPlayer().getInventory().setItemInHand(role.getTertiaryAbility().getItemStack());
                                    gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You have leveled up the " + ChatColor.BLUE + role.getTertiaryAbility().getName() + ChatColor.YELLOW + " ability to level " + ChatColor.BLUE + role.getTertiaryAbility().getTier() + ChatColor.YELLOW + "!");
                                    return;
                                }

                                gp.sendMessage(plugin.getPrefix() + ChatColor.BLUE + role.getTertiaryAbility().getName() + ChatColor.YELLOW + " is already at it's max level!");
                                gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1F, 1F);
                                return;
                            }

                            else if (ItemStackUtils.areItemStacksSimilar(gp.getPlayer().getItemInHand(), role.getUltraAbility().getItemStack())) {

                                event.setCancelled(true);

                                if (role.getUltraAbility().getTier() < role.getUltraAbility().getMaxTier()) {
                                    role.getUltraAbility().setTier(role.getUltraAbility().getTier() + 1);
                                    gp.getPlayer().getInventory().setItemInHand(role.getUltraAbility().getItemStack());
                                    gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You have leveled up the " + ChatColor.BLUE + role.getUltraAbility().getName() + ChatColor.YELLOW + " ability to level " + ChatColor.BLUE + role.getUltraAbility().getTier() + ChatColor.YELLOW + "!");
                                    return;
                                }

                                gp.sendMessage(plugin.getPrefix() + ChatColor.BLUE + role.getUltraAbility().getName() + ChatColor.YELLOW + " is already at it's max level!");
                                gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1F, 1F);
                                return;
                            }

                            return;
                        }

                        if (role.getName().equals("Healer")) {

                            if (ItemStackUtils.areItemStacksSimilar(gp.getPlayer().getItemInHand(), role.getPrimaryAbility().getItemStack())) {

                                event.setCancelled(true);

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

                            else if (ItemStackUtils.areItemStacksSimilar(gp.getPlayer().getItemInHand(), role.getSecondaryAbility().getItemStack())) {

                                event.setCancelled(true);

                                if (!role.getSecondaryAbility().canUseAbility()) {
                                    gp.sendMessage(plugin.getPrefix() + ChatColor.BLUE + role.getSecondaryAbility().getName() + ChatColor.YELLOW + " is still on cooldown for " + ChatColor.BLUE + role.getSecondaryAbility().getTimeRemaining() + ChatColor.YELLOW + " seconds!");
                                    return;
                                }

                                if (gp.getMana() < role.getPrimaryAbility().getManaCost()) {
                                    gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "Not enough mana!");
                                    gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1F, 1F);
                                    return;
                                }

                                role.getSecondaryAbility().play(plugin, gp.getPlayer(), gp.getPlayer());
                                return;
                            }
                        }

                        else if (role.getName().equals("Assassin")) {

                            event.setCancelled(true);

                            if (ItemStackUtils.areItemStacksSimilar(gp.getPlayer().getItemInHand(), role.getPrimaryAbility().getItemStack())) {

                                if (!role.getPrimaryAbility().canUseAbility()) {
                                    gp.sendMessage(plugin.getPrefix() + ChatColor.BLUE + role.getPrimaryAbility().getName() + ChatColor.YELLOW + " is still on cooldown for " + ChatColor.BLUE + role.getPrimaryAbility().getTimeRemaining() + ChatColor.YELLOW + " seconds!");
                                    return;
                                }

                                if (gp.getMana() < role.getPrimaryAbility().getManaCost()) {
                                    gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "Not enough mana!");
                                    gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1F, 1F);
                                    return;
                                }

                                role.getPrimaryAbility().play(plugin, gp.getPlayer());
                                return;
                            }
                        }
                    }
                }
        }
    }
}
