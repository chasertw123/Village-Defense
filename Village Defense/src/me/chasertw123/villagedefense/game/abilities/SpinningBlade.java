package me.chasertw123.villagedefense.game.abilities;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.AbilityCreationException;
import me.chasertw123.villagedefense.game.GamePlayer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

// Still a work in-progress
public class SpinningBlade extends Ability {

    public SpinningBlade() throws AbilityCreationException {
        super("Spinning Blade", 3, new int[] { 35, 35, 35 }, new int[] { 6, 5, 4 }, AbilityType.SECONDARY, new ItemStack(Material.INK_SACK, 1, (short) 10), "This ability spins you around an does scaling damage based on it's level! Use it when in large groups of monsters!");
    }

    @Override
    public void play(Main plugin, Object... args) {

        if (!(args[0] instanceof Player))
            return;

        GamePlayer gp = plugin.getGame().getGamePlayer((Player) args[0]);

        new SpinBlade(gp, this, plugin);

        gp.decrementMana(getManaCost());
        this.resetCooldown();

    }

    class SpinBlade extends BukkitRunnable {

        GamePlayer gp;
        SpinningBlade sb;

        public SpinBlade(GamePlayer gp, SpinningBlade sb, Main plugin) {
            this.gp = gp;
            this.sb = sb;

            this.runTaskLater(plugin, 2L);
        }

        @Override
        public void run() {

        }

    }

}
