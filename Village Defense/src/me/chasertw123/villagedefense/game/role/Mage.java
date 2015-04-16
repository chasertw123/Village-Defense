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

public class Mage extends Role {

    public Mage() throws RoleCreationException {
        super("Mage", 280, 20, 110, 50, null, null, null, null, setToolSets(), new ItemStack(Material.BLAZE_ROD), "This role acts as a high damage dealer taking dowm large portions of enimies. You have high attack, high mana," + " a small speed boost but you cannot take many hits");
    }

    @Override
    public ItemStack getBanner() {
        FancyItemStack itemStack = new FancyItemStack(Material.BANNER, ChatColor.GREEN + "" + ChatColor.BOLD + "Mage");
        BannerMeta bm = (BannerMeta) itemStack.getItemMeta();

        bm.setBaseColor(DyeColor.PURPLE);
        bm.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP));
        bm.addPattern(new Pattern(DyeColor.PURPLE, PatternType.RHOMBUS_MIDDLE));
        bm.addPattern(new Pattern(DyeColor.BLACK, PatternType.CIRCLE_MIDDLE));
        bm.addPattern(new Pattern(DyeColor.WHITE, PatternType.CURLY_BORDER));
        bm.addPattern(new Pattern(DyeColor.PURPLE, PatternType.TRIANGLES_BOTTOM));
        bm.addPattern(new Pattern(DyeColor.BLACK, PatternType.GRADIENT));

        itemStack.setItemMeta(bm);

        return itemStack;
    }

    private static ArrayList<ToolSet> setToolSets() {

        ArrayList<ToolSet> toolSets = new ArrayList<ToolSet>();

        toolSets.add(new ToolSet(ToolType.CHESTPLATE, new Tool(new FancyItemStack(Material.LEATHER_CHESTPLATE, ChatColor.GREEN + "" + ChatColor.BOLD + "Mage Chestplate" + Utils.fancyTierString(1)), 0, 1)));
        toolSets.add(new ToolSet(ToolType.LEGGINGS, new Tool(new FancyItemStack(Material.LEATHER_LEGGINGS, ChatColor.GREEN + "" + ChatColor.BOLD + "Mage Leggings" + Utils.fancyTierString(1)), 0, 1)));
        toolSets.add(new ToolSet(ToolType.BOOTS, new Tool(new FancyItemStack(Material.LEATHER_BOOTS, ChatColor.GREEN + "" + ChatColor.BOLD + "Mage Boots" + Utils.fancyTierString(1)), 0, 1)));
        toolSets.add(new ToolSet(ToolType.WEAPON, new Tool(new FancyItemStack(Material.WOOD_SWORD, ChatColor.GREEN + "" + ChatColor.BOLD + "Mage Weapon" + Utils.fancyTierString(1)), 0, 1)));

        return toolSets;
    }
}
