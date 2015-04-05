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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Shield extends Ability {

    public Shield() throws AbilityCreationException {
        super("Shield", 3, new int[] { 75, 75, 75 }, new int[] { 12, 17, 23 }, AbilityType.SECONDARY, new ItemStack(Material.INK_SACK, 1, (short) 10), "Right click a player to shield them or right click anything else to shield your self. This ability shields for 2 hearts per level and lasts for 10 seconds per level.");
    }

    @Override
    public void play(Main plugin, Object... args) {

        Player shielder = (Player) args[0];

        if (shielder == null)
            return;

        Player shielded = (Player) args[1];

        if (shielded == null)
            return;

        shielded.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, (20 * 10) * getTier(), getTier()));
        this.runShield(shielded);

        GamePlayer gp = plugin.getGame().getGamePlayer(shielder);

        this.resetCooldown();
        gp.decrementMana(getManaCost());

        if (shielder == shielded) {
            shielder.sendMessage(plugin.getPrefix() + "You shielded yourself!");
            shielder.playSound(shielder.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
        }

        else {
            shielder.sendMessage(plugin.getPrefix() + "You shielded " + shielded.getName() + "!");
            shielded.sendMessage(plugin.getPrefix() + shielder.getName() + " shielded you!");
            shielder.playSound(shielder.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
            shielded.playSound(shielded.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
        }
    }

    private void runShield(Player p) {

        Location loc = p.getEyeLocation().add(0.0D, 0.2D, 0.0D);
        double increment = (2 * Math.PI) / 60;
        double radius = 0.8;

        for (int i = 0; i < 60; i++) {

            double angle = i * increment;
            double x = loc.getX() + (radius * Math.cos(angle));
            double z = loc.getZ() + (radius * Math.sin(angle));

            Location loc2 = new Location(loc.getWorld(), x, loc.getY(), z);
            loc.getWorld().spigot().playEffect(loc2, Effect.FIREWORKS_SPARK, 0, 0, 0, 0, 0, 0, 1, 32);
        }

    }
}
