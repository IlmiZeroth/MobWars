package org.giperfire.mobWars.game;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.giperfire.mobWars.MobWars;
import org.giperfire.mobWars.admin.MapController;
import org.giperfire.mobWars.admin.SignController;

import java.util.List;

public class Lobby {
    private final List<Player> playerList;
    private final int maxPlayers;
    private int currentPlayers = 1;
    private final String mapName;
    private BukkitTask startTimer;

    public Lobby(List<Player> playerList, int maxPlayers, String mapName) {
        this.playerList = playerList;
        this.maxPlayers = maxPlayers;
        this.mapName = mapName;
    }
    public boolean join(Player player){
        if(playerList.contains(player)) return false;
        if(currentPlayers == maxPlayers) return false;
        if(LobbyController.isPlayerInLobby(player)) return false;
        playerList.add(player);
        currentPlayers++;
        int currentPlayers = getPlayerList().size();
        SignController.updateSignForMap(MapController.getMap(mapName), currentPlayers);
        if (currentPlayers == 2) {
            playerList.forEach(p -> p.sendMessage("§a[MobWars]§7 Через 10 секунд начинается игра!"));
            startTimer = new BukkitRunnable() {
                @Override
                public void run() {
                    startGame();
                }
            }.runTaskLater(MobWars.getInstance(), 20*10);
        }
        if(currentPlayers == maxPlayers){
            startGame();
            return true;
        }
        for (Player p : getPlayerList()) {
            p.sendMessage("§a[MobWars] §e%player%§7 присоединился к лобби [%current_players%/%max_players%]."
                    .replace("%player%", player.getDisplayName())
                    .replace("%max_players%", ""+maxPlayers)
                    .replace("%current_players%", String.valueOf(getPlayerList().size())));
        }
        return true;
    }
    public boolean leave(Player player){
        if(!playerList.contains(player)) return false;
        playerList.remove(player);
        currentPlayers--;
        int currentPlayers = getPlayerList().size();
        SignController.updateSignForMap(MapController.getMap(mapName), currentPlayers);
        if(currentPlayers < 2){
            try{
                startTimer.cancel();
            } catch (Exception e){}
        }
        if(currentPlayers == 0) {
            LobbyController.removeLobby(this);
            return true;
        }

        for (Player pl : getPlayerList()) {
            pl.sendMessage("§c[MobWars]§e %player%§7 покинул лобби [%current_players%/%max_players%]."
                    .replace("%player%", player.getDisplayName())
                    .replace("%max_players%", ""+maxPlayers)
                    .replace("%current_players%", String.valueOf(getPlayerList().size())));
        }
        return true;
    }
    public List<Player> getPlayerList(){
        return playerList;
    }
    private void startGame(){
        playerList.forEach(p -> p.sendMessage("§a[MobWars]§7 Все в сборе, игра начинается!"));
        LobbyController.removeLobby(this);
        new Game(playerList, MapController.getMap(mapName));
    }
}
