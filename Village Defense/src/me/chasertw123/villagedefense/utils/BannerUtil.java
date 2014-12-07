package me.chasertw123.villagedefense.utils;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.meta.BannerMeta;

public class BannerUtil {

	public static FancyItemStack getHealerBanner() {
		
		FancyItemStack itemStack = new FancyItemStack(Material.BANNER, ChatColor.GREEN + "" + ChatColor.BOLD + "Healer");
		BannerMeta bm = (BannerMeta) itemStack.getItemMeta();
		
		bm.setBaseColor(DyeColor.WHITE);
		bm.addPattern(new Pattern(DyeColor.RED, PatternType.RHOMBUS_MIDDLE));
		bm.addPattern(new Pattern(DyeColor.WHITE, PatternType.HALF_HORIZONTAL));
		bm.addPattern(new Pattern(DyeColor.RED, PatternType.CIRCLE_MIDDLE));
		bm.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_TOP));
		
		itemStack.setItemMeta(bm);
		
		return itemStack;
	}
	
	public static FancyItemStack getAssassinBanner() {
		
		FancyItemStack itemStack = new FancyItemStack(Material.BANNER, ChatColor.GREEN + "" + ChatColor.BOLD + "Assassin");
		BannerMeta bm = (BannerMeta) itemStack.getItemMeta();
		
		bm.setBaseColor(DyeColor.WHITE);
		bm.addPattern(new Pattern(DyeColor.RED, PatternType.HALF_HORIZONTAL));
		bm.addPattern(new Pattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE));
		bm.addPattern(new Pattern(DyeColor.RED, PatternType.CURLY_BORDER));
		bm.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM));
		bm.addPattern(new Pattern(DyeColor.RED, PatternType.CIRCLE_MIDDLE));
		bm.addPattern(new Pattern(DyeColor.RED, PatternType.TRIANGLE_BOTTOM));
		
		itemStack.setItemMeta(bm);
		
		return itemStack;
	}
	
	public static FancyItemStack getTankBanner() {
		
		FancyItemStack itemStack = new FancyItemStack(Material.BANNER, ChatColor.GREEN + "" + ChatColor.BOLD + "Tank");
		BannerMeta bm = (BannerMeta) itemStack.getItemMeta();
		
		bm.setBaseColor(DyeColor.LIME);
		bm.addPattern(new Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER));
		bm.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLES_TOP));
		bm.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_SMALL));
		
		itemStack.setItemMeta(bm);
		
		return itemStack;
	}

	public static FancyItemStack getArcherBanner() {
	
		FancyItemStack itemStack = new FancyItemStack(Material.BANNER, ChatColor.GREEN + "" + ChatColor.BOLD + "Archer");
		BannerMeta bm = (BannerMeta) itemStack.getItemMeta();
		
		bm.setBaseColor(DyeColor.WHITE);
		bm.addPattern(new Pattern(DyeColor.BROWN, PatternType.BORDER));
		bm.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_LEFT));
		bm.addPattern(new Pattern(DyeColor.SILVER, PatternType.STRIPE_MIDDLE));
		
		itemStack.setItemMeta(bm);
		
		return itemStack;
	}

	public static FancyItemStack getMageBanner() {
	
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
