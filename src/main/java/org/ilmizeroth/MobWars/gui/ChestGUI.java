package org.ilmizeroth.MobWars.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ilmizeroth.MobWars.mob.MobStats;

import java.util.ArrayList;

public interface ChestGUI {
    Inventory createInventory();
    Inventory getInventory();

    void onInventoryClose(InventoryCloseEvent event);
    void onInventoryDrag(InventoryDragEvent event);
    void onInventoryClick(InventoryClickEvent event);
    void handleClick(Player player, int slot);

    void openInventory(Player p);
    void updateInventory();
    void closeInventory(Player p);

    void initializeItems(Inventory inv);

    void updateItem(Class blockMob, int cooldown, int count);

    default ItemStack createGuiItem(final Material material, final String name, final int count, final boolean isGlint, final String... lore){
        final ItemStack item = new ItemStack(material, count);
        final ItemMeta meta = item.getItemMeta();
        meta.setEnchantmentGlintOverride(isGlint);
        meta.setDisplayName(name);
        ArrayList<String> resultLore = new ArrayList<>();
        for(String s: lore){
            for(String k: s.split("\n")){
                resultLore.add(k);
            }
        }
        meta.setLore(resultLore);

        item.setItemMeta(meta);

        return item;
    }
    default String createLore(double maxHealth,
                               double moveSpeed,
                               double baseDamage,
                               double attackDamage,
                               double attackSpeed,
                               double cost,
                               double paydayIncrease,
                               double cooldown) {
        return String.format(
                        "§cЗдоровье: §f%.0f❤\n" +
                        "§aСкорость: §f%.2f блоков/сек\n" +
                        "§6Урон по базе: §f%f⚔\n" +
                        "§4Урон по игрокам: §f%.1f❤/удар\n" +
                        "§bСкорость атаки: §f%.1f ударов/сек\n\n" +
                        "§9Стоимость: §f%.0f⛃\n" +
                        "§2Доход: §f+%.0f⛃/сек\n" +
                        "§8Кулдаун: §f%.0f сек",
                maxHealth,
                moveSpeed,
                baseDamage,
                attackDamage,
                attackSpeed,
                cost,
                paydayIncrease,
                cooldown
        );
    }
    default String createLore(MobStats mobStats){
        if(mobStats.attackSpeed() == 0){
            return String.format(
                            "§cЗдоровье: §f%.0f❤\n" +
                            "§aСкорость: §f%.2f блоков/сек\n" +
                            "§6Урон по базе: §f%d⚔\n\n" +
                            "§9Стоимость: §f%.0f⛃\n" +
                            "§2Доход: §f+%.2f⛃\n" +
                            "§8Кулдаун: §f%.2f сек",
                    mobStats.maxHealth(),
                    mobStats.moveSpeed(),
                    mobStats.baseDamage(),
                    mobStats.cost(),
                    mobStats.paydayIncrease(),
                    mobStats.cooldown()
            );
        } else{
            return String.format(
                            "§cЗдоровье: §f%.0f❤\n" +
                            "§aСкорость: §f%.2f блоков/сек\n" +
                            "§6Урон по базе: §f%d⚔\n" +
                            "§4Урон по игрокам: §f%.1f❤/удар\n" +
                            "§bСкорость атаки: §f%.1f ударов/сек\n\n" +
                            "§9Стоимость: §f%.0f⛃\n" +
                            "§2Доход: §f+%.2f⛃\n" +
                            "§8Кулдаун: §f%.2f сек",
                    mobStats.maxHealth(),
                    mobStats.moveSpeed(),
                    mobStats.baseDamage(),
                    mobStats.attackDamage(),
                    mobStats.attackSpeed(),
                    mobStats.cost(),
                    mobStats.paydayIncrease(),
                    mobStats.cooldown()
            );
        }
    }
    default String createLore(MobStats mobStats, int currentCooldown){
        if(mobStats.attackSpeed() == 0){
            return String.format(
                            "§cЗдоровье: §f%.0f❤\n" +
                            "§aСкорость: §f%.2f блоков/сек\n" +
                            "§6Урон по базе: §f%d⚔\n\n" +
                            "§9Стоимость: §f%.0f⛃\n" +
                            "§2Доход: §f+%.2f⛃\n\n" +
                            "§fКулдаун: §e%d сек",
                    mobStats.maxHealth(),
                    mobStats.moveSpeed(),
                    mobStats.baseDamage(),
                    mobStats.cost(),
                    mobStats.paydayIncrease(),
                    currentCooldown
            );
        } else{
            return String.format(
                            "§cЗдоровье: §f%.0f❤\n" +
                            "§aСкорость: §f%.2f блоков/сек\n" +
                            "§6Урон по базе: §f%d⚔\n" +
                            "§4Урон по игрокам: §f%.1f❤/удар\n" +
                            "§bСкорость атаки: §f%.1f ударов/сек\n\n" +
                            "§9Стоимость: §f%.0f⛃\n" +
                            "§2Доход: §f+%.2f⛃\n\n" +
                            "§fКулдаун: §e%d сек",
                    mobStats.maxHealth(),
                    mobStats.moveSpeed(),
                    mobStats.baseDamage(),
                    mobStats.attackDamage(),
                    mobStats.attackSpeed(),
                    mobStats.cost(),
                    mobStats.paydayIncrease(),
                    currentCooldown
            );
        }
    }
    default String createLore(double maxHealth,
                              double moveSpeed,
                              double baseDamage,
                              double cost,
                              double paydayIncrease,
                              double cooldown) {
        return String.format(
                        "§cЗдоровье: §f%.0f❤\n" +
                        "§aСкорость: §f%.2f блоков/сек\n" +
                        "§6Урон по базе: §f%f⚔\n\n" +
                        "§9Стоимость: §f%.0f⛃\n" +
                        "§2Доход: §f+%.0f⛃/сек\n" +
                        "§8Кулдаун: §f%.0f сек",
                maxHealth,
                moveSpeed,
                baseDamage,
                cost,
                paydayIncrease,
                cooldown
        );
    }
}
