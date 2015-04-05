package me.chasertw123.villagedefense.game.abilities;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.AbilityCreationException;
import me.chasertw123.villagedefense.game.GamePlayer;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Dash extends Ability {

    private ChargeDash cd = null;

    public Dash() throws AbilityCreationException {
        super("Dash", 3, new int[] { 35, 35, 35 }, new int[] { 4, 2, 0 }, AbilityType.PRIMARY, new ItemStack(Material.INK_SACK, 1, (short) 10), "Right click to begin charging up your dash right click again to dash or when dash is fully charged to will atoumaticlly dash.");
    }

    @Override
    public void play(Main plugin, Object... args) {

        if (!(args[0] instanceof Player))
            return;

        Player player = (Player) args[0];

        if (cd != null) {

            GamePlayer gp = plugin.getGame().getGamePlayer(player);
            int charge = cd.getCharge();

            cd.cancel();
            cd = null;

            if (charge > 10)
                charge = 10;

            player.setVelocity(player.getLocation().getDirection().multiply(charge / 2).setY(0.0D));
            player.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You dashed forward!");
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 1F, 1F);

            new PlayOutDashParticles(gp, charge, plugin);

            this.resetCooldown();
            gp.decrementMana(getManaCost());
        }

        else
            cd = new ChargeDash(this.getTier(), plugin.getGame().getGamePlayer(player), plugin);
    }

    class PlayOutDashParticles extends BukkitRunnable {

        private GamePlayer gp;
        private int time;

        public PlayOutDashParticles(GamePlayer gp, int charge, Main plugin) {
            this.gp = gp;
            this.time = ((charge / 10) * 20) - 15;

            this.runTaskTimer(plugin, 1L, 1L);
        }

        @Override
        public void run() {

            if (time <= 0)
                this.cancel();

            gp.getPlayer().getWorld().spigot().playEffect(gp.getPlayer().getLocation().subtract(0.0D, 0.05D, 0.0D), Effect.LARGE_SMOKE, 0, 0, 0, 0, 0, 0, 2, 32);
            gp.getPlayer().getWorld().spigot().playEffect(gp.getPlayer().getLocation().subtract(0.0D, 0.05D, 0.0D), Effect.LAVA_POP, 0, 0, 0, 0, 0, 0, 5, 32);

            time--;
        }

    }

    class ChargeDash extends BukkitRunnable {

        private Main plugin;
        private GamePlayer gamePlayer;
        private int abilityTier, charge = 0, maxCharge;

        public ChargeDash(int abilityTier, GamePlayer gamePlayer, Main plugin) {
            this.plugin = plugin;
            this.gamePlayer = gamePlayer;
            this.abilityTier = abilityTier;
            this.maxCharge = 30 + (abilityTier * 30);

            this.runTaskTimer(plugin, 1L, 1L);
        }

        @Override
        public void run() {

            if (charge >= maxCharge)
                gamePlayer.getRole().getPrimaryAbility().play(plugin, gamePlayer.getPlayer());

            if (charge % 4 == 0) {

                float pitch = (float) charge / maxCharge;

                gamePlayer.getPlayer().playSound(gamePlayer.getPlayer().getLocation(), Sound.ORB_PICKUP, pitch, 1F);
            }

            charge += (abilityTier * 2);

        }

        public int getCharge() {
            return charge;
        }

    }
}
