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

public class Assassin extends Role {

    public Assassin() throws RoleCreationException {
        super("Assassin", 90, 140, 150, null, null, null, null, allOneMaxTiers(), new ItemStack(Material.NETHER_STAR), "This role acts has a high damage dealer taking down a majority of enemies. You have low mana, high attack, a huge speed boost," + " but you can't take much damage before you die.");
    }

    @Override
    public ItemStack getBanner() {

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

    @Override
    public ItemStack getItemStack(UpgradeType type, int tier) {

        switch (type) {

            case CHESTPLATE:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.LEATHER_CHESTPLATE, ChatColor.GREEN + "" + ChatColor.BOLD + "Assassin Chestplate" + fancyTierString(tier));

                    default:
                        return null;
                }

            case LEGGINGS:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.LEATHER_LEGGINGS, ChatColor.GREEN + "" + ChatColor.BOLD + "Assassin Leggings" + fancyTierString(tier));

                    default:
                        return null;
                }

            case BOOTS:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.LEATHER_BOOTS, ChatColor.GREEN + "" + ChatColor.BOLD + "Assassin Boots" + fancyTierString(tier));

                    default:
                        return null;
                }

            case WEAPON:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.IRON_SWORD, ChatColor.GREEN + "" + ChatColor.BOLD + "Assassin Weapon" + fancyTierString(tier));

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
