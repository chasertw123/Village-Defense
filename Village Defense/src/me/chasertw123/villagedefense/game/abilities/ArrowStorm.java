package me.chasertw123.villagedefense.game.abilities;

import java.util.ArrayList;
import java.util.UUID;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.AbilityCreationException;
import me.chasertw123.villagedefense.game.GamePlayer;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ArrowStorm extends Ability {

    public ArrayList<UUID> arrowStormArrows = new ArrayList<UUID>();
    private boolean inProgess = false;

    public ArrowStorm() throws AbilityCreationException {
        super("Arrow Storm", 3, new int[] { 50, 45, 40 }, new int[] { 18, 15, 12 }, AbilityType.PRIMARY, "Right click this to have a storm of arrows rain down around you! The damage and size of this ability scales on it's level!");
    }

    @Override
    public void play(Main plugin, Object... args) {

        if (!(args[0] instanceof Player))
            return;

        Player player = (Player) args[0];

        if (player == null)
            return;

        GamePlayer gp = plugin.getGame().getGamePlayer(player);

        if (inProgess) {
            gp.sendMessage(plugin.getPrefix() + "You currently have an arrow storm in progress!");
            return;
        }

        else {

            // Play Ability

        }

    }

    class PlayOutArrowStorm extends BukkitRunnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub

        }

    }

}
