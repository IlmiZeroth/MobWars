package org.giperfire.mobWars.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public interface AnyGUI {
    Inventory getInventory();
    void handleClick(Player player, int slot);
    void onInventoryClose(InventoryCloseEvent event);
    void onInventoryDrag(InventoryDragEvent event);
    void onInventoryClick(InventoryClickEvent event);

}
