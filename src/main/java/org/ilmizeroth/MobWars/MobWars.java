package org.ilmizeroth.MobWars;

import org.bukkit.plugin.java.JavaPlugin;
import org.ilmizeroth.MobWars.admin.AdminExecutor;
import org.ilmizeroth.MobWars.game.repository.MapRepository;
import org.ilmizeroth.MobWars.game.repository.SignRepository;
import org.ilmizeroth.MobWars.handler.SignHandler;
import org.ilmizeroth.MobWars.handler.GUIHandler;
import org.ilmizeroth.MobWars.handler.GameHandler;
import org.ilmizeroth.MobWars.listener.GUIListener;
import org.ilmizeroth.MobWars.listener.GameListener;
import org.ilmizeroth.MobWars.listener.SignListener;

public final class MobWars extends JavaPlugin {
    private static JavaPlugin javaPlugin;
    public static JavaPlugin getInstance(){
        return javaPlugin;
    }
    public MobWars(){
        javaPlugin = this;
    }
    @Override
    public void onEnable() {
        getDataFolder().mkdir();

        getCommand("mwa").setExecutor(new AdminExecutor());
//        getCommand("mobwars").setExecutor(new TestExecutor());
        MapRepository.loadMapsFromFile();
        SignRepository.loadSignsFromFile();
        getServer().getPluginManager().registerEvents(new SignListener(new SignHandler()), this);
        getServer().getPluginManager().registerEvents(new GUIListener(new GUIHandler()), this);
        getServer().getPluginManager().registerEvents(new GameListener(new GameHandler()), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
