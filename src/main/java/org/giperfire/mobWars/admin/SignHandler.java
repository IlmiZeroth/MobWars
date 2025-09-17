package org.giperfire.mobWars.admin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.giperfire.mobWars.game.Lobby;
import org.giperfire.mobWars.game.LobbyController;

import java.util.ArrayList;

public class SignHandler implements Listener {
    @EventHandler
    public void onSignBreak(BlockBreakEvent event){
        if(event.getBlock().getState() instanceof org.bukkit.block.Sign) {
            for (Sign sign : SignController.getAllSigns()) {
                if (event.getBlock().getLocation().equals(sign.getLocation())){
                    if(!event.getPlayer().isOp()) {
                        event.setCancelled(true);
                        return;
                    }
                    event.getPlayer().sendMessage("§c[MobWars] Табличка была уничтожена!");
                    SignController.removeSign(new Sign("any", 0, event.getBlock().getLocation()));
                }
            }
        }
    }
    @EventHandler
    public void onSignBreak(SignChangeEvent event){
        if(event.getBlock().getState() instanceof org.bukkit.block.Sign) {
            for (Sign sign : SignController.getAllSigns()) {
                if (event.getBlock().getLocation().equals(sign.getLocation())){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void interactWithSign(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getClickedBlock().getState() instanceof org.bukkit.block.Sign){
                for (Sign sign : SignController.getAllSigns()) {
                    if (event.getClickedBlock().getLocation().equals(sign.getLocation())) {
                        event.setCancelled(true);
                        Lobby lobby = LobbyController.getLobby(MapController.getMap(sign.getMapName()));
                        Map map = MapController.getMap(sign.getMapName());
                        if (lobby != null && lobby.getPlayerList().contains(event.getPlayer())) {
                            lobby.leave(event.getPlayer());
                            event.getPlayer().sendMessage("§c[MobWars]§7 Вы покинули лобби!");
                            return;
                        } else if (!map.isBusy()) {
                            if (lobby == null) {
                                lobby = new Lobby(new ArrayList<>(), sign.getMaxPlayers(), sign.getMapName());
                                LobbyController.addLobby(map,
                                        lobby);
                            }
                            if(lobby.join(event.getPlayer()))
                                event.getPlayer().sendMessage("§a[MobWars]§7 Вы присоединились к лобби!");
                            else
                                event.getPlayer().sendMessage("§c[MobWars] Вы уже присоединились к другому лобби!");
                        } else {
                            event.getPlayer().sendMessage("§c[MobWars] Вы не можете подключиться к лобби.");
                        }

                    }
                }
            }
        }
    }
}
