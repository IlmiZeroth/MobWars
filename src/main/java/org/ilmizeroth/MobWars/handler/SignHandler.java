package org.ilmizeroth.MobWars.handler;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.ilmizeroth.MobWars.game.Map;
import org.ilmizeroth.MobWars.game.repository.MapRepository;
import org.ilmizeroth.MobWars.game.Lobby;
import org.ilmizeroth.MobWars.game.controller.LobbyController;
import org.ilmizeroth.MobWars.game.Sign;
import org.ilmizeroth.MobWars.game.repository.SignRepository;

import java.util.ArrayList;

public class SignHandler {

    public void handleSignBreak(BlockBreakEvent event){
        for (Sign sign : SignRepository.getAllSigns()) {
            if (event.getBlock().getLocation().equals(sign.getLocation())){
                if(!event.getPlayer().hasPermission("mobWars.admin") && !event.getPlayer().isOp()) {
                    event.setCancelled(true);
                    return;
                }
                event.getPlayer().sendMessage("§c[MobWars] Табличка была уничтожена!");
                SignRepository.removeSign(new Sign("any", 0, event.getBlock().getLocation()));
            }
        }
    }

    public void handleInteractWithSign(PlayerInteractEvent event){
        for (Sign sign : SignRepository.getAllSigns()) {
            if (event.getClickedBlock().getLocation().equals(sign.getLocation())) {
                event.setCancelled(true);
                Lobby lobby = LobbyController.getLobby(MapRepository.getMap(sign.getMapName()));
                Map map = MapRepository.getMap(sign.getMapName());
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
