package org.ilmizeroth.MobWars.game.controller;

import org.bukkit.entity.Player;
import org.ilmizeroth.MobWars.game.Game;
import org.ilmizeroth.MobWars.game.Map;

import javax.annotation.Nullable;
import java.util.HashMap;

public class GameController {
    private static final HashMap<Map, Game> allGames = new HashMap();
    public static Game getGame(Map map){
        return allGames.get(map);
    }
    public static void addGame(Map map, Game game){
        allGames.put(map, game);
    }
    public static void removeGame(Map map){
        allGames.remove(map);
    }
    public static boolean isPlayerInGame(Player player){
        for(Game game: allGames.values()){
            if(game.getPlayerList().contains(player)) return true;
        }
        return false;
    }
    @Nullable
    public static Game getGameWithPlayer(Player player){
        for(Game game: allGames.values()){
            if(game.getPlayerList().contains(player)) return game;
        }
        return null;
    }
}
