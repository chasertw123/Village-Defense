package me.chasertw123.villagedefense.game.role;

import me.chasertw123.villagedefense.exceptions.VillageDefenseException;
import me.chasertw123.villagedefense.game.abilities.Heal;
import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class Healer extends Role {

    public Healer() throws VillageDefenseException {
        super("Healer", 100, 110, 280, new Heal(), null, null, null, allOneMaxTiers(), new ItemStack(Material.INK_SACK, 1, (short) 10), "This role acts as support healing and buffing their allies. You have high mana but low attack damage and a slight speed boost.");
    }

    @Override
    public ItemStack getBanner() {
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

    @Override
    public ItemStack getItemStack(ToolType type, int tier) {

        switch (type) {

            case CHESTPLATE:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.LEATHER_CHESTPLATE, ChatColor.GREEN + "" + ChatColor.BOLD + "Healer Chestplate" + fancyTierString(tier));

                    default:
                        return null;
                }

            case LEGGINGS:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.LEATHER_LEGGINGS, ChatColor.GREEN + "" + ChatColor.BOLD + "Healer Leggings" + fancyTierString(tier));

                    default:
                        return null;
                }

            case BOOTS:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.LEATHER_BOOTS, ChatColor.GREEN + "" + ChatColor.BOLD + "Healer Boots" + fancyTierString(tier));

                    default:
                        return null;
                }

            case WEAPON:
                switch (tier) {

                    case 1:
                        return new FancyItemStack(Material.WOOD_SWORD, ChatColor.GREEN + "" + ChatColor.BOLD + "Healer Weapon" + fancyTierString(tier));

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
