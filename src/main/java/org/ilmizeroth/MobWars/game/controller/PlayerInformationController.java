package org.ilmizeroth.MobWars.game.controller;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ilmizeroth.MobWars.MobWars;
import org.ilmizeroth.MobWars.game.player.PlayerInformation;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class PlayerInformationController {
    private static HashMap<UUID, PlayerInformation> informationHashMap = new HashMap<>();
    private static File inventoryFile;
    public PlayerInformationController(){
        createFileIfNotExists();
        loadInformationsFromFile();
    }

    public static PlayerInformation getInformation(UUID uuid){
        return informationHashMap.get(uuid);
    }
    private static void createFileIfNotExists() {
        inventoryFile = new File(MobWars.getInstance().getDataFolder(), "player_information.yml");

        if (!inventoryFile.exists()) {
            MobWars.getInstance().getDataFolder().mkdirs();
            try {
                inventoryFile.createNewFile();
                FileConfiguration config = YamlConfiguration.loadConfiguration(inventoryFile);

                config.options().copyDefaults(true);
                config.save(inventoryFile);

                MobWars.getInstance().getLogger().info("Successfully created player_information.yml file!");
            } catch (IOException ex) {
                MobWars.getInstance().getLogger().severe("Failed to create player_information.yml file!");
                ex.printStackTrace();
            }
        }
    }

    public static void loadInformationsFromFile(){
        createFileIfNotExists();
        FileConfiguration config = YamlConfiguration.loadConfiguration(inventoryFile);
        informationHashMap.clear();
        for (String key : config.getConfigurationSection("Information.").getKeys(true)) {
            informationHashMap.put(UUID.fromString(key), (PlayerInformation) config.get("Information." + key));
        }
    }
    public static void saveInformationToFile(UUID uuid, PlayerInformation playerInformation){
        createFileIfNotExists();
        FileConfiguration config = YamlConfiguration.loadConfiguration(inventoryFile);
        informationHashMap.put(uuid, playerInformation);
        for(UUID playerUUID: informationHashMap.keySet()) {
            config.set("Information." + playerUUID.toString(), informationHashMap.get(playerUUID));
        }
        try {
            config.save(inventoryFile);
        } catch (IOException e) {
        }
    }
}