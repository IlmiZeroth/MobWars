package org.giperfire.mobWars;

import org.bukkit.plugin.java.JavaPlugin;
import org.giperfire.mobWars.admin.AdminExecutor;
import org.giperfire.mobWars.admin.MapController;
import org.giperfire.mobWars.admin.SignController;
import org.giperfire.mobWars.admin.SignHandler;
import org.giperfire.mobWars.game.GameHandler;
import org.giperfire.mobWars.game.LeaveHandler;
import org.giperfire.mobWars.game.PlayerDeathHandler;
import org.giperfire.mobWars.gui.GUIListener;

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
        MapController.loadMapsFromFile();
        SignController.loadSignsFromFile();
        getServer().getPluginManager().registerEvents(new SignHandler(), this);
        getServer().getPluginManager().registerEvents(new LeaveHandler(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
        getServer().getPluginManager().registerEvents(new GameHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathHandler(), this);


//        BetterModelPlugin betterModel = (BetterModelPlugin) Bukkit.getPluginManager().getPlugin("BetterModel");
//        if (betterModel != null) {
//            BetterModel.register(betterModel);  // Принудительная регистрация
//        } else {
//            throw new RuntimeException("BetterModel не найден!");
//        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
