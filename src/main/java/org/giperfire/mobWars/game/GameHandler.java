package org.giperfire.mobWars.game;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.giperfire.mobWars.gui.AnyGUI;
import org.giperfire.mobWars.mob.*;

import java.util.Set;

public class GameHandler implements Listener {

    private static final Set<Integer> PROTECTED_SLOTS = Set.of(0, 1, 2, 7, 8, 36, 37, 38, 39);

    @EventHandler
    public void onEmeraldUse(PlayerInteractEvent event) {
        if (GameController.isPlayerInGame(event.getPlayer())) {
            if (event.hasItem() && event.getMaterial().equals(Material.EMERALD)) {
                GameController.getGameWithPlayer(event.getPlayer()).openGUI(event.getPlayer());
            }
        }
    }
    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        if (GameController.isPlayerInGame(((Player) event.getEntity()).getPlayer())) {
            if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onStarUse(PlayerInteractEvent event) {
        if (GameController.isPlayerInGame(event.getPlayer())) {
            if (event.hasItem() && event.getMaterial().equals(Material.END_CRYSTAL)) {
                GameController.getGameWithPlayer(event.getPlayer()).openWeaponGUI(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            if (GameController.isPlayerInGame(((Player) event.getEntity()).getPlayer())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void killBlockMobs(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        Player killer = event.getEntity().getKiller();
        if (GameController.isPlayerInGame(killer)) {
            if (event.getEntity().hasMetadata("mobType")) {
                String mobType = event.getEntity().getMetadata("mobType").get(0).asString();
                switch (mobType){
                    case "glassBlock" -> playerKillMobAnnouncement(killer.getPlayer(), GlassMob.getClassStats().getCost()/8);
                    case "sandBlock" -> playerKillMobAnnouncement(killer.getPlayer(), SandMob.getClassStats().getCost()/8);
                    case "dirtBlock" -> playerKillMobAnnouncement(killer.getPlayer(), DirtMob.getClassStats().getCost()/8);
                    case "oakBlock" -> playerKillMobAnnouncement(killer.getPlayer(), OakMob.getClassStats().getCost()/8);
                    case "cobblestoneBlock" -> playerKillMobAnnouncement(killer.getPlayer(), CobblestoneMob.getClassStats().getCost()/8);
                    case "ironBlock" -> playerKillMobAnnouncement(killer.getPlayer(), IronMob.getClassStats().getCost()/8);
                    case "goldBlock" -> playerKillMobAnnouncement(killer.getPlayer(), GoldMob.getClassStats().getCost()/8);
                    case "diamondBlock" -> playerKillMobAnnouncement(killer.getPlayer(), DiamondMob.getClassStats().getCost()/8);
                    case "obsidianBlock" -> playerKillMobAnnouncement(killer.getPlayer(), ObsidianMob.getClassStats().getCost()/8);
                    case "bedrockBlock" -> playerKillMobAnnouncement(killer.getPlayer(), BedrockMob.getClassStats().getCost()/8);
                }
            }
        }
    }
    private void playerKillMobAnnouncement(Player player, double payAmount){
        GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
        gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()+payAmount);
        player.sendMessage(String.format("§c[MobWars]§7 Вы получили §e%.2f⛃ §7за убийство моба.", payAmount));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        if (GameController.isPlayerInGame(player)) {
            // Проверяем все возможные слоты
            if (isProtectedSlot(event.getSlot()) ||
                    isProtectedSlot(event.getRawSlot()) ||
                    (event.getHotbarButton() != -1 && isProtectedSlot(event.getHotbarButton()))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        if (GameController.isPlayerInGame(((Player) event.getWhoClicked()).getPlayer())) {
            // Проверяем все затронутые слоты
            for (int slot : event.getRawSlots()) {
                if (isProtectedSlot(slot)) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onHotkeySwap(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        if (GameController.isPlayerInGame(player)) {
            // Обрабатываем горячие клавиши (нажатие цифр)
            if (event.getClick() == ClickType.NUMBER_KEY) {
                int hotbarSlot = event.getHotbarButton();
                if (isProtectedSlot(hotbarSlot)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void playerDropEvent(PlayerDropItemEvent event) {
        if (GameController.isPlayerInGame(event.getPlayer())) {
            int slot = event.getPlayer().getInventory().getHeldItemSlot();
            if (isProtectedSlot(slot)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void playerSwapHandsEvent(PlayerSwapHandItemsEvent event) {
        if (GameController.isPlayerInGame(event.getPlayer())) {
            int slot = event.getPlayer().getInventory().getHeldItemSlot();
            if (isProtectedSlot(slot)) {
                event.setCancelled(true);
            }
        }
    }

    // Новый обработчик для Creative режима (на всякий случай)
    @EventHandler
    public void onCreativeInventoryAction(InventoryCreativeEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        if (GameController.isPlayerInGame(((Player) event.getWhoClicked()).getPlayer())) {
            if (isProtectedSlot(event.getSlot())) {
                event.setCancelled(true);
            }
        }
    }

    private boolean isProtectedSlot(int slot) {
        return PROTECTED_SLOTS.contains(slot);
    }
}