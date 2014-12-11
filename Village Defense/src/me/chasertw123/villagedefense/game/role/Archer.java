package me.chasertw123.villagedefense.game.role;

import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class Archer extends Role {

	public Archer() {
		super("Archer", 100, 110, 220, null, null, null, null, new ItemStack(Material.BOW), 
				"This role acts as a marksman, dealing lots of damage from long distances. You have medium attack damage,"
				+ " a medium amount of mana, and a small speed buff.");
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
	
	

}
