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

public class Tank extends Role {

    public Tank() throws RoleCreationException {
        super("Tank", 50, 85, 280, null, null, null, null, allOneMaxTiers(), new ItemStack(Material.DIAMOND_CHESTPLATE), "This role acts as the main damage taker of the team. You have a medium amount of attack damage, tons of mana," + " and are highly resistant to damage, but you are a little slow.");
    }

    @Override
    public ItemStack getBanner() {
        FancyItemStack itemStack = new FancyItemStack(Material.BANNER, ChatColor.GREEN + "" + ChatColor.BOLD + "Tank");
        BannerMeta bm = (BannerMeta) itemStack.getItemMeta();

        bm.setBaseColor(DyeColor.LIME);
        bm.addPattern(new Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER));
        bm.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLES_TOP));
        bm.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_SMALL));

        itemStack.setItemMeta(bm);

        return itemStack;
    }

    @Override
    public ItemStack getItemStack(ToolType type, int tier) {

        switch (type) {

            case CHESTPLATE:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.IRON_CHESTPLATE, ChatColor.GREEN + "" + ChatColor.BOLD + "Tank Chestplate" + fancyTierString(tier));

                    default:
                        return null;
                }

            case LEGGINGS:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.IRON_LEGGINGS, ChatColor.GREEN + "" + ChatColor.BOLD + "Tank Leggings" + fancyTierString(tier));

                    default:
                        return null;
                }

            case BOOTS:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.IRON_BOOTS, ChatColor.GREEN + "" + ChatColor.BOLD + "Tank Boots" + fancyTierString(tier));

                    default:
                        return null;
                }

            case WEAPON:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.IRON_SWORD, ChatColor.GREEN + "" + ChatColor.BOLD + "Tank Weapon" + fancyTierString(tier));

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
