package org.giperfire.mobWars.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.giperfire.mobWars.MobWars;
import org.giperfire.mobWars.gui.ChangeWeaponGUI;
import org.giperfire.mobWars.gui.ChestGUI;
import org.giperfire.mobWars.gui.GameTeamGUI;
import org.giperfire.mobWars.mob.BlockMob;
import org.giperfire.mobWars.mob.MobController;

import java.util.HashMap;
import java.util.List;

public class GamePlayer {
    private final Player player;
    private double playerBalance;
    private double playerPayDay;
    private Scoreboard gameScoreboard;
    private final GameTeam team;
    private final GameTeam enemyTeam;
    private final ChestGUI summonGUI;
    private final ChestGUI weaponGUI;
    private final Objective objective;
    private final BukkitTask updaterTask;
    private WeaponProgressor weaponProgressor;
    private ArmorProgressor armorProgressor;
    private BowProgressor bowProgressor;
    private boolean isSpeedBoosted;
    private boolean isDoubleJump;
    private int summonRate;
    private final HashMap<Class, Integer> blockMobCooldowns = (HashMap<Class, Integer>) MobController.getAllBlockMobsCooldown().clone();
    private final HashMap<Class, Integer> blockMobCounts = (HashMap<Class, Integer>) MobController.getAllBlockMobs().clone();
    private final List<Class> blockHierarchy = MobController.getMobsHierarchy();
    private double timeMultiplier = 1;
    public GamePlayer(Player player, double balance, double payDay, GameTeam team, GameTeam enemyTeam) {
        this.player = player;
        this.playerBalance = 10;
        this.playerPayDay = 20;
        this.team = team;
        this.summonRate = 10;
        this.enemyTeam = enemyTeam;
        this.weaponProgressor = WeaponProgressor.STICK;
        this.armorProgressor = ArmorProgressor.LEATHER;
        this.bowProgressor = BowProgressor.NONE;
        this.isSpeedBoosted = false;
        this.isDoubleJump = false;
        this.summonGUI = new GameTeamGUI(player, team, enemyTeam);
        this.weaponGUI = new ChangeWeaponGUI(player);
        this.gameScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = gameScoreboard.registerNewObjective("Stats_" + player.getName(), "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.objective.setDisplayName("§6MobWars"); // Установите название

        new BukkitRunnable() {

            @Override
            public void run() {
                if (isCancelled()) return;
                try {
                    gameScoreboard = GameController.getGameWithPlayer(player).prepareScoreboard(gameScoreboard);
                    player.setScoreboard(gameScoreboard);
                    cancel();
                } catch (Exception e) {

                }
            }
        }.runTaskTimer(MobWars.getInstance(), 20, 20);
        updaterTask = new BukkitRunnable(){

            @Override
            public void run() {
                if(isCancelled()) return;
                updateScoreboard();
                int updateCount = (GameController.getGameWithPlayer(player).getCurrentStage()-1)/2;
                for(Class blockClass: blockHierarchy){
                    if(updateCount-- < 0) break;
                    summonGUI.updateItem(blockClass, blockMobCooldowns.get(blockClass), blockMobCounts.get(blockClass));
                    blockMobCooldowns.put(blockClass, blockMobCooldowns.get(blockClass)-1);
                    if(blockMobCooldowns.get(blockClass) <= 0){
                        blockMobCooldowns.put(blockClass, (int) (MobController.getAllBlockMobsCooldown().get(blockClass)*timeMultiplier));
                        if(blockMobCounts.containsKey(blockClass)) {
                            if (blockMobCounts.get(blockClass) < MobController.getAllBlockMobsLimits().get(blockClass)) {
                                blockMobCounts.put(blockClass, blockMobCounts.get(blockClass) + 1);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(MobWars.getInstance(), 0, 20);
    }
    public void payDay(){

        playerBalance += playerPayDay;
        if(timeMultiplier>0.0125) {
            timeMultiplier -= (double) 1 / 80; // Max game time nearly 40 minutes
        }

    }
    public void setPlayerPayDay(double payDay){
        this.playerPayDay = payDay;
    }
    public double getPlayerPayDay(){
        return playerPayDay;
    }
    public int getSummonRate(){
        return summonRate;
    }
    public void setSummonRate(int summonRate){
        this.summonRate = summonRate;
    }
    public double getPlayerBalance(){
        return playerBalance;
    }
    public void setPlayerBalance(double balance){
        this.playerBalance = balance;
    }
    public ChestGUI getTeamGUI(){
        return summonGUI;
    }
    public Player getPlayer(){
        return player;
    }
    public ChestGUI getWeaponGUI(){
        return weaponGUI;
    }
    public ArmorProgressor getArmorProgressor(){
        return armorProgressor;
    }
    public WeaponProgressor getWeaponProgressor(){
        return weaponProgressor;
    }
    public BowProgressor getBowProgressor(){
        return bowProgressor;
    }

    public void setArmorProgressor(ArmorProgressor armorProgressor) {
        this.armorProgressor = armorProgressor;
    }
    public void setWeaponProgressor(WeaponProgressor weaponProgressor){
        this.weaponProgressor = weaponProgressor;
    }
    public void setBowProgressor(BowProgressor bowProgressor){
        this.bowProgressor = bowProgressor;
    }
    public void setDoubleJump(boolean state){
        isDoubleJump = state;
        if(state) {
            PotionEffect strengthEffect = new PotionEffect(PotionEffectType.STRENGTH, -1, 2);
            player.addPotionEffect(strengthEffect);
        }
        else{
            player.removePotionEffect(PotionEffectType.STRENGTH);
        }
    }
    public void setSpeedBoosted(boolean state){
        isSpeedBoosted = state;
        if(state) {
            PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, -1, 2);
            player.addPotionEffect(speedEffect);
        }
        else{
            player.removePotionEffect(PotionEffectType.SPEED);
        }
    }
    public boolean isSpeedBoosted() {
        return isSpeedBoosted;
    }
    public boolean isDoubleJump(){
        return isDoubleJump;
    }
    public void setTimeMultiplier(double timeMultiplier){
        this.timeMultiplier = timeMultiplier;
    }
    public double getTimeMultiplier(){
        return timeMultiplier;
    }
    public void updateScoreboard(){
        for (String entry : gameScoreboard.getEntries()) {
            gameScoreboard.resetScores(entry);
        }

        Score zeroth = objective.getScore(" ");
        Score first = objective.getScore(String.format("§9Баланс: §f%.0f⛃", playerBalance));
        Score second = objective.getScore(String.format("§2Доход: §f+%.0f☻", playerPayDay));
        Score third = objective.getScore(String.format("§2Призывов: §f+%d☠",summonRate));

        Score fourth = objective.getScore(String.format("§6База противника: §f%d⚔", 100));
        if (enemyTeam != null) {
            fourth = objective.getScore(String.format("§6База противника: §f%d⚔", enemyTeam.getBaseHP()));
        }

        Score fifth = objective.getScore("");
        Score sixth = objective.getScore(String.format("§cЗдоровье: §f%d❤", team.getBaseHP()));

        zeroth.setScore(7);
        first.setScore(6);
        second.setScore(5);
        third.setScore(4);
        fourth.setScore(3);
        fifth.setScore(2);
        sixth.setScore(1);

        player.setScoreboard(gameScoreboard);
        }
    public boolean spawnBlockMob(BlockMob blockMob){
        if (canSpawn(blockMob)){
            this.playerBalance -= blockMob.getStats().getCost();
            blockMobCounts.put(blockMob.getClass(), blockMobCounts.get(blockMob.getClass())-1);
            blockMob.spawnMob();
            summonRate--;
            playerPayDay += blockMob.getStats().getPaydayIncrease();
            summonGUI.updateItem(blockMob.getClass(), blockMobCooldowns.get(blockMob.getClass()), blockMobCounts.get(blockMob.getClass()));
            return true;
        }
        return false;
    }
    public void leave(){
        updaterTask.cancel();
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        setSpeedBoosted(false);
        setDoubleJump(false);
    }
    private boolean canSpawn(BlockMob blockMob){
        if(blockMobCooldowns.containsKey(blockMob.getClass())){
            if(blockMobCooldowns.get(blockMob.getClass()) <= 1 || blockMobCounts.get(blockMob.getClass()) > 0){
                if(this.playerBalance < blockMob.getStats().getCost()){
                    return false;
                } else {
                    if(this.summonRate > 0) {
                        if((GameController.getGameWithPlayer(player).getCurrentStage()-1)/2 >=
                                blockHierarchy.indexOf(blockMob.getClass())) {
                            return true;
                        }
                    }
                }
            }
        }
        else{
            if(this.playerBalance < blockMob.getStats().getCost()){
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
