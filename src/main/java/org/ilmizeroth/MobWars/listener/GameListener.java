package org.ilmizeroth.MobWars.listener;

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
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.ilmizeroth.MobWars.game.controller.GameController;
import org.ilmizeroth.MobWars.game.controller.LobbyController;
import org.ilmizeroth.MobWars.handler.GameHandler;

public class GameListener implements Listener {

    private final GameHandler gameHandler;
    public GameListener(GameHandler gameHandler){
        this.gameHandler = gameHandler;
    }

    @EventHandler
    public void onEmeraldUse(PlayerInteractEvent event) {
        if (GameController.isPlayerInGame(event.getPlayer())) {
            gameHandler.handleEmeraldUse(event);
        }
    }
    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player player)) return;
        if (GameController.isPlayerInGame(player)) {
            gameHandler.handleFallDamage(event);
        }
    }

    @EventHandler
    public void onStarUse(PlayerInteractEvent event) {
        if (GameController.isPlayerInGame(event.getPlayer())) {
            if (event.hasItem() && event.getMaterial().equals(Material.END_CRYSTAL)) {
                gameHandler.handleStarUse(event);
            }
        }
    }
    @EventHandler
    public void handlerDeath(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (GameController.isPlayerInGame(player)) {
                gameHandler.handleDeath(event);
            }
        }
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(LobbyController.isPlayerInLobby(player)){
            LobbyController.getLobbyWithPlayer(player).leave(player);
        }
        if(GameController.isPlayerInGame(player)){
            GameController.getGameWithPlayer(player).getGamePlayers().get(player).leave();
            GameController.getGameWithPlayer(player).leave(player);

        }
    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (GameController.isPlayerInGame(player)) {
                gameHandler.handlePlayerHunger(event);
            }
        }
    }

    @EventHandler
    public void killBlockMobs(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        if (GameController.isPlayerInGame(event.getEntity().getKiller())) {
            gameHandler.handleKillBlockMobs(event);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (GameController.isPlayerInGame(player)) {
            gameHandler.handleInventoryClick(event);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (GameController.isPlayerInGame(player)) {
            gameHandler.handleInventoryDrag(event);
        }
    }

    @EventHandler
    public void onHotkeySwap(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (GameController.isPlayerInGame(player)) {
            gameHandler.handleHotkeySwap(event);
        }
    }

    @EventHandler
    public void playerDropEvent(PlayerDropItemEvent event) {
        if (GameController.isPlayerInGame(event.getPlayer())) {
            gameHandler.handlePlayerDropEvent(event);
        }
    }

    @EventHandler
    public void playerSwapHandsEvent(PlayerSwapHandItemsEvent event) {
        if (GameController.isPlayerInGame(event.getPlayer())) {
            gameHandler.handlePlayerSwapHandsEvent(event);
        }
    }

    @EventHandler
    public void onCreativeInventoryAction(InventoryCreativeEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (GameController.isPlayerInGame(player)) {
            gameHandler.handleCreativeInventoryAction(event);
        }
    }

}