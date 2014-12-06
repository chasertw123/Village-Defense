package me.chasertw123.villagedefense.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FancyItemSatck extends ItemStack {

	public FancyItemSatck(Material material, int amount, short data, String display) {
		super(material, amount, data);
		
		ItemMeta im = super.getItemMeta();
		
		im.setDisplayName(display);
		
		super.setItemMeta(im);
	}
	
	public FancyItemSatck(Material material, int amount, short data, String display, String... lore) {
		super(material, amount, data);
		
		ItemMeta im = super.getItemMeta();
		
		im.setDisplayName(display);
		im.setLore(Arrays.asList(lore));
		
		super.setItemMeta(im);
	}
	
	public FancyItemSatck(Material material, int amount, String display) {
		super(material, amount);
		
		ItemMeta im = super.getItemMeta();
		
		im.setDisplayName(display);
		
		super.setItemMeta(im);
	}
	
	public FancyItemSatck(Material material, int amount, String display, String... lore) {
		super(material, amount);
		
		ItemMeta im = super.getItemMeta();
		
		im.setDisplayName(display);
		im.setLore(Arrays.asList(lore));
		
		super.setItemMeta(im);
	}
	
	public FancyItemSatck(Material material, String display) {
		super(material);
		
		ItemMeta im = super.getItemMeta();
		
		im.setDisplayName(display);
		
		super.setItemMeta(im);
	}
	
	public FancyItemSatck(Material material, String display, String... lore) {
		super(material);
		
		ItemMeta im = super.getItemMeta();
		
		im.setDisplayName(display);
		im.setLore(Arrays.asList(lore));
		
		super.setItemMeta(im);
	}
	
	public FancyItemSatck setLore(String... lore) {
		
		ItemMeta im = super.getItemMeta();
		im.setLore(Arrays.asList(lore));		
		
		super.setItemMeta(im);
		
		return this;
	}
	
	public FancyItemSatck addLore(String... lore) {
		
		ItemMeta im = super.getItemMeta();
		
		List<String> newLore = im.getLore();
		newLore.addAll(Arrays.asList(lore));
		
		im.setLore(newLore);
		
		super.setItemMeta(im);
		
		return this;
	}
}
