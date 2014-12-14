package me.chasertw123.villagedefense.commands;

import java.util.ArrayList;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.role.Role;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VillageDefenseCmd implements CommandExecutor {

    private Main plugin;

    public VillageDefenseCmd(Main plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(plugin.getPrefix() + "/vd arena");
            sender.sendMessage(plugin.getPrefix() + "/vd role");
        } else {
            if (args[0].equalsIgnoreCase("arena")) {
                if (args.length == 1 || args[1].equalsIgnoreCase("help")) {
                    sender.sendMessage(plugin.getPrefix() + "/vd arena addenemyspawn");
                    sender.sendMessage(plugin.getPrefix() + "/vd arena removenemyspawn");
                    sender.sendMessage(plugin.getPrefix() + "/vd arena setbuilding");
                    sender.sendMessage(plugin.getPrefix() + "/vd arena setspawn");
                } else if (args[1].equalsIgnoreCase("addenemyspawn")) {
                    // TODO: implement
                } else if (args[1].equalsIgnoreCase("removenemyspawn")) {
                    // TODO: implement
                } else if (args[1].equalsIgnoreCase("setbuilding")) {
                    // TODO: implement
                } else if (args[1].equalsIgnoreCase("setspawn")) {
                    // TODO: implement
                } else {
                    sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Unknown subcommand");
                }

            } else if (args[0].equalsIgnoreCase("role")) {
                if (args.length == 1 || args[1].equalsIgnoreCase("help")) {
                    sender.sendMessage(plugin.getPrefix() + "/vd role <name of role>");
                    String s = "";
                    for (Class<? extends Role> role : (ArrayList<Class<? extends Role>>) Role.roleClasses.clone()) {
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
                    for (Class<? extends Role> role : (ArrayList<Class<? extends Role>>) Role.roleClasses.clone()) {
                        try {
                            Role r = role.newInstance();
                            if (r.getName().equalsIgnoreCase(args[2])) {
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
}
