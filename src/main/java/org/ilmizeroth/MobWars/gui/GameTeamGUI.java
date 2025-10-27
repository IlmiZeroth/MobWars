package org.ilmizeroth.MobWars.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.ilmizeroth.MobWars.game.controller.GameController;
import org.ilmizeroth.MobWars.game.player.GamePlayer;
import org.ilmizeroth.MobWars.game.GameTeam;
import org.ilmizeroth.MobWars.handler.GUIHandler;
import org.ilmizeroth.MobWars.listener.GUIListener;
import org.ilmizeroth.MobWars.mob.*;

public class GameTeamGUI implements ChestGUI{
    private final Player player;
    private Inventory inventory;
    private final GameTeam team;
    private final GameTeam enemy;

    public GameTeamGUI(Player player, GameTeam team, GameTeam enemy){
        this.player = player;
        this.inventory = createInventory();
        this.team = team;
        this.enemy = enemy;
    }

    @Override
    public Inventory createInventory() {
        Inventory inv = Bukkit.createInventory(null, 54, "Меню атаки");
        initializeItems(inv);
        return inv;
    }

    @Override
    public void initializeItems(Inventory inv) {
        /*
                               double maxHealth,
                               double moveSpeed,
                               double baseDamage,
                               double attackDamage,
                               double attackSpeed,
                               double cost,
                               double paydayIncrease,
                               double cooldown
         */
        for(int i = 0; i<54; i++){
            inv.setItem(i, createGuiItem(Material.BLACK_STAINED_GLASS_PANE, " ", 1, false));
        }
        double balance = 0;
        try {
            balance = GameController.getGameWithPlayer(player).getGamePlayers().get(player).getPlayerBalance();
        } catch (Exception e){}
        inv.setItem(10, createGuiItem(Material.BARRIER, "Стекляшка", 1, balance >= GlassMob.getClassStats().cost(), createLore(GlassMob.getClassStats())));
        inv.setItem(11, createGuiItem(Material.BARRIER, "Прах", 1, balance >= SandMob.getClassStats().cost(), createLore(SandMob.getClassStats())));
        inv.setItem(12, createGuiItem(Material.BARRIER, "Говняшка", 1, balance >= DirtMob.getClassStats().cost(), createLore(DirtMob.getClassStats())));
        inv.setItem(13, createGuiItem(Material.BARRIER, "Деревяшка", 1, balance >= OakMob.getClassStats().cost(), createLore(OakMob.getClassStats())));
        inv.setItem(14, createGuiItem(Material.BARRIER, "Сын камня", 1, balance >= CobblestoneMob.getClassStats().cost(), createLore(CobblestoneMob.getClassStats())));
        inv.setItem(15, createGuiItem(Material.BARRIER, "Тупой утюг", 1, balance >= IronMob.getClassStats().cost(), createLore(IronMob.getClassStats())));
        inv.setItem(16, createGuiItem(Material.BARRIER, "Шакал младший", 1, balance >= GoldMob.getClassStats().cost(), createLore(GoldMob.getClassStats())));
        inv.setItem(19, createGuiItem(Material.BARRIER, "Шакал старший", 1, balance >= DiamondMob.getClassStats().cost(), createLore(DiamondMob.getClassStats())));
        inv.setItem(20, createGuiItem(Material.BARRIER, "Мазохист", 1, balance >= ObsidianMob.getClassStats().cost(), createLore(ObsidianMob.getClassStats())));
        inv.setItem(21, createGuiItem(Material.BARRIER, "Хороший парень", 1, balance >= BedrockMob.getClassStats().cost(), createLore(BedrockMob.getClassStats())));
    }

    @Override
    public void openInventory(Player p) {
        p.openInventory(inventory);
        GUIHandler.openGUI(p, this);
    }

