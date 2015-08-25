package me.chasertw123.villagedefense.game.abilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.AbilityCreationException;
import me.chasertw123.villagedefense.game.GamePlayer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ArrowStorm extends Ability {

    public ArrayList<UUID> arrowStormArrows = new ArrayList<UUID>();
    private PlayOutArrowStorm POAS = null;

    public ArrowStorm() throws AbilityCreationException {
        super("Arrow Storm", 3, new int[] { 50, 50, 50 }, new int[] { 22, 18, 14 }, AbilityType.PRIMARY, "Right click this to have a storm of arrows rain down around you! The damage and size of this ability scales on it's level!");
    }

    @Override
    public void play(Main plugin, Object... args) {

        if (!(args[0] instanceof Player))
            return;

        Player player = (Player) args[0];

        if (player == null)
            return;

        GamePlayer gp = plugin.getGame().getGamePlayer(player);

        if (POAS != null && POAS.getChargesLeft() > 0) {
            gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You currently have an arrow storm in progress!");
            return;
        }

        else if (POAS != null && POAS.getChargesLeft() <= 0) {

            POAS.cancel();
            POAS = null;

            player.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "Your arrow storm has ended!");
            this.resetCooldown();

            return;
        }

        else {

            POAS = new PlayOutArrowStorm(getTier(), gp, plugin);

            gp.decrementMana(getManaCost());
            gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "Your storm of arrows has started!");

            return;
        }
    }

    class PlayOutArrowStorm extends BukkitRunnable {

        private Main plugin;
        private GamePlayer gamePlayer;
        private int abilityTier, chargesLeft = 3, time = 0;

        public PlayOutArrowStorm(int abilityTier, GamePlayer gamePlayer, Main plugin) {
            this.abilityTier = abilityTier;
            this.gamePlayer = gamePlayer;
            this.plugin = plugin;

            this.runTaskTimer(plugin, 1L, 1L);
        }

        @Override
        public void run() {

            if (chargesLeft <= 0)
                gamePlayer.getRole().getPrimaryAbility().play(plugin, gamePlayer.getPlayer());

            if (time < 20)
                time++;

            Location location = gamePlayer.getPlayer().getLocation().add(0D, 20D, 0D);
            int centerX = location.getBlockX(), centerZ = location.getBlockZ(), radius = 4 + abilityTier;
            List<Block> blocks = new ArrayList<Block>();

            for (int x = -centerX + radius; x < centerX; x++)
                for (int z = -centerZ + radius; x < centerZ; z++)
                    blocks.add(location.getWorld().getBlockAt(x, location.getBlockY(), z));

            for (Block b : blocks) {
                Arrow a = b.getWorld().spawnArrow(b.getLocation(), new Vector(), 0F, 0F);
                a.setShooter(gamePlayer.getPlayer());

                arrowStormArrows.add(a.getUniqueId());
            }

            time = 0;
            chargesLeft--;

        }

        public int getChargesLeft() {
            return chargesLeft;
        }

    }

}
