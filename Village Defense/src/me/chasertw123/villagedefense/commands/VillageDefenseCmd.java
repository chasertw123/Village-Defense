package me.chasertw123.villagedefense.commands;

import java.util.ArrayList;
import java.util.List;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.building.Building;
import me.chasertw123.villagedefense.game.role.Role;
import me.chasertw123.villagedefense.utils.LocationUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VillageDefenseCmd implements CommandExecutor {

    private Main plugin;

    public VillageDefenseCmd(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(plugin.getPrefix() + "/vd arena");
            sender.sendMessage(plugin.getPrefix() + "/vd role");
        } else {
            if (args[0].equalsIgnoreCase("arena")) {
                if (!sender.hasPermission("villagedefense.admin")) {
                    sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "You don't have permission for this command");
                    return true;
                } else if (args.length == 1 || args[1].equalsIgnoreCase("help")) {
                    sender.sendMessage(plugin.getPrefix() + "/vd arena addenemyspawn");
                    sender.sendMessage(plugin.getPrefix() + "/vd arena removeenemyspawn");
                    sender.sendMessage(plugin.getPrefix() + "/vd arena setbuilding");
                    sender.sendMessage(plugin.getPrefix() + "/vd arena setspawn");
                    sender.sendMessage(plugin.getPrefix() + "/vd arena setroleselect");
                } else if (args[1].equalsIgnoreCase("addenemyspawn")) {

                    if (args.length == 2) {
                        sender.sendMessage(plugin.getPrefix() + "/vd arena addenemyspawn");
                        return true;
                    } else if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getPrefix() + "You need to be ingame for this command!");
                        return true;
                    } else {
                        List<String> ls = (plugin.getArenaConfig().contains("enemyspawns")) ? plugin.getArenaConfig().getStringList("enemyspawns") : new ArrayList<String>();
                        ls.add(LocationUtils.serializeLoc(((Player) sender).getLocation()));
                        plugin.getArenaConfig().set("enemyspawns", ls);
                        plugin.saveArenaConfig();
                        sender.sendMessage(plugin.getPrefix() + "You have added a spawnpoint with id " + (ls.size() - 1) + " at your location.");
                    }
                } else if (args[1].equalsIgnoreCase("removeenemyspawn")) {

                    if (args.length == 2) {
                        sender.sendMessage(plugin.getPrefix() + "/vd arena removeenemyspawn <id>");
                        return true;
                    } else if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getPrefix() + "You need to be ingame for this command!");
                        return true;
                    } else if (!isInt(args[2])) {
                        sender.sendMessage(plugin.getPrefix() + "This is not a number!");
                        return true;
                    } else {
                        List<String> ls = (plugin.getArenaConfig().contains("enemyspawns")) ? plugin.getArenaConfig().getStringList("enemyspawns") : new ArrayList<String>();
                        try {
                            ls.remove(Integer.parseInt(args[2]));
                            plugin.getArenaConfig().set("enemyspawns", ls);
                            plugin.saveArenaConfig();
                            sender.sendMessage(plugin.getPrefix() + "You have removed " + args[2] + " from the enemies.");
                        } catch (IndexOutOfBoundsException e) {
                            sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Input is either too high or too low!");
                        }
                    }
                } else if (args[1].equalsIgnoreCase("setbuilding")) {
                    if (args.length == 2) {
                        sender.sendMessage(plugin.getPrefix() + "/vd arena setbuilding <building>");
                        String s = "";
                        for (Class<? extends Building> b : Building.buildingClasses) {
                            try {
                                if (s.equals(""))
                                    s = b.getSimpleName();
                                else
                                    s = s + ", " + b.getSimpleName();
                            } catch (Exception e) {
                            }

                        }
                        sender.sendMessage(plugin.getPrefix() + "Available buildings: " + s);
                        return true;
                    } else if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getPrefix() + "You need to be ingame for this command!");
                        return true;
                    } else {
                        for (Class<? extends Building> b : Building.buildingClasses)
                            if (b.getSimpleName().equalsIgnoreCase(args[2])) {
                                plugin.getArenaConfig().set("buildings." + b.getSimpleName(), LocationUtils.serializeLoc(((Player) sender).getLocation()));
                                plugin.saveArenaConfig();
                                sender.sendMessage(plugin.getPrefix() + "You have set " + b.getSimpleName() + " to your location.");
                                return true;
                            }
                        sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Building not found!");

                    }
                } else if (args[1].equalsIgnoreCase("setspawn")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getPrefix() + "You need to be ingame for this command!");
                        return true;
                    } else {
                        plugin.getArenaConfig().set("spawn", LocationUtils.serializeLoc(((Player) sender).getLocation()));
                        plugin.saveArenaConfig();
                        sender.sendMessage(plugin.getPrefix() + "You have set spawn to your location.");
                    }
                } else if (args[1].equalsIgnoreCase("setroleselect")) {
                    if (args.length == 2) {
                        sender.sendMessage(plugin.getPrefix() + "/vd arena setroleselect <role>");
                        String s = "";
                        for (Class<? extends Role> role : Role.roleClasses.keySet()) {
                            try {
                                Role r = role.newInstance();
                                if (s.equals(""))
                                    s = r.getName();
                                else
                                    s = s + ", " + r.getName();
                            } catch (Exception e) {
                            }

                        }
                        sender.sendMessage(plugin.getPrefix() + "Available roles: " + s);
                        return true;
                    } else if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getPrefix() + "You need to be ingame for this command!");
                        return true;
                    } else {
                        for (Class<? extends Role> role : Role.roleClasses.keySet()) {
                            try {
                                Role r = role.newInstance();
                                if (r.getName().equalsIgnoreCase(args[2])) {
                                    plugin.getArenaConfig().set("roleselector." + r.getName(), LocationUtils.serializeLoc(((Player) sender).getLocation()));
                                    plugin.saveArenaConfig();
                                    sender.sendMessage(plugin.getPrefix() + "You have set " + r.getName() + "'s selector to your location.");
                                    return true;
                                }
                            } catch (Exception e) {
                            }

                        }
                        sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Role not found!");
                    }
                } else {
                    sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Unknown subcommand");
                }

            } else if (args[0].equalsIgnoreCase("role")) {
                if (args.length == 1 || args[1].equalsIgnoreCase("help")) {
                    sender.sendMessage(plugin.getPrefix() + "/vd role <name of role>");
                    String s = "";
                    for (Class<? extends Role> role : Role.roleClasses.keySet()) {
                        try {
                            Role r = role.newInstance();
                            if (s.equals(""))
                                s = r.getName();
                            else
                                s = s + ", " + r.getName();
                        } catch (Exception e) {
                        }

                    }
                    sender.sendMessage(plugin.getPrefix() + "Available roles: " + s);
                } else {
                    for (Class<? extends Role> role : Role.roleClasses.keySet()) {
                        try {
                            Role r = role.newInstance();
                            if (r.getName().equalsIgnoreCase(args[1])) {
                                // TODO: set role
                                return true;
                            }
                        } catch (Exception e) {
                        }

                    }
                    sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Role not found!");
                }

            }
        }

        return true;
    }

    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
