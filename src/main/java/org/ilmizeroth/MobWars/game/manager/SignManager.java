package org.ilmizeroth.MobWars.game.manager;

import org.bukkit.Location;
import org.ilmizeroth.MobWars.game.Map;
import org.ilmizeroth.MobWars.game.repository.MapRepository;
import org.ilmizeroth.MobWars.game.Sign;
import org.ilmizeroth.MobWars.game.repository.SignRepository;

import java.util.List;

public class SignManager {
    private static List<Sign> signs = SignRepository.getAllSigns();

    private static String getMapState(String mapName){
        return MapRepository.getMap(mapName).isBusy() ? "§cIN_GAME" : "§aREADY";
    }

    public static void updateSign(Sign sign){
        String state = getMapState(sign.getMapName());
        Location signLocation = sign.getLocation();
        org.bukkit.block.Sign thisSign = (org.bukkit.block.Sign) signLocation.getBlock().getState();
        thisSign.setLine(2, state);
        thisSign.setLine(3, sign.getCurrentPlayers()+"/"+sign.getMaxPlayers());
        thisSign.update();
    }
    public static void updateAllSigns(){
        for(Sign sign: signs){
            updateSign(sign);
        }
    }
    public static void updateSignPlayers(Map map, int currentPlayers){
        for(Sign sign: signs){
            if(sign.getMapName().equals(map.getMapName())) {
                sign.setCurrentPlayers(currentPlayers);
                updateSign(sign);
            }
        }
    }
}
