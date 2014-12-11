package me.chasertw123.villagedefense;

import me.chasertw123.villagedefense.exceptions.AbilityCreationException;
import me.chasertw123.villagedefense.exceptions.GameCreationException;
import me.chasertw123.villagedefense.game.Game;
import me.chasertw123.villagedefense.game.arena.Arena;
import me.chasertw123.villagedefense.game.role.Healer;
import me.chasertw123.villagedefense.game.role.Role;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	private final String prefix = ChatColor.WHITE + "[" + ChatColor.GREEN + "VD" + ChatColor.WHITE + "]" + ChatColor.RESET;
	private Game game;
	
	public void onEnable() {
		
		this.getServer().getPluginManager().registerEvents(this, this);
		
		System.out.println("You are about to witness the evolution of something awesome.");
		
		try {
			game = new Game(new Arena(null, null), 1, 1);
		} catch (GameCreationException e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Role role = null;
		
		try {
			role = new Healer();
		} catch (AbilityCreationException e) {
			e.printStackTrace();
		}
		
		if (role == null)
			return;
		
		event.getPlayer().getInventory().setItem(0, role.getItemStack());
		event.getPlayer().getInventory().setItem(1, role.getPrimaryAbility().getItemStack());
		event.getPlayer().getInventory().setItem(2, role.getSecondaryAbility().getItemStack());
		event.getPlayer().getInventory().setItem(3, role.getTertiaryAbility().getItemStack());
		event.getPlayer().getInventory().setItem(4, role.getUltraAbility().getItemStack());
		event.getPlayer().getInventory().setHelmet(role.getBanner());
	}
	
	/****************************************
	 * 									    *
	 *			Console Messages	    	* 
	 * 										*
	 ****************************************/
	
	public void sendConsoleInfo(String message) {
		this.getLogger().info(message);
	}

	public void sendConsoleWarning(String message) {
		this.getLogger().warning(message);
	}
	
	public void sendConsoleSevere(String message) {
		this.getLogger().severe(message);
	}
	
	/****************************************
	 * 									    *
	 *			Getters and Setters	    	* 
	 * 										*
	 ****************************************/
	
	public String getPrefix() {
		return prefix;
	}
	
	public Game getGame() {
		return game;
	}
}
