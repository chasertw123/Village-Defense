package me.chasertw123.villagedefense.commands;

import java.util.ArrayList;
import java.util.List;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.GameState;
import me.chasertw123.villagedefense.game.arena.Arena;
import me.chasertw123.villagedefense.game.building.Building;
import me.chasertw123.villagedefense.game.role.Role;
import me.chasertw123.villagedefense.game.scoreboard.ScoreboardType;
import me.chasertw123.villagedefense.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
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

        if (args.length == 0 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
            sender.sendMessage(plugin.getPrefix() + "/vd arena");
            sender.sendMessage(plugin.getPrefix() + "/vd role");
            sender.sendMessage(plugin.getPrefix() + "/vd gold");
            sender.sendMessage(plugin.getPrefix() + "/vd vote");
        }

        else {

            if (args[0].equalsIgnoreCase("arena")) {

                if (!sender.hasPermission("villagedefense.admin")) {
                    sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "You don't have permission for this command");
                    return true;
                }

                if (args.length == 1 || args[1].equalsIgnoreCase("help") || args[1].equalsIgnoreCase("?")) {
                    sender.sendMessage(plugin.getPrefix() + "- Arena specific commands -");
                    sender.sendMessage(plugin.getPrefix() + "/vd arena addenemyspawn");
                    sender.sendMessage(plugin.getPrefix() + "/vd arena removeenemyspawn");
                    sender.sendMessage(plugin.getPrefix() + "/vd arena setbuilding");
                    sender.sendMessage(plugin.getPrefix() + "/vd arena setspawn");
                    sender.sendMessage(plugin.getPrefix() + "- Arena unspecific commands -");
                    sender.sendMessage(plugin.getPrefix() + "/vd arena setlobby");
                    sender.sendMessage(plugin.getPrefix() + "/vd arena setroleselect");
                    return true;
                }

                else if (args[1].equalsIgnoreCase("addenemyspawn")) {

                    if (args.length != 3) {
                        sender.sendMessage(plugin.getPrefix() + "/vd arena addenemyspawn <arena>");
                        return true;
                    }

                    else if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getPrefix() + "You need to be ingame for this command!");
                        return true;
                    }

                    else {

                        String pre = "arena." + args[2] + ".";
                        List<String> ls = (plugin.getArenaConfig().contains(pre + "enemyspawns")) ? plugin.getArenaConfig().getStringList(pre + "enemyspawns") : new ArrayList<String>();

                        ls.add(Utils.serializeLoc(((Player) sender).getLocation()));
                        plugin.getArenaConfig().set(pre + "enemyspawns", ls);
                        plugin.saveArenaConfig();
                        sender.sendMessage(plugin.getPrefix() + "You have added a spawnpoint with id " + (ls.size() - 1) + " at your location.");
                    }
                }

                else if (args[1].equalsIgnoreCase("removeenemyspawn")) {

                    if (args.length != 4) {
                        sender.sendMessage(plugin.getPrefix() + "/vd arena removeenemyspawn <id> <arena>");
                        return true;
                    }

                    else if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getPrefix() + "You need to be ingame for this command!");
                        return true;
                    }

                    else if (!isInt(args[2])) {
                        sender.sendMessage(plugin.getPrefix() + "This is not a number!");
                        return true;
                    }

                    else {

                        String pre = "arena." + args[3] + ".";
                        List<String> ls = (plugin.getArenaConfig().contains(pre + "enemyspawns")) ? plugin.getArenaConfig().getStringList(pre + "enemyspawns") : new ArrayList<String>();

                        try {
                            ls.remove(Integer.parseInt(args[2]));
                            plugin.getArenaConfig().set(pre + "enemyspawns", ls);
                            plugin.saveArenaConfig();
                            sender.sendMessage(plugin.getPrefix() + "You have removed " + args[2] + " from the enemies.");
                        } catch (IndexOutOfBoundsException e) {
                            sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Input is either too high or too low!");
                        }
                    }
                }

                else if (args[1].equalsIgnoreCase("setbuilding")) {

                    if (args.length != 4) {
                        sender.sendMessage(plugin.getPrefix() + "/vd arena setbuilding <building> <arena>");
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
                    }

                    else if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getPrefix() + "You need to be ingame for this command!");
                        return true;
                    }

                    else {

                        String pre = "arena." + args[3] + ".";
                        for (Class<? extends Building> b : Building.buildingClasses)
                            if (b.getSimpleName().equalsIgnoreCase(args[2])) {
                                plugin.getArenaConfig().set(pre + "buildings." + b.getSimpleName(), Utils.serializeLoc(((Player) sender).getLocation()));
                                plugin.saveArenaConfig();
                                sender.sendMessage(plugin.getPrefix() + "You have set " + b.getSimpleName() + " to your location.");
                                return true;
                            }

                        sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Building not found!");
                    }
                }

                else if (args[1].equalsIgnoreCase("setspawn")) {

                    if (args.length != 3) {
                        sender.sendMessage(plugin.getPrefix() + "/vd arena setspawn <arena>");
                        return true;
                    }

                    if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getPrefix() + "You need to be ingame for this command!");
                        return true;
                    }

                    else {
                        String pre = "arena." + args[2] + ".";
                        plugin.getArenaConfig().set(pre + "spawn", Utils.serializeLoc(((Player) sender).getLocation()));
                        plugin.saveArenaConfig();
                        sender.sendMessage(plugin.getPrefix() + "You have set spawn to your location.");
                    }
                }

                else if (args[1].equalsIgnoreCase("setlobby")) {

                    if (args.length != 2) {
                        sender.sendMessage(plugin.getPrefix() + "/vd arena setlobby");
                    }

                    if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getPrefix() + "You need to be ingame for this command!");
                        return true;
                    }

                    else {
                        plugin.getArenaConfig().set("lobby", Utils.serializeLoc(((Player) sender).getLocation()));
                        plugin.saveArenaConfig();
                        sender.sendMessage(plugin.getPrefix() + "You have set lobby to your location.");
                    }
                }

                else if (args[1].equalsIgnoreCase("setroleselect")) {

                    if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getPrefix() + "You need to be ingame for this command!");
                        return true;
                    }

                    if (args.length != 3) {

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
                    }

                    else {

                        for (Class<? extends Role> role : Role.roleClasses.keySet()) {

                            try {

                                Role r = role.newInstance();

                                if (r.getName().equalsIgnoreCase(args[2])) {
                                    plugin.getArenaConfig().set("roleselector." + r.getName(), Utils.serializeLoc(((Player) sender).getLocation()));
                                    plugin.saveArenaConfig();
                                    sender.sendMessage(plugin.getPrefix() + "You have set " + r.getName() + "'s selector to your location.");
                                    return true;
                                }
                            } catch (Exception e) {
                            }
                        }

                        sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Role not found!");
                        return true;
                    }
                }

                else {

                    sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Unknown subcommand! Try /vd arena ?");
                    return true;
                }

            }

            else if (args[0].equalsIgnoreCase("role")) {

                if (args.length == 1 || args[1].equalsIgnoreCase("help") || args[1].equalsIgnoreCase("?")) {
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
                }

                else {

                    if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getPrefix() + "You need to be ingame for this command!");
                        return true;
                    }

                    if (plugin.getGame().getGameState() == GameState.LOBBY || plugin.getGame().getGameState() == GameState.STARTING) {

                        GamePlayer gp = plugin.getGame().getGamePlayer(((Player) sender));

                        if (gp == null) {
                            sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "An error has occured!");
                            return true;
                        }

                        for (Class<? extends Role> r : Role.roleClasses.keySet()) {

                            try {

                                Role role = r.newInstance();

                                if (!args[1].equalsIgnoreCase(role.getName()))
                                    continue;

                                if (gp.getRole() == null || !gp.getRole().getName().equals(role.getName())) {
                                    gp.setRole(role);
                                    plugin.getScoreboardManager().updateScoreboard(ScoreboardType.STARTING, ScoreboardType.VOTING);
                                    gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.LEVEL_UP, 1F, 1F);
                                    gp.getPlayer().sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You have selected the " + ChatColor.BLUE + role.getName() + ChatColor.YELLOW + " role!");
                                    return true;
                                }

                                else {
                                    gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You already have the role " + ChatColor.BLUE + role.getName() + ChatColor.YELLOW + " selected!");
                                    gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.CREEPER_HISS, 1F, 1F);
                                    return true;
                                }

                            } catch (Exception e) {
                            }

                        }
                    }

                    else {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "You cannot change your role during the game!");
                        return true;
                    }

                    sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Role not found! Try /vd role ?");
                    return true;
                }

            }

            else if (args[0].equalsIgnoreCase("gold")) {

                if (args.length == 1) {

                    if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getPrefix() + "You need to be ingame for this command!");
                        return true;
                    }

                    sender.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You have " + ChatColor.BLUE + plugin.getGame().getGamePlayer((Player) sender).getGold() + ChatColor.YELLOW + " gold!");
                }

                else if (args[1].equalsIgnoreCase("help") || args[1].equalsIgnoreCase("?")) {
                    sender.sendMessage(plugin.getPrefix() + "/vd gold pay <player> <amount>");
                    sender.sendMessage(plugin.getPrefix() + "/vd gold give <player> <amount>");
                    sender.sendMessage(plugin.getPrefix() + "/vd gold take <player> <amount>");
                    sender.sendMessage(plugin.getPrefix() + "/vd gold set <player> <amount>");
                    return true;
                }

                else if (args[1].equalsIgnoreCase("pay")) {

                    if (args.length != 4) {
                        sender.sendMessage(plugin.getPrefix() + "/vd gold pay <player> <amount>");
                        return true;
                    }

                    if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getPrefix() + "You need to be ingame for this command!");
                        return true;
                    }

                    if (plugin.getGame().getGameState() != GameState.INGAME) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "The game is not currently in progress!");
                        return true;
                    }

                    Player player = Bukkit.getPlayer(args[2]);

                    if (player == null) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.BLUE + args[2] + ChatColor.YELLOW + " doesn't exist or is not oneline!");
                        return true;
                    }

                    if (!this.isInt(args[3])) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.BLUE + args[3] + ChatColor.YELLOW + " is not an amount of gold!");
                        return true;
                    }

                    GamePlayer gp = plugin.getGame().getGamePlayer((Player) sender);
                    int amount = Integer.parseInt(args[3]);

                    if (gp.isEqualToPlayer(player)) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You cannot send gold to yourself!");
                        return true;
                    }

                    if (gp.getGold() - amount < 0) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You do not have " + ChatColor.BLUE + amount + ChatColor.YELLOW + " gold!");
                        return true;
                    }

                    gp.decrementGold(amount);
                    plugin.getGame().getGamePlayer(player).incrementGold(amount);

                    sender.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You payed " + ChatColor.BLUE + amount + ChatColor.YELLOW + " gold to " + ChatColor.BLUE + player.getName() + ChatColor.YELLOW + "!");
                    player.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You recieved " + ChatColor.BLUE + amount + ChatColor.YELLOW + "gold from " + ChatColor.BLUE + gp.getPlayerName() + ChatColor.YELLOW + "!");

                    return true;
                }

                else if (args[1].equalsIgnoreCase("give")) {

                    if (args.length != 4) {
                        sender.sendMessage(plugin.getPrefix() + "/vd gold give <player> <amount>");
                        return true;
                    }

                    if (sender instanceof Player && !sender.hasPermission("villagedefense.gold.admin")) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "You no not have permission to do that!");
                        return true;
                    }

                    if (plugin.getGame().getGameState() != GameState.INGAME) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "The game is not currently in progress!");
                        return true;
                    }

                    Player player = Bukkit.getPlayer(args[2]);

                    if (player == null) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.BLUE + args[2] + ChatColor.YELLOW + " doesn't exist or is not oneline!");
                        return true;
                    }

                    if (!this.isInt(args[3])) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.BLUE + args[3] + ChatColor.YELLOW + " is not an amount of gold!");
                        return true;
                    }

                    int amount = Integer.parseInt(args[3]);

                    plugin.getGame().getGamePlayer(player).incrementGold(amount);
                    player.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You have recieved " + ChatColor.BLUE + amount + ChatColor.YELLOW + " gold!");

                    if (sender instanceof Player && !((Player) sender).getName().equals(player.getName()))
                        sender.sendMessage(plugin.getPrefix() + ChatColor.BLUE + player.getName() + ChatColor.YELLOW + " recieved " + ChatColor.BLUE + amount + ChatColor.YELLOW + " gold!");

                    return true;
                }

                else if (args[1].equalsIgnoreCase("take")) {

                    if (args.length != 4) {
                        sender.sendMessage(plugin.getPrefix() + "/vd gold take <player> <amount>");
                        return true;
                    }

                    if (sender instanceof Player && !sender.hasPermission("villagedefense.gold.admin")) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "You no not have permission to do that!");
                        return true;
                    }

                    if (plugin.getGame().getGameState() != GameState.INGAME) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "The game is not currently in progress!");
                        return true;
                    }

                    Player player = Bukkit.getPlayer(args[2]);

                    if (player == null) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.BLUE + args[2] + ChatColor.YELLOW + " doesn't exist or is not oneline!");
                        return true;
                    }

                    if (!this.isInt(args[3])) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.BLUE + args[3] + ChatColor.YELLOW + " is not an amount of gold!");
                        return true;
                    }

                    int amount = Integer.parseInt(args[3]);

                    if (plugin.getGame().getGamePlayer(player).getGold() - amount < 0) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "Too much gold taken! The most you can take is " + ChatColor.BLUE + plugin.getGame().getGamePlayer(player).getGold() + ChatColor.YELLOW + "!");
                        return true;
                    }

                    plugin.getGame().getGamePlayer(player).decrementGold(amount);
                    player.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You have lost " + ChatColor.BLUE + amount + ChatColor.YELLOW + " gold!");

                    if (sender instanceof Player && !((Player) sender).getName().equals(player.getName()))
                        sender.sendMessage(plugin.getPrefix() + ChatColor.BLUE + player.getName() + ChatColor.YELLOW + " lost " + ChatColor.BLUE + amount + ChatColor.YELLOW + " gold!");

                    return true;
                }

                else if (args[1].equalsIgnoreCase("set")) {

                    if (args.length != 4) {
                        sender.sendMessage(plugin.getPrefix() + "/vd gold set <player> <amount>");
                        return true;
                    }

                    if (sender instanceof Player && !sender.hasPermission("villagedefense.gold.admin")) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "You no not have permission to do that!");
                        return true;
                    }

                    if (plugin.getGame().getGameState() != GameState.INGAME) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "The game is not currently in progress!");
                        return true;
                    }

                    Player player = Bukkit.getPlayer(args[2]);

                    if (player == null) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.BLUE + args[2] + ChatColor.YELLOW + " doesn't exist or is not oneline!");
                        return true;
                    }

                    if (!this.isInt(args[3])) {
                        sender.sendMessage(plugin.getPrefix() + ChatColor.BLUE + args[3] + ChatColor.YELLOW + " is not an amount of gold!");
                        return true;
                    }

                    int amount = Integer.parseInt(args[3]);

                    plugin.getGame().getGamePlayer(player).setGold(amount);
                    player.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "Your gold has been set " + ChatColor.BLUE + amount + ChatColor.YELLOW + " gold!");

                    if (sender instanceof Player && !((Player) sender).getName().equals(player.getName()))
                        sender.sendMessage(plugin.getPrefix() + ChatColor.BLUE + player.getName() + "'s" + ChatColor.YELLOW + " gold has been set to " + ChatColor.BLUE + amount + ChatColor.YELLOW + "!");

                    return true;
                }

                else {

                    sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Unknown subcommand! Try /vd gold ?");
                    return true;
                }

            }

            else if (args[0].equalsIgnoreCase("vote")) {

                if (args.length != 2) {
                    sender.sendMessage(plugin.getPrefix() + "/vd vote <Arena>");
                    return true;
                }

                if (!(sender instanceof Player)) {
                    sender.sendMessage(plugin.getPrefix() + "You need to be ingame for this command!");
                    return true;
                }

                if (plugin.getGame().getGameState() != GameState.LOBBY) {
                    sender.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You cannot vote during a game!");
                    return true;
                }

                String arenaName = args[1];
                boolean successful = false;

                for (Arena a : plugin.getArenas()) {
                    if (a.getName().equalsIgnoreCase(arenaName)) {
                        plugin.getVoteManager().addVote((Player) sender, a);
                        successful = true;
                        arenaName = a.getName();
                        plugin.getScoreboardManager().updateScoreboard(ScoreboardType.VOTING);
                    }
                }

                if (successful)
                    sender.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You have added a vote to " + ChatColor.BLUE + arenaName + ChatColor.YELLOW + "!");
                else
                    sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Couldn't find the arena " + arenaName + "!");
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
