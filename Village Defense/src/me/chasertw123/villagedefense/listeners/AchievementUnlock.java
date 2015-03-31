package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.events.AchievementUnlockEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class AchievementUnlock implements Listener {

    private Main plugin;

    public AchievementUnlock(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAchievementUnlock(AchievementUnlockEvent event) {

        if (event.isCancelled())
            return;

        if (plugin.getStatsManager().getAchievement(event.getAchievement(), event.getPlayer()))
            return;

        plugin.getStatsManager().setAchievement(event.getAchievement(), event.getPlayer(), true);
        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 1F, 1F);

        TextComponent b = new TextComponent("["), m = new TextComponent("Achievement Unlocked");

        b.setColor(ChatColor.YELLOW);
        m.setColor(ChatColor.BLUE);

        b.addExtra(m);
        b.addExtra("]");

        ComponentBuilder cb = new ComponentBuilder("You unlokced the ").color(ChatColor.YELLOW).append(event.getAchievement().getFriendlyname()).color(ChatColor.BLUE).append(" achievement!").color(ChatColor.YELLOW);

        b.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, cb.create()));

        event.getPlayer().spigot().sendMessage(b);
    }
}
