package me.chasertw123.villagedefense.game.role;

import java.util.ArrayList;

import me.chasertw123.villagedefense.exceptions.RoleCreationException;
import me.chasertw123.villagedefense.game.tools.Tool;
import me.chasertw123.villagedefense.game.tools.ToolSet;
import me.chasertw123.villagedefense.game.tools.ToolType;
import me.chasertw123.villagedefense.utils.FancyItemStack;
import me.chasertw123.villagedefense.utils.Utils;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class Archer extends Role {

    public Archer() throws RoleCreationException {
        super("Archer", 220, 10, 110, 100, null, null, null, null, setToolSets(), new ItemStack(Material.BOW), "This role acts as a marksman, dealing lots of damage from long distances. You have medium attack damage," + " a medium amount of mana, and a small speed buff.");
    }

    @Override
    public ItemStack getBanner() {
        FancyItemStack itemStack = new FancyItemStack(Material.BANNER, ChatColor.GREEN + "" + ChatColor.BOLD + "Archer");
        BannerMeta bm = (BannerMeta) itemStack.getItemMeta();

        bm.setBaseColor(DyeColor.WHITE);
        bm.addPattern(new Pattern(DyeColor.BROWN, PatternType.BORDER));
        bm.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_LEFT));
        bm.addPattern(new Pattern(DyeColor.SILVER, PatternType.STRIPE_MIDDLE));

        itemStack.setItemMeta(bm);

        return itemStack;
    }

    private static ArrayList<ToolSet> setToolSets() {

        ArrayList<ToolSet> toolSets = new ArrayList<ToolSet>();

        toolSets.add(new ToolSet(ToolType.CHESTPLATE, new Tool(new FancyItemStack(Material.LEATHER_CHESTPLATE, ChatColor.GREEN + "" + ChatColor.BOLD + "Archer Chestplate" + Utils.fancyTierString(1)), 0, 1)));
        toolSets.add(new ToolSet(ToolType.LEGGINGS, new Tool(new FancyItemStack(Material.LEATHER_LEGGINGS, ChatColor.GREEN + "" + ChatColor.BOLD + "Archer Leggings" + Utils.fancyTierString(1)), 0, 1)));
        toolSets.add(new ToolSet(ToolType.BOOTS, new Tool(new FancyItemStack(Material.LEATHER_BOOTS, ChatColor.GREEN + "" + ChatColor.BOLD + "Archer Boots" + Utils.fancyTierString(1)), 0, 1)));
        toolSets.add(new ToolSet(ToolType.WEAPON, new Tool(new FancyItemStack(Material.BOW, ChatColor.GREEN + "" + ChatColor.BOLD + "Archer Weapon" + Utils.fancyTierString(1)), 0, 1)));

        return toolSets;
    }

}
