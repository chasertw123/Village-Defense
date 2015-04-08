package me.chasertw123.villagedefense.game.role;

import java.util.ArrayList;

import me.chasertw123.villagedefense.exceptions.RoleCreationException;
import me.chasertw123.villagedefense.game.tools.Tool;
import me.chasertw123.villagedefense.game.tools.ToolSet;
import me.chasertw123.villagedefense.game.tools.ToolType;
import me.chasertw123.villagedefense.utils.FancyItemStack;
import me.chasertw123.villagedefense.utils.StringUtils;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class Tank extends Role {

    public Tank() throws RoleCreationException {
        super("Tank", 280, 20, 85, 140, null, null, null, null, setToolSets(), new ItemStack(Material.DIAMOND_CHESTPLATE), "This role acts as the main damage taker of the team. You have a medium amount of attack damage, tons of mana," + " and are highly resistant to damage, but you are a little slow.");
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

    private static ArrayList<ToolSet> setToolSets() {

        ArrayList<ToolSet> toolSets = new ArrayList<ToolSet>();

        toolSets.add(new ToolSet(ToolType.CHESTPLATE, new Tool(new FancyItemStack(Material.IRON_CHESTPLATE, ChatColor.GREEN + "" + ChatColor.BOLD + "Tank Chestplate" + StringUtils.fancyTierString(1)), 0, 1)));
        toolSets.add(new ToolSet(ToolType.LEGGINGS, new Tool(new FancyItemStack(Material.IRON_LEGGINGS, ChatColor.GREEN + "" + ChatColor.BOLD + "Tank Leggings" + StringUtils.fancyTierString(1)), 0, 1)));
        toolSets.add(new ToolSet(ToolType.BOOTS, new Tool(new FancyItemStack(Material.IRON_BOOTS, ChatColor.GREEN + "" + ChatColor.BOLD + "Tank Boots" + StringUtils.fancyTierString(1)), 0, 1)));
        toolSets.add(new ToolSet(ToolType.WEAPON, new Tool(new FancyItemStack(Material.IRON_SWORD, ChatColor.GREEN + "" + ChatColor.BOLD + "Tank Weapon" + StringUtils.fancyTierString(1)), 0, 1)));

        return toolSets;
    }
}
