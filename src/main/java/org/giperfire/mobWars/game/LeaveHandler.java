package org.giperfire.mobWars.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class LeaveHandler implements Listener {
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
}
