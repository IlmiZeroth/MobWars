package org.giperfire.mobWars.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.*;

public class GUIListener implements Listener {
    private static final Map<UUID, AnyGUI> activeGUIs = new HashMap<>();

    public static void openGUI(Player player, AnyGUI gui) {
        activeGUIs.put(player.getUniqueId(), gui);
    }

    public static void closeGUI(Player player) {
        AnyGUI gui = activeGUIs.get(player.getUniqueId());
        if (gui != null) {
            activeGUIs.remove(player.getUniqueId());
        }
    }

    public static AnyGUI get(UUID player){
        if(activeGUIs.containsKey(player)) return activeGUIs.get(player);
        return null;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        AnyGUI gui = activeGUIs.get(player.getUniqueId());
        if (gui == null || !event.getInventory().equals(gui.getInventory())) return;

        gui.onInventoryClick(event);
        gui.handleClick(((Player) event.getWhoClicked()).getPlayer(), event.getSlot());
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();
        AnyGUI gui = activeGUIs.get(player.getUniqueId());
        if (activeGUIs.containsKey(event.getWhoClicked().getUniqueId())) {
            gui.onInventoryDrag(event);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        AnyGUI gui = activeGUIs.get(player.getUniqueId());
        if (activeGUIs.containsKey(player.getUniqueId())) {
            gui.onInventoryClose(event);
        }
    }

}
