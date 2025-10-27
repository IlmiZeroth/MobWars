package org.ilmizeroth.MobWars.game;

import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.ilmizeroth.MobWars.MobWars;
import org.ilmizeroth.MobWars.game.controller.GameController;
import org.ilmizeroth.MobWars.game.player.GamePlayer;
import org.ilmizeroth.MobWars.game.player.PlayerInformation;
import org.ilmizeroth.MobWars.game.controller.PlayerInformationController;
import org.ilmizeroth.MobWars.game.manager.SignManager;

import java.util.*;

public class Game {
    private final Map map;
    private final List<Player> playerList;
    private final GameTeam firstTeam;
    private final GameTeam secondTeam;
    private final List<Location> positions;
    private BossBar bossBar;
    private BukkitTask stageUpdater;
    private int currentStage = 1;
    private int stageTime = 0;
    private final HashMap<Player, GamePlayer> gamePlayers;
    private Scoreboard gameScoreboard;
    private int currentPlayers = 1;

    public Game(final List<Player> playerList, Map map){
        this.gamePlayers = new HashMap<>();
        this.playerList = playerList;
        this.map = map;
        this.positions = map.getPositions();
        this.currentPlayers = playerList.size();
        this.bossBar = Bukkit.createBossBar("[MobWars] загрузка игры", BarColor.YELLOW, BarStyle.SEGMENTED_20);
        playerList.stream().forEach(bossBar::addPlayer);

        this.gameScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        List<Player> firstTeamPlayers = new ArrayList<>(playerList.subList(0, (playerList.size()+1)/2));
        List<Player> secondTeamPlayers = new ArrayList<>(playerList.subList((playerList.size()+1)/2, playerList.size()));

        this.firstTeam = new GameTeam(firstTeamPlayers, map.getFirstTeamSpawn(), map.getFirstTeamBase());
        this.secondTeam = new GameTeam(secondTeamPlayers, map.getSecondTeamSpawn(), map.getSecondTeamBase());

        this.firstTeam.setEnemy(secondTeam);
        this.secondTeam.setEnemy(firstTeam);

        for(Player p: firstTeamPlayers){
            gamePlayers.put(p, new GamePlayer(p, 10, 1, firstTeam, secondTeam));
        }
        for(Player p: secondTeamPlayers){
            gamePlayers.put(p, new GamePlayer(p, 10, 1, secondTeam, firstTeam));
        }

        changeSigns(map);
        prepareGame();
        GameController.addGame(map, this);

        stageUpdater = new BukkitRunnable(){
            @Override
            public void run() {
                if(isCancelled()) return;
                bossBar.setTitle("Текущая волна: " + currentStage);
                bossBar.setProgress((double) (stageTime)/30);
                stageTime++;
                if(stageTime == 30){
                    stageTime = 0;
                    currentStage+=1;
                    for(GamePlayer player: getGamePlayers().values()){
                        player.payDay();
                        if(GameController.getGameWithPlayer(player.getPlayer()).getCurrentStage() >= 30){
                            player.setSummonRate(100);
                        }else{
                            player.setSummonRate(10);
                        }
                    }
                    if(currentStage == 30){
                        for(Player p: playerList){
                            p.sendTitle( ChatColor.DARK_RED+"Апокалипсис", ChatColor.RED+"↓ КД призыва "+ ChatColor.GREEN + "Количество вызовов ↑");
                            p.playSound(p, Sound.ENTITY_GHAST_SCREAM, 100, 100);
                            gamePlayers.get(p).setSummonRate(100);
                            gamePlayers.get(p).setTimeMultiplier(gamePlayers.get(p).getTimeMultiplier() - 0.25);
                        }
                    }
                }
            }
        }.runTaskTimer(MobWars.getInstance(), 0, 20);
    }
    public int getCurrentStage(){
        return currentStage;
    }
    public boolean leave(Player player){
        if(!playerList.contains(player)) return false;
        playerList.remove(player);
        currentPlayers--;
        bossBar.removePlayer(player);
        if(player != null) {
            returnInventory(player);
            returnToLocation(player);
            returnGameMode(player);
        }
        if(firstTeam.contains(player)) firstTeam.remove(player);
        if(secondTeam.contains(player)) secondTeam.remove(player);
        if(gamePlayers.containsKey(player)) gamePlayers.get(player).leave();
        checkEndGame();
        return true;
    }
    public void playerDeath(Player player){
        player.setGameMode(GameMode.SPECTATOR);
        final int[] timeToReview = {10};
        new BukkitRunnable() {
            @Override
            public void run() {
                if(isCancelled()) return;
                player.sendTitle(ChatColor.RED+"MobWars", String.format("§aДо воскрешения §c%d §aсекунд", timeToReview[0]), 0, 20, 0);
                timeToReview[0]--;
                if(timeToReview[0] == 0){
                    player.setGameMode(GameMode.ADVENTURE);
                    if(firstTeam.contains(player)) player.teleport(firstTeam.getTargetLocation());
                    if(secondTeam.contains(player)) player.teleport(secondTeam.getTargetLocation());
                    if(gamePlayers.get(player).isDoubleJump()) player.setAllowFlight(true);
                    cancel();
                }
            }
        }.runTaskTimer(MobWars.getInstance(), 0, 20L);
    }
    public void checkEndGame(){
        if(firstTeam.getTeamList().isEmpty()) endTheGame();
        if(secondTeam.getTeamList().isEmpty()) endTheGame();
    }

