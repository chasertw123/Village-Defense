package me.chasertw123.villagedefense.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FancyItemStack extends ItemStack {

	public FancyItemStack(Material material, int amount, short data, String display) {
		super(material, amount, data);
		
		ItemMeta im = super.getItemMeta();
		
		im.setDisplayName(display);
		
		super.setItemMeta(im);
	}
	
	public FancyItemStack(Material material, int amount, short data, String display, String... lore) {
		super(material, amount, data);
		
		ItemMeta im = super.getItemMeta();
		
		im.setDisplayName(display);
		im.setLore(Arrays.asList(lore));
		
		super.setItemMeta(im);
	}
	
	public FancyItemStack(Material material, int amount, String display) {
		super(material, amount);
		
		ItemMeta im = super.getItemMeta();
		
		im.setDisplayName(display);
		
		super.setItemMeta(im);
	}
	
	public FancyItemStack(Material material, int amount, String display, String... lore) {
		super(material, amount);
		
		ItemMeta im = super.getItemMeta();
		
		im.setDisplayName(display);
		im.setLore(Arrays.asList(lore));
		
		super.setItemMeta(im);
	}
	
	public FancyItemStack(Material material, String display) {
		super(material);
		
		ItemMeta im = super.getItemMeta();
		
		im.setDisplayName(display);
		
		super.setItemMeta(im);
	}
	
	public FancyItemStack(Material material, String display, String... lore) {
		super(material);
		
		ItemMeta im = super.getItemMeta();
		
		im.setDisplayName(display);
		im.setLore(Arrays.asList(lore));
		
		super.setItemMeta(im);
	}
	
	public FancyItemStack(ItemStack itemStack) {
		super(itemStack);
	}
	
	public FancyItemStack setDisplayName(String display) {
		
		ItemMeta im = super.getItemMeta();
		im.setDisplayName(display);
		
		super.setItemMeta(im);
		
		return this;
	}
	
	public FancyItemStack setLore(String... lore) {
		
		ItemMeta im = super.getItemMeta();
		im.setLore(Arrays.asList(lore));		
		
		super.setItemMeta(im);
		
		return this;
	}
	
	public FancyItemStack addLore(String... lore) {
		
		ItemMeta im = super.getItemMeta();
		
		if (im.hasLore()) {
			
			List<String> newLore = im.getLore();
			newLore.addAll(Arrays.asList(lore));
			
			im.setLore(newLore);
		
			super.setItemMeta(im);
		} 
		
		else setLore(lore);
		
		return this;
	}
	
	public FancyItemStack addFancyLore(String lore, String color) {
		StringBuilder sb = new StringBuilder(lore);

		int i = 0;
		while (i + 30 < sb.length() && (i = sb.lastIndexOf(" ", i + 30)) != -1) {
		    sb.replace(i, i + 1, "\n" + color);
		}
		
		String[] newLore = sb.toString().split("\n");
		return setLore(newLore);
	}
}
