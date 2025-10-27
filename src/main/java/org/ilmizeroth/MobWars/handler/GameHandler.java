package org.ilmizeroth.MobWars.handler;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.ilmizeroth.MobWars.game.controller.GameController;
import org.ilmizeroth.MobWars.game.player.GamePlayer;
import org.ilmizeroth.MobWars.mob.*;

import java.util.Set;

public class GameHandler{

    private static final Set<Integer> PROTECTED_SLOTS = Set.of(0, 1, 2, 7, 8, 36, 37, 38, 39);

    public void handleEmeraldUse(PlayerInteractEvent event) {
        if (event.hasItem() && event.getMaterial().equals(Material.EMERALD)) {
            GameController.getGameWithPlayer(event.getPlayer()).openGUI(event.getPlayer());
        }
    }

    public void handleFallDamage(EntityDamageEvent event) {
        if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) event.setCancelled(true);
    }

    public void handleStarUse(PlayerInteractEvent event) {
        if (event.hasItem() && event.getMaterial().equals(Material.END_CRYSTAL)) {
            GameController.getGameWithPlayer(event.getPlayer()).openWeaponGUI(event.getPlayer());
        }

    }

    public void handleDeath(EntityDamageEvent event){
        Player player = (Player) event.getEntity();
        boolean hasTotemInHand = hasTotemInAnyHand(player);

        if (hasTotemInHand) return;

        else if (player.getHealth() - event.getDamage() <= 0) {
            event.setCancelled(true);
            player.setHealth(20);
            GameController.getGameWithPlayer(player).playerDeath(player);
        }
    }

    public void handlePlayerHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    public void handleKillBlockMobs(EntityDeathEvent event) {
            if (event.getEntity().hasMetadata("mobType")) {
                Player killer = event.getEntity().getKiller();
                String mobType = event.getEntity().getMetadata("mobType").get(0).asString();
                switch (mobType) {
                    case "glassBlock" ->
                            playerKillMobAnnouncement(killer, GlassMob.getClassStats().cost() / 8);
                    case "sandBlock" ->
                            playerKillMobAnnouncement(killer, SandMob.getClassStats().cost() / 8);
                    case "dirtBlock" ->
                            playerKillMobAnnouncement(killer, DirtMob.getClassStats().cost() / 8);
                    case "oakBlock" ->
                            playerKillMobAnnouncement(killer, OakMob.getClassStats().cost() / 8);
                    case "cobblestoneBlock" ->
                            playerKillMobAnnouncement(killer, CobblestoneMob.getClassStats().cost() / 8);
                    case "ironBlock" ->
                            playerKillMobAnnouncement(killer, IronMob.getClassStats().cost() / 8);
                    case "goldBlock" ->
                            playerKillMobAnnouncement(killer, GoldMob.getClassStats().cost() / 8);
                    case "diamondBlock" ->
                            playerKillMobAnnouncement(killer, DiamondMob.getClassStats().cost() / 8);
                    case "obsidianBlock" ->
                            playerKillMobAnnouncement(killer, ObsidianMob.getClassStats().cost() / 8);
                    case "bedrockBlock" ->
                            playerKillMobAnnouncement(killer, BedrockMob.getClassStats().cost() / 8);
                }
            }


    }
    private void playerKillMobAnnouncement(Player player, double payAmount){
        GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
        gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()+payAmount);
        player.sendMessage(String.format("§c[MobWars]§7 Вы получили §e%.2f⛃ §7за убийство моба.", payAmount));
    }

    public void handleInventoryClick(InventoryClickEvent event) {
        if (isProtectedSlot(event.getSlot()) ||
                isProtectedSlot(event.getRawSlot()) ||
                (event.getHotbarButton() != -1 && isProtectedSlot(event.getHotbarButton()))) {
            event.setCancelled(true);
        }
    }

    public void handleInventoryDrag(InventoryDragEvent event) {
        for (int slot : event.getRawSlots()) {
            if (isProtectedSlot(slot)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    public void handleHotkeySwap(InventoryClickEvent event) {
        if (event.getClick() == ClickType.NUMBER_KEY) {
            int hotbarSlot = event.getHotbarButton();
            if (isProtectedSlot(hotbarSlot)) {
                event.setCancelled(true);
            }
        }
    }

    public void handlePlayerDropEvent(PlayerDropItemEvent event) {
        int slot = event.getPlayer().getInventory().getHeldItemSlot();
        if (isProtectedSlot(slot)) event.setCancelled(true);
    }

    public void handlePlayerSwapHandsEvent(PlayerSwapHandItemsEvent event) {
        int slot = event.getPlayer().getInventory().getHeldItemSlot();
        if (isProtectedSlot(slot)) event.setCancelled(true);
    }


    public void handleCreativeInventoryAction(InventoryCreativeEvent event) {
        if (isProtectedSlot(event.getSlot())) event.setCancelled(true);
    }

    private boolean isProtectedSlot(int slot) {
        return PROTECTED_SLOTS.contains(slot);
    }

    private boolean hasTotemInAnyHand(Player player) {
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        if (mainHand != null && mainHand.getType() == Material.TOTEM_OF_UNDYING) {
            return true;
        }
        ItemStack offHand = player.getInventory().getItemInOffHand();
        if (offHand != null && offHand.getType() == Material.TOTEM_OF_UNDYING) {
            return true;
        }

        return false;
    }
}