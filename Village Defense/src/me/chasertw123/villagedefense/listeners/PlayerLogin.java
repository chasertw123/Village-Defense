package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GameState;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLogin implements Listener {

	private Main plugin;
	
	public PlayerLogin(Main plugin) {
		this.plugin = plugin;
		
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		
		if (plugin.getArena().get = GameState.STARTINGUP) {
			
		}
		
		else if (GameState.)
	}
}
