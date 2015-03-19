package me.chasertw123.villagedefense.game.abilities;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.AbilityCreationException;
import me.chasertw123.villagedefense.game.GamePlayer;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Heal extends Ability {

    public Heal() throws AbilityCreationException {
        super("Heal", 3, new int[] { 45, 35, 30 }, new int[] { 4, 3, 3 }, AbilityType.PRIMARY, new ItemStack(Material.INK_SACK, 1, (short) 10), "Right click a player to heal them or right click anything else to heal your self. This ability heals 1.5 hearts per level.");
    }

    @Override
    public void play(Main plugin, Object... args) {

        Player healer = (Player) args[0];

        if (healer == null)
            return;

        Player healed = (Player) args[1];

        if (healed == null)
            return;

        double health = healed.getHealth() + (getTier() * 3);

        if (health > healed.getMaxHealth())
            health = healed.getMaxHealth();

        healed.setHealth(health);
        this.runHelix(healed);

        GamePlayer gp = plugin.getGame().getGamePlayer(healer);

        gp.getRole().getPrimaryAbility().resetCooldown();
        gp.decrementMana(getManaCost());

        if (healer == healed) {
            healer.sendMessage(plugin.getPrefix() + "You healed yourself!");
            healer.playSound(healer.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
        }

        else {
            healer.sendMessage(plugin.getPrefix() + "You healed " + healed.getName() + "!");
            healed.sendMessage(plugin.getPrefix() + healer.getName() + " healed you!");
            healer.playSound(healer.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
            healed.playSound(healed.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
        }
    }

    private void runHelix(Player p) {

        Location loc = p.getLocation();
        double radius = 5;

        for (double y = 5; y >= 0; y -= 0.007) {
            radius = y / 3;
            double x = radius * Math.cos(3 * y);
            double z = radius * Math.sin(3 * y);

            double y2 = 5 - y;

            Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
            loc.getWorld().spigot().playEffect(loc2, Effect.HAPPY_VILLAGER, 0, 0, 0, 0, 0, 0, 1, 64);
        }

        for (double y = 5; y >= 0; y -= 0.007) {
            radius = y / 3;
            double x = -(radius * Math.cos(3 * y));
            double z = -(radius * Math.sin(3 * y));

            double y2 = 5 - y;

            Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
            loc.getWorld().spigot().playEffect(loc2, Effect.HAPPY_VILLAGER, 0, 0, 0, 0, 0, 0, 1, 64);
        }

    }
}
