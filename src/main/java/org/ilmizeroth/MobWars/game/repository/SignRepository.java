package org.ilmizeroth.MobWars.game.repository;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ilmizeroth.MobWars.MobWars;
import org.ilmizeroth.MobWars.game.Map;
import org.ilmizeroth.MobWars.game.Sign;

import java.io.File;
import java.util.ArrayList;

public class SignRepository {
    private static ArrayList<Sign> signs = new ArrayList<>();
    private static FileConfiguration signsConfig;

    static{
        loadSignsFromFile();
    }

    public static ArrayList<Sign> getAllSigns(){
        return signs;
    }

    public static void loadSignsFromFile(){
        File file = getSignsFile();

        signsConfig = YamlConfiguration.loadConfiguration(file);
        signs.clear();
        if (signsConfig.contains("signs")) {
            for (String mapName : signsConfig.getConfigurationSection("signs").getKeys(false)) {
                for (String sign : signsConfig.getConfigurationSection("signs."+mapName).getKeys(false)) {
                    String path = "signs." + mapName + "." + sign;
                    Location location = new Location(
                            MobWars.getInstance().getServer().getWorld(signsConfig.getString(path + ".world")),
                            signsConfig.getDouble(path + ".x"),
                            signsConfig.getDouble(path + ".y"),
                            signsConfig.getDouble(path + ".z"),
                            (float) signsConfig.getDouble(path + ".yaw"),
                            (float) signsConfig.getDouble(path + ".pitch"));
                    Map map = MapRepository.getMap(mapName);
                    signs.add(new Sign(mapName, map.getMapMaxPlayers(), location));
                }
            }
        }
    }
    public static void addSignToFile(Sign sign){
        File file = getSignsFile();

        signsConfig = YamlConfiguration.loadConfiguration(file);

        String path = "signs." +  sign.getMapName() + "." + signs.size();
        Location loc = sign.getLocation();

        signsConfig.set(path + ".world", loc.getWorld().getName());
        signsConfig.set(path + ".x", loc.getX());
        signsConfig.set(path + ".y", loc.getY());
        signsConfig.set(path + ".z", loc.getZ());
        signsConfig.set(path + ".yaw", loc.getYaw());
        signsConfig.set(path + ".pitch", loc.getPitch());

        try {
            signsConfig.save(file);
        }catch (Exception ex){
            MobWars.getInstance().getLogger().severe("Failed to create signs.yml file!");
            ex.printStackTrace();
        }
        signs.add(sign);
    }

    public static void removeSign(Sign removedSign){
        File file = recreateAndGetSignsFile();
        signsConfig = YamlConfiguration.loadConfiguration(file);
        for(int i = 0; i<signs.size(); i++){
            if(removedSign.equals(signs.get(i))){
                signs.remove(signs.get(i));
                continue;
            }
            String path = "signs." +  signs.get(i).getMapName() + "." + i;
            Location loc = signs.get(i).getLocation();

            signsConfig.set(path + ".world", loc.getWorld().getName());
            signsConfig.set(path + ".x", loc.getX());
            signsConfig.set(path + ".y", loc.getY());
            signsConfig.set(path + ".z", loc.getZ());
            signsConfig.set(path + ".yaw", loc.getYaw());
            signsConfig.set(path + ".pitch", loc.getPitch());

        }
        try {
            signsConfig.save(file);
        }catch (Exception ex){
            MobWars.getInstance().getLogger().severe("Failed to create signs.yml file!");
            ex.printStackTrace();
        }
    }

    private static File getSignsFile(){
        File file = new File(MobWars.getInstance().getDataFolder(), "signs.yml");
        if (!file.exists()) {
            MobWars.getInstance().getDataFolder().mkdirs();
            try {
                file.createNewFile();
            }catch (Exception ex){
                MobWars.getInstance().getLogger().severe("Failed to create signs.yml file!");
                ex.printStackTrace();
            }
        }
        return file;
    }
    private static File recreateAndGetSignsFile(){
        File file = new File(MobWars.getInstance().getDataFolder(), "signs.yml");
        MobWars.getInstance().getDataFolder().mkdirs();
        try {
            file.delete();
            file.createNewFile();
        }catch (Exception ex){
            MobWars.getInstance().getLogger().severe("Failed to create signs.yml file!");
            ex.printStackTrace();
        }
        return file;
    }
}
