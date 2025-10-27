package org.ilmizeroth.MobWars.game;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.ilmizeroth.MobWars.MobWars;
import org.ilmizeroth.MobWars.game.controller.GameController;
import org.ilmizeroth.MobWars.mob.BlockMob;

import java.util.*;

public class GameTeam {
    private final List<Player> playerList;

    private double timeMultiplier;
    private int baseHP;
    private BukkitTask bukkitTask;
    private Location spawnLocation;
    private Location targetLocation;
    private int randomStaffTimer;

    private GameTeam enemyTeam;

    public GameTeam(List<Player> playerList, Location spawnLoc, Location targetLoc){
        if(playerList == null) playerList = new ArrayList<>();
        this.playerList = playerList;
        this.timeMultiplier = 1;
        this.baseHP = 100;
        this.randomStaffTimer = 60;
        this.spawnLocation = spawnLoc;
        this.targetLocation = targetLoc;

        this.bukkitTask = new BukkitRunnable(){

            @Override
            public void run() {
                if(isCancelled()) return;

                randomStaffTimer--;

                if(randomStaffTimer == 0){
                    everyTakeRandomStaff();
                    randomStaffTimer = 60;
                }

            }
        }.runTaskTimer(MobWars.getInstance(), 0, 20L);
    }
    public void setEnemy(GameTeam enemy){
        this.enemyTeam = enemy;
    }
    public GameTeam getEnemyTeam(){
        return enemyTeam;
    }

    public boolean contains(Player player){
        return this.playerList.contains(player);
    }
    public void remove(Player player){
        playerList.remove(player);
    }
    public List<Player> getTeamList(){
        return playerList;
    }
    public int getBaseHP(){
        return baseHP;
    }

    public boolean getDamage(BlockMob blockMob){
        for(Player player: playerList){
            player.playSound(player, Sound.ENTITY_ENDER_DRAGON_GROWL, 100, 100);
        }
        this.baseHP -= (int) blockMob.getStats().baseDamage();
        if(baseHP <= 0) {
            destroyBase();
            return true;
        }
        return false;
    }
    private void everyTakeRandomStaff(){
        ItemStack potionSlownessF = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionSlownessFMeta = (PotionMeta) potionSlownessF.getItemMeta();
        potionSlownessFMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOWNESS, 300, 1), true);
        potionSlownessF.setItemMeta(potionSlownessFMeta);

        ItemStack potionSlownessS = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionSlownessSMeta = (PotionMeta) potionSlownessS.getItemMeta();
        potionSlownessSMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOWNESS, 600, 2), true);
        potionSlownessS.setItemMeta(potionSlownessSMeta);

        ItemStack potionStrengthF = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionStrengthFMeta = (PotionMeta) potionStrengthF.getItemMeta();
        potionStrengthFMeta.addCustomEffect(new PotionEffect(PotionEffectType.STRENGTH, 600, 1), true);
        potionStrengthF.setItemMeta(potionStrengthFMeta);

        ItemStack potionStrengthS = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionStrengthSMeta = (PotionMeta) potionStrengthS.getItemMeta();
        potionStrengthSMeta.addCustomEffect(new PotionEffect(PotionEffectType.STRENGTH, 600, 2), true);
        potionStrengthS.setItemMeta(potionStrengthSMeta);

        ItemStack[] itemStacks = new ItemStack[] {
                new ItemStack(Material.GOLDEN_APPLE),
                new ItemStack(Material.ENCHANTED_GOLDEN_APPLE),
                new ItemStack(Material.TOTEM_OF_UNDYING),
                potionSlownessF,
                potionSlownessS,
                potionStrengthS,
                potionStrengthF
        };
        Random random = new Random();
        for (Player p: playerList){
            int randomIndex = random.nextInt(itemStacks.length);
            for(int i = 0; i < random.nextInt(1, 3); i++) {
                p.getInventory().addItem(itemStacks[randomIndex]);
            }
        }
    }
    private void destroyBase(){
        bukkitTask.cancel();
        try{
            Iterator<Player> iterator = playerList.iterator();
            while (iterator.hasNext()) {
                Player player = iterator.next();
                iterator.remove(); // Сначала удаляем из списка
                player.sendTitle(ChatColor.RED+"MobWars", ChatColor.GREEN+"Вы проиграли");
                GameController.getGameWithPlayer(player).getGamePlayers().get(player).leave();
                GameController.getGameWithPlayer(player).leave(player);
            }
        }catch (Exception e){}
    }
    public Location getSpawnLocation(){
        return spawnLocation;
    }
    public Location getTargetLocation(){
        return targetLocation;
    }

}
