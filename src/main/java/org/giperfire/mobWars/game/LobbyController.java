package org.giperfire.mobWars.game;

import org.bukkit.entity.Player;
import org.giperfire.mobWars.admin.Map;

import java.util.HashMap;
import java.util.UUID;

public class LobbyController {
    private static final HashMap<Map, Lobby> allLobbies = new HashMap();
    public static Lobby getLobby(Map uuid){
        return allLobbies.get(uuid);
    }
    public static void addLobby(Map map, Lobby lobby){
        allLobbies.put(map, lobby);
    }
    public static void removeLobby(Lobby lobby){
        for(Map map: allLobbies.keySet()){
            if(allLobbies.get(map).equals(lobby)){
                allLobbies.remove(map);
            }
        }
        System.out.println(allLobbies);
    }
    public static boolean isPlayerInLobby(Player player){
        for(Lobby lobby: allLobbies.values()){
            if(lobby.getPlayerList().contains(player)) return true;
        }
        return false;
    }
    public static Lobby getLobbyWithPlayer(Player player){
        for(Lobby lobby: allLobbies.values()){
            if(lobby.getPlayerList().contains(player)) return lobby;
        }
        return null;
    }
}