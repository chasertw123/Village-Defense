package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.GameState;
import me.chasertw123.villagedefense.game.building.Building;
import me.chasertw123.villagedefense.game.building.BuildingType;
import me.chasertw123.villagedefense.utils.FancyItemStack;
import me.chasertw123.villagedefense.utils.ItemStackUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    private Main plugin;

    public InventoryClick(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (plugin.getGame().getGameState() == GameState.INGAME) {

            if (event.getCurrentItem() == null && !(event.getWhoClicked() instanceof Player))
                return;

            GamePlayer gp = plugin.getGame().getGamePlayer((Player) event.getWhoClicked());

            if (event.getInventory().getName().contains("Gold Transfer")) {

                GamePlayer tgp = plugin.getGame().getGamePlayer(Bukkit.getPlayer(event.getInventory().getName().substring(16)));

                if (tgp == null) {
                    gp.sendMessage(plugin.getPrefix() + ChatColor.BLUE + event.getInventory().getName().substring(16) + ChatColor.YELLOW + " is no longer in the game!");
                    return;
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("1 Gold")) {

                    if (gp.getGold() < 1) {
                        gp.sendMessage(plugin.getPrefix() + "You do not have " + ChatColor.BLUE + "1" + ChatColor.YELLOW + " gold to spend!");
                        return;
                    }

                    gp.decrementGold(1);
                    tgp.incrementGold(1);
                    tgp.sendMessage(plugin.getPrefix() + ChatColor.BLUE + gp.getPlayerName() + ChatColor.YELLOW + " sent you " + ChatColor.BLUE + "1" + ChatColor.YELLOW + " gold!");
                    gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You sent " + ChatColor.BLUE + tgp.getPlayerName() + ChatColor.BLUE + " 1" + ChatColor.YELLOW + " gold!");

                    return;
                }

                else if (event.getCurrentItem().getItemMeta().getDisplayName().contains("10 Gold")) {

                    if (gp.getGold() < 10) {
                        gp.sendMessage(plugin.getPrefix() + "You do not have " + ChatColor.BLUE + "10" + ChatColor.YELLOW + " gold to spend!");
                        return;
                    }

                    gp.decrementGold(10);
                    tgp.incrementGold(10);
                    tgp.sendMessage(plugin.getPrefix() + ChatColor.BLUE + gp.getPlayerName() + ChatColor.YELLOW + " sent you " + ChatColor.BLUE + "10" + ChatColor.YELLOW + " gold!");
                    gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You sent " + ChatColor.BLUE + tgp.getPlayerName() + ChatColor.BLUE + " 10" + ChatColor.YELLOW + " gold!");

                    return;
                }

                else if (event.getCurrentItem().getItemMeta().getDisplayName().contains("100 Gold")) {

                    if (gp.getGold() < 100) {
                        gp.sendMessage(plugin.getPrefix() + "You do not have " + ChatColor.BLUE + "100" + ChatColor.YELLOW + " gold to spend!");
                        return;
                    }

                    gp.decrementGold(100);
                    tgp.incrementGold(100);
                    tgp.sendMessage(plugin.getPrefix() + ChatColor.BLUE + gp.getPlayerName() + ChatColor.YELLOW + " sent you " + ChatColor.BLUE + "100" + ChatColor.YELLOW + " gold!");
                    gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You sent " + ChatColor.BLUE + tgp.getPlayerName() + ChatColor.BLUE + " 100" + ChatColor.YELLOW + " gold!");

                    return;
                }
            }

            for (Building b : Building.buildingObjects) {

                if (b.getType() == BuildingType.TOWNHALL && b.getVillager().makeInventory(gp).getName().equals(event.getInventory().getName())) {

                    for (Building building : Building.buildingObjects)
                        if (ItemStackUtils.itemStacksHaveSameName(event.getCurrentItem(), building.getItemStack())) {

                            event.setCancelled(true);

                            if (building.getTier() >= building.getMaxTier()) {
                                gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "The " + ChatColor.BLUE + building.getName() + ChatColor.YELLOW + " is at it's max level!");
                                gp.getPlayer().closeInventory();
                                return;
                            }

                            if (building.getTier() >= b.getTier() + 1) {
                                gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "The " + ChatColor.BLUE + building.getName() + ChatColor.YELLOW + " is currently not upgradeable!");
                                gp.getPlayer().closeInventory();
                                return;
                            }

                            if (gp.getGold() - building.costToUpgrade(building.getTier() + 1) < 0) {

                                int goldNeeded = building.costToUpgrade(building.getTier() + 1) - gp.getGold();

                                gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "Insufficient Funds! You need " + ChatColor.BLUE + goldNeeded + ChatColor.YELLOW + " more gold to upgrade the " + ChatColor.BLUE + building.getName() + ChatColor.YELLOW + "!");
                                gp.getPlayer().closeInventory();
                                return;
                            }

                            gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You upgraded the " + ChatColor.BLUE + building.getName() + ChatColor.YELLOW + " to level " + ChatColor.BLUE + (building.getTier() + 1) + ChatColor.YELLOW + "!");
                            gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.LEVEL_UP, 1F, 1F);
                            gp.getPlayer().closeInventory();
                            gp.decrementGold(building.costToUpgrade(building.getTier() + 1));

                            for (GamePlayer p : plugin.getGame().getPlayers())
                                if (!p.isEqualToPlayer(gp.getPlayer()))
                                    p.sendMessage(plugin.getPrefix() + ChatColor.BLUE + gp.getPlayerName() + ChatColor.YELLOW + " upgraded the " + ChatColor.BLUE + building.getName() + ChatColor.YELLOW + " to level " + ChatColor.BLUE + (building.getTier() + 1) + ChatColor.YELLOW + "!");

                            building.levelUp(plugin);
                        }
                }

                else if (b.getType() == BuildingType.ARMORSMITH && b.getVillager().makeInventory(gp).getName().equals(event.getInventory().getName())) {
                    event.setCancelled(true);
                }

                else if (b.getType() == BuildingType.WEAPONSMITH && b.getVillager().makeInventory(gp).getName().equals(event.getInventory().getName())) {
                    event.setCancelled(true);
                }

                else if (b.getType() == BuildingType.BREWERY && b.getVillager().makeInventory(gp).getName().equals(event.getInventory().getName())) {
                    event.setCancelled(true);
                }

                else if (b.getType() == BuildingType.FARMER && b.getVillager().makeInventory(gp).getName().equals(event.getInventory().getName())) {

                    event.setCancelled(true);

                    int price = Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(5)).substring(7));

                    if (gp.getGold() - price < 0) {

                        int goldNeeded = price - gp.getGold();

                        gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "Insufficient Funds! You need " + ChatColor.BLUE + goldNeeded + ChatColor.YELLOW + " more gold to buy " + ChatColor.BLUE + event.getCurrentItem().getAmount() + ChatColor.YELLOW + " more piece" + (event.getCurrentItem().getAmount() == 1 ? "" : "s") + " of food!");
                        gp.getPlayer().closeInventory();
                        return;
                    }

                    FancyItemStack is = new FancyItemStack(event.getCurrentItem().getType(), event.getCurrentItem().getAmount(), event.getCurrentItem().getItemMeta().getDisplayName());

                    for (int i = 0; i <= 2; i++)
                        is.addLore(event.getCurrentItem().getItemMeta().getLore().get(i));

                    gp.decrementGold(price);
                    gp.getPlayer().closeInventory();
                    gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.BURP, 1F, 1F);
                    gp.getPlayer().getInventory().addItem(is);
                    gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You bought " + ChatColor.BLUE + event.getCurrentItem().getAmount() + ChatColor.YELLOW + " piece" + (event.getCurrentItem().getAmount() == 1 ? "" : "s") + " of food!");
                }

                else if (b.getType() == BuildingType.CHURCH && b.getVillager().makeInventory(gp).getName().equals(event.getInventory().getName())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
