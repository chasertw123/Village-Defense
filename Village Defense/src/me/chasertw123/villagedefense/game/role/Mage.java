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

public class Mage extends Role {

    public Mage() throws RoleCreationException {
        super("Mage", 50, 110, 280, null, null, null, null, allOneMaxTiers(), new ItemStack(Material.BLAZE_ROD), "This role acts as a high damage dealer taking dowm large portions of enimies. You have high attack, high mana," + " a small speed boost but you cannot take many hits");
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

    @Override
    public ItemStack getItemStack(UpgradeType type, int tier) {

        switch (type) {

            case CHESTPLATE:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.LEATHER_CHESTPLATE, ChatColor.GREEN + "" + ChatColor.BOLD + "Mage Chestplate" + fancyTierString(tier));

                    default:
                        return null;
                }

            case LEGGINGS:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.LEATHER_LEGGINGS, ChatColor.GREEN + "" + ChatColor.BOLD + "Mage Leggings" + fancyTierString(tier));

                    default:
                        return null;
                }

            case BOOTS:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.LEATHER_BOOTS, ChatColor.GREEN + "" + ChatColor.BOLD + "Mage Boots" + fancyTierString(tier));

                    default:
                        return null;
                }

            case WEAPON:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.WOOD_SWORD, ChatColor.GREEN + "" + ChatColor.BOLD + "Mage Weapon" + fancyTierString(tier));

                    default:
                        return null;
                }

            default:
                return null;
        }
    }

    @Override
    public int getCost(UpgradeType type, int tier) {

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
