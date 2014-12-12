package me.chasertw123.villagedefense.game.role;

import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class Tank extends Role {

    public Tank() {
        super("Tank", 50, 85, 280, null, null, null, null, new ItemStack(Material.DIAMOND_CHESTPLATE), "This role acts as the main damage taker of the team. You have a medium amount of attack damage, tons of mana," + " and are highly resistant to damage, but you are a little slow.");
    }

    @Override
    public ItemStack getBanner() {
        FancyItemStack itemStack = new FancyItemStack(Material.BANNER, ChatColor.GREEN + "" + ChatColor.BOLD + "Tank");
        BannerMeta bm = (BannerMeta) itemStack.getItemMeta();

        bm.setBaseColor(DyeColor.LIME);
        bm.addPattern(new Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER));
        bm.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLES_TOP));
        bm.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_SMALL));

        itemStack.setItemMeta(bm);

        return itemStack;
    }

}
