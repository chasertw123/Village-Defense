package me.chasertw123.villagedefense.game.role;

import me.chasertw123.villagedefense.exceptions.RoleCreationException;
import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class Archer extends Role {

    public Archer() throws RoleCreationException {
        super("Archer", 100, 110, 220, null, null, null, null, allOneMaxTiers(), new ItemStack(Material.BOW), "This role acts as a marksman, dealing lots of damage from long distances. You have medium attack damage," + " a medium amount of mana, and a small speed buff.");
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

    @Override
    public ItemStack getItemStack(ToolType type, int tier) {

        switch (type) {

            case CHESTPLATE:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.LEATHER_CHESTPLATE, ChatColor.GREEN + "" + ChatColor.BOLD + "Archer Chestplate" + fancyTierString(tier));

                    default:
                        return null;
                }

            case LEGGINGS:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.LEATHER_LEGGINGS, ChatColor.GREEN + "" + ChatColor.BOLD + "Archer Leggings" + fancyTierString(tier));

                    default:
                        return null;
                }

            case BOOTS:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.LEATHER_BOOTS, ChatColor.GREEN + "" + ChatColor.BOLD + "Archer Boots" + fancyTierString(tier));

                    default:
                        return null;
                }

            case WEAPON:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.BOW, ChatColor.GREEN + "" + ChatColor.BOLD + "Archer Weapon" + fancyTierString(tier));

                    default:
                        return null;
                }

            default:
                return null;
        }
    }

    @Override
    public int getCost(ToolType type, int tier) {

        switch (type) {

            case CHESTPLATE:
                switch (tier) {

                    case 1:
                        return 0;

                    default:
                        return 0;
                }

            case LEGGINGS:
                switch (tier) {

                    case 1:
                        return 0;

                    default:
                        return 0;
                }

            case BOOTS:
                switch (tier) {

                    case 1:
                        return 0;

                    default:
                        return 0;
                }

            case WEAPON:
                switch (tier) {

                    case 1:
                        return 0;

                    default:
                        return 0;
                }

            default:
                return 0;
        }
    }

    private String fancyTierString(int tier) {
        return ChatColor.BLUE + "" + ChatColor.BOLD + " [" + ChatColor.GOLD + ChatColor.BOLD + "Level " + tier + ChatColor.BLUE + "" + ChatColor.BOLD + "]";
    }
}