    @Override
    public void closeInventory(Player p) {
        GUIHandler.closeGUI(p);
    }
    @Override
    public void updateInventory() {
        closeInventory(player);
        openInventory(player);
    }
    public void updateItem(Class blockMob, int cooldown, int count){
        double balance = 0;
        int maxSummonRate = 0;
        int currentSummonRate = 0;
        try {
            balance = GameController.getGameWithPlayer(player).getGamePlayers().get(player).getPlayerBalance();
        } catch (Exception e){}
        if(count > 0) switch (blockMob.getSimpleName()){
            case "GlassMob" -> inventory.setItem(10, createGuiItem(Material.BLUE_STAINED_GLASS, "Стекляшка", count, balance >= GlassMob.getClassStats().cost(), createLore(GlassMob.getClassStats(), cooldown)));
            case "SandMob" -> inventory.setItem(11, createGuiItem(Material.SAND, "Прах", count, balance >= SandMob.getClassStats().cost(), createLore(SandMob.getClassStats(), cooldown)));
            case "DirtMob" -> inventory.setItem(12, createGuiItem(Material.DIRT, "Говняшка", count, balance >= DirtMob.getClassStats().cost(), createLore(DirtMob.getClassStats(), cooldown)));
            case "OakMob" -> inventory.setItem(13, createGuiItem(Material.OAK_LOG, "Деревяшка", count, balance >= OakMob.getClassStats().cost(), createLore(OakMob.getClassStats(), cooldown)));
            case "CobblestoneMob" -> inventory.setItem(14, createGuiItem(Material.COBBLESTONE, "Сын камня", count, balance >= CobblestoneMob.getClassStats().cost(), createLore(CobblestoneMob.getClassStats(), cooldown)));
            case "IronMob" -> inventory.setItem(15, createGuiItem(Material.IRON_BLOCK, "Тупой утюг", count, balance >= IronMob.getClassStats().cost(), createLore(IronMob.getClassStats(), cooldown)));
            case "GoldMob" -> inventory.setItem(16, createGuiItem(Material.GOLD_BLOCK, "Шакал младший", count, balance >= GoldMob.getClassStats().cost(), createLore(GoldMob.getClassStats(), cooldown)));
            case "DiamondMob" -> inventory.setItem(19, createGuiItem(Material.DIAMOND_BLOCK, "Шакал старший", count, balance >= DiamondMob.getClassStats().cost(), createLore(DiamondMob.getClassStats(), cooldown)));
            case "ObsidianMob" -> inventory.setItem(20, createGuiItem(Material.OBSIDIAN, "Мазохист", count, balance >= ObsidianMob.getClassStats().cost(), createLore(ObsidianMob.getClassStats(), cooldown)));
            case "BedrockMob" -> inventory.setItem(21, createGuiItem(Material.BEDROCK, "Хороший парень", count, balance >= BedrockMob.getClassStats().cost(), createLore(BedrockMob.getClassStats(), cooldown)));
        }
        if(count == 0) switch (blockMob.getSimpleName()){
            case "GlassMob" -> inventory.setItem(10, createGuiItem(Material.BARRIER, "Стекляшка", 1, false, createLore(GlassMob.getClassStats(), cooldown)));
            case "SandMob" -> inventory.setItem(11, createGuiItem(Material.BARRIER, "Прах", 1, false, createLore(SandMob.getClassStats(), cooldown)));
            case "DirtMob" -> inventory.setItem(12, createGuiItem(Material.BARRIER, "Говняшка", 1, false, createLore(DirtMob.getClassStats(), cooldown)));
            case "OakMob" -> inventory.setItem(13, createGuiItem(Material.BARRIER, "Деревяшка", 1, false, createLore(OakMob.getClassStats(), cooldown)));
            case "CobblestoneMob" -> inventory.setItem(14, createGuiItem(Material.BARRIER, "Сын камня", 1, false, createLore(CobblestoneMob.getClassStats(), cooldown)));
            case "IronMob" -> inventory.setItem(15, createGuiItem(Material.BARRIER, "Тупой утюг", 1, false, createLore(IronMob.getClassStats(), cooldown)));
            case "GoldMob" -> inventory.setItem(16, createGuiItem(Material.BARRIER, "Шакал младший", 1, false, createLore(GoldMob.getClassStats(), cooldown)));
            case "DiamondMob" -> inventory.setItem(19, createGuiItem(Material.BARRIER, "Шакал старший", 1, false, createLore(DiamondMob.getClassStats(), cooldown)));
            case "ObsidianMob" -> inventory.setItem(20, createGuiItem(Material.BARRIER, "Мазохист", 1, false, createLore(ObsidianMob.getClassStats(), cooldown)));
            case "BedrockMob" -> inventory.setItem(21, createGuiItem(Material.BARRIER, "Хороший парень", 1, false, createLore(BedrockMob.getClassStats(), cooldown)));
        }
    }

    @Override
    public void handleClick(Player player, int slot) {
        GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
        switch (slot){
            case 10 -> gamePlayer.spawnBlockMob(new GlassMob(enemy.getSpawnLocation(), enemy.getTargetLocation(), team));
            case 11 -> gamePlayer.spawnBlockMob(new SandMob(enemy.getSpawnLocation(), enemy.getTargetLocation(), team));
            case 12 -> gamePlayer.spawnBlockMob(new DirtMob(enemy.getSpawnLocation(), enemy.getTargetLocation(), team));
            case 13 -> gamePlayer.spawnBlockMob(new OakMob(enemy.getSpawnLocation(), enemy.getTargetLocation(), team));
            case 14 -> gamePlayer.spawnBlockMob(new CobblestoneMob(enemy.getSpawnLocation(), enemy.getTargetLocation(), team));
            case 15 -> gamePlayer.spawnBlockMob(new IronMob(enemy.getSpawnLocation(), enemy.getTargetLocation(), team));
            case 16 -> gamePlayer.spawnBlockMob(new GoldMob(enemy.getSpawnLocation(), enemy.getTargetLocation(), team));
            case 19 -> gamePlayer.spawnBlockMob(new DiamondMob(enemy.getSpawnLocation(), enemy.getTargetLocation(), team));
            case 20 -> gamePlayer.spawnBlockMob(new ObsidianMob(enemy.getSpawnLocation(), enemy.getTargetLocation(), team));
            case 21 -> gamePlayer.spawnBlockMob(new BedrockMob(enemy.getSpawnLocation(), enemy.getTargetLocation(), team));
        }
        updateInventory();
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public Inventory getInventory(){
        return inventory;
    }

    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(inventory)) return;
        e.setCancelled(true);
    }

    public void onInventoryDrag(InventoryDragEvent e){
        if (e.getInventory().equals(inventory)) {
            e.setCancelled(true);
        }
    }
}