    private void endTheGame(){
        stageUpdater.cancel();
        if(firstTeam.getTeamList().isEmpty()){
            Iterator<Player> iterator = secondTeam.getTeamList().iterator();
            while (iterator.hasNext()) {
                Player player = iterator.next();
                iterator.remove(); // Сначала удаляем из списка
                player.sendTitle(ChatColor.GREEN+"MobWars", ChatColor.DARK_AQUA+"Поздравляю, вы победили!");
                getGamePlayers().get(player).leave();
                leave(player);
            }
        }
        else if(secondTeam.getTeamList().isEmpty()){
                Iterator<Player> iterator = firstTeam.getTeamList().iterator();
                while (iterator.hasNext()) {
                    Player player = iterator.next();
                    iterator.remove(); // Сначала удаляем из списка
                    player.sendTitle( ChatColor.GREEN+"MobWars", ChatColor.DARK_AQUA+"Поздравляю, вы победили!");
                    getGamePlayers().get(player).leave();
                    leave(player);
                }
            }
        this.map.setBusy(false);
        GameController.removeGame(this.map);
        SignManager.updateSignPlayers(this.map, 0);
    }
    private void returnInventory(Player player){
        Inventory playerInventory = player.getInventory();
        ItemStack[] oldInventory = PlayerInformationController.getInformation(player.getUniqueId()).getPlayerInventory();
        playerInventory.setContents(oldInventory);
    }
    private void returnToLocation(Player player){
        Location newLocation = PlayerInformationController.getInformation(player.getUniqueId()).getPlayerLocation();
        player.teleport(newLocation);
    }
    private void returnGameMode(Player player){
        player.setGameMode(PlayerInformationController.getInformation(player.getUniqueId()).getGameMode());
    }
    public Scoreboard prepareScoreboard(Scoreboard gameScoreboard){
        if(gameScoreboard == null) return null;
        Team redTeam = gameScoreboard.registerNewTeam("Red");
        Team blueTeam = gameScoreboard.registerNewTeam("Blue");

        redTeam.setAllowFriendlyFire(false);
        blueTeam.setAllowFriendlyFire(false);

        // Настраиваем отображение
        redTeam.setColor(ChatColor.RED); // Ники красные
        blueTeam.setColor(ChatColor.BLUE); // Ники синие

        redTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS); // Ники всегда видны
        blueTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);

        for(Player p: playerList){
            if(firstTeam.contains(p)) {
                redTeam.addEntry(p.getName());
            }
            else {
                blueTeam.addEntry(p.getName());
            }
        }
        return gameScoreboard;
    }

    private void prepareGame(){
        Location firstSpawnPoint = map.getFirstTeamBase();
        Location secondSpawnPoint = map.getSecondTeamBase();

        for(Player p: playerList){
            PlayerInformationController.saveInformationToFile(p.getUniqueId(), new PlayerInformation(p.getUniqueId(), p.getInventory(), p.getLocation(), p.getGameMode()));
            PlayerInformation.setGameInventory(p);
            p.setHealth(20);
            p.setFoodLevel(20);
            p.setSaturation(20);
            p.setGameMode(GameMode.ADVENTURE);
            p.setScoreboard(gameScoreboard);
            if(firstTeam.contains(p)) {
                p.teleport(firstSpawnPoint);
            }
            else {
                p.teleport(secondSpawnPoint);
            }
        }
    }

    private void changeSigns(Map map) {
        map.setBusy(true);
        SignManager.updateSignPlayers(map, currentPlayers);
    }
    public List<Player> getPlayerList(){
        return playerList;
    }
    public HashMap<Player, GamePlayer> getGamePlayers(){
        return gamePlayers;
    }
    public void openGUI(Player player){
        if(playerList.contains(player)){
            gamePlayers.get(player).getTeamGUI().openInventory(player);
        }
    }
    public void openWeaponGUI(Player player){
        if(playerList.contains(player)){
            gamePlayers.get(player).getWeaponGUI().openInventory(player);
        }
    }
}