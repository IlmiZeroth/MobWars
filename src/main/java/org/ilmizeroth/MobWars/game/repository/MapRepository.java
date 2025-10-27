package org.ilmizeroth.MobWars.game.repository;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ilmizeroth.MobWars.MobWars;
import org.ilmizeroth.MobWars.game.Map;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MapRepository {
    private final static LinkedHashMap<String, Map> mapList = new LinkedHashMap<>();
    private static FileConfiguration mapsConfig;

    static{
        loadMapsFromFile();
    }

    public static Map getMap(String index){
        return mapList.get(index);
    }

    public static Location getLocationFromPath(String path){
        Location loc = new Location(
                MobWars.getInstance().getServer().getWorld(mapsConfig.getString(path + ".world")),
                mapsConfig.getDouble(path + ".x"),
                mapsConfig.getDouble(path + ".y"),
                mapsConfig.getDouble(path + ".z"),
                (float) mapsConfig.getDouble(path + ".yaw"),
                (float) mapsConfig.getDouble(path + ".pitch")
        );
        return loc;
    }

    public static void loadMapsFromFile(){
        File file = new File(MobWars.getInstance().getDataFolder(), "maps.yml");
        if (!file.exists()) {
            MobWars.getInstance().getDataFolder().mkdirs();
            try {
                file.createNewFile();
            }catch (Exception ex){
                MobWars.getInstance().getLogger().severe("Failed to create maps.yml file!");
                ex.printStackTrace();
            }
        }

        mapsConfig = YamlConfiguration.loadConfiguration(file);

        if (mapsConfig.contains("maps")) {
            for (String mapName : mapsConfig.getConfigurationSection("maps").getKeys(false)) {
                int maxPlayers = mapsConfig.getInt("maps." + mapName + ".maxPlayers");
                ArrayList<Location> positions = new ArrayList<>();
                positions.add(getLocationFromPath(String.format("maps.%s.positions.FirstTeamSpawn", mapName)));
                positions.add(getLocationFromPath(String.format("maps.%s.positions.SecondTeamSpawn", mapName)));
                positions.add(getLocationFromPath(String.format("maps.%s.positions.FirstTeamBase", mapName)));
                positions.add(getLocationFromPath(String.format("maps.%s.positions.SecondTeamBase", mapName)));
                mapList.put(mapName, new Map(mapName, maxPlayers, positions));

            }
        }
    }
    public static void addMapToFile(Map map){
        File file = new File(MobWars.getInstance().getDataFolder(), "maps.yml");
        if (!file.exists()) {
            MobWars.getInstance().getDataFolder().mkdirs();
            try {
                file.createNewFile();
            }catch (Exception ex){
                MobWars.getInstance().getLogger().severe("Failed to create maps.yml file!");
                ex.printStackTrace();
            }
        }
        mapsConfig = YamlConfiguration.loadConfiguration(file);

        ArrayList<Location> positions = map.getPositions();
        String path = "maps." +  map.getMapName();
        mapsConfig.set(path + ".maxPlayers", map.getMapMaxPlayers());

        setLocationToPath(String.format("%s.positions.FirstTeamSpawn", path), positions.get(0));
        setLocationToPath(String.format("%s.positions.SecondTeamSpawn", path), positions.get(1));

        setLocationToPath(String.format("%s.positions.FirstTeamBase", path), positions.get(2));
        setLocationToPath(String.format("%s.positions.SecondTeamBase", path), positions.get(3));
        try {
            mapsConfig.save(file);
        }catch (Exception ex){
            MobWars.getInstance().getLogger().severe("Failed to create maps.yml file!");
            ex.printStackTrace();
        }
        mapList.put(map.getMapName(), map);
    }
    private static void setLocationToPath(String path, Location loc){
        mapsConfig.set(path + ".world", loc.getWorld().getName());
        mapsConfig.set(path + ".x", loc.getX());
        mapsConfig.set(path + ".y", loc.getY());
        mapsConfig.set(path + ".z", loc.getZ());
        mapsConfig.set(path + ".yaw", loc.getYaw());
        mapsConfig.set(path + ".pitch", loc.getPitch());
    }
}
