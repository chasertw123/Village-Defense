package me.chasertw123.villagedefense.game.role;

import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta

public class Mage extends Role {

	public Mage() {
		super("Mage", 50, 110, 280, null, null, null, null, new ItemStack(Material.BLAZE_ROD),
				"This role acts as a high damage dealer taking dowm large portions of enimies. You have high attack, high mana,"
				+ " a small speed boost but you cannot take many hits");
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

}
