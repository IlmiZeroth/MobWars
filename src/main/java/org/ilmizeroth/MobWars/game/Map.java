package org.ilmizeroth.MobWars.game;

import org.bukkit.Location;

import java.util.ArrayList;

public class Map {
    private String mapName;
    private int mapMaxPlayers;
    private boolean isBusy;
    private ArrayList<Location> positions;

    public Map(String mapName, int mapMaxPlayers, ArrayList<Location> positions){
        this.mapName = mapName;
        this.mapMaxPlayers = mapMaxPlayers;
        this.positions = positions;
        this.isBusy = false;
    }
    public String getMapName(){
        return mapName;
    }
    public int getMapMaxPlayers(){
        return mapMaxPlayers;
    }
    public ArrayList<Location> getPositions(){
        return positions;
    }
    public Location getFirstTeamSpawn(){
        return positions.get(0);
    }
    public Location getSecondTeamSpawn(){
        return positions.get(1);
    }
    public Location getFirstTeamBase(){
        return positions.get(2);
    }
    public Location getSecondTeamBase(){
        return positions.get(3);
    }
    public boolean isBusy(){
        return isBusy;
    }
    public void setBusy(boolean state){
        this.isBusy = state;
    }
}
