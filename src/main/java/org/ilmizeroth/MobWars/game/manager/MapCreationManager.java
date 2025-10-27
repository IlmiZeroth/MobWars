package org.ilmizeroth.MobWars.game.manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.ilmizeroth.MobWars.game.Map;
import org.ilmizeroth.MobWars.game.repository.MapRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MapCreationManager {
    private static HashMap<Player, MapCreationManager> sessions = new HashMap();
    private int maxPlayers;
    private String mapName;
    private ArrayList<Location> positions;

    public MapCreationManager(String mapName, int maxPlayers){
        this.mapName = mapName;
        this.maxPlayers = maxPlayers;
        this.positions = new ArrayList<>(Arrays.asList(null, null, null, null, null, null));
    }
    public static MapCreationManager getSession(Player player){
        return sessions.get(player);
    }
    public Player getPlayer(){
        for(Player pl: sessions.keySet()){
            if(sessions.get(pl).equals(this)) return pl;
        }
        return null;
    }
    public void setFirstTeamSpawn(Location pos){
        positions.set(0, pos);
        if(isReady()) completeSession();
    }
    public void setSecondTeamSpawn(Location pos){
        positions.set(1, pos);
        if(isReady()) completeSession();
    }
    public void setFirstTeamBase(Location pos){
        positions.set(2, pos);
        if(isReady()) completeSession();
    }
    public void setSecondTeamBase(Location pos){
        positions.set(3, pos);
        if(isReady()) completeSession();
    }

    public static boolean hasActiveSession(Player player){
        return sessions.containsKey(player);
    }
    public static void startNewSession(Player player, String mapName, int maxPlayers){
        sessions.put(player, new MapCreationManager(mapName, maxPlayers));
    }
    public boolean isReady(){
        return positions.get(0) != null && positions.get(1) != null
                && positions.get(2) != null && positions.get(3) != null;
    }
    public void completeSession(){
        MapRepository.addMapToFile(new Map(mapName, maxPlayers, positions));
        cancelSession(getPlayer());
    }
    public static void cancelSession(Player player){
        sessions.remove(player);
    }
}
