package org.ilmizeroth.MobWars.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ilmizeroth.MobWars.game.Game;
import org.ilmizeroth.MobWars.game.controller.GameController;
import org.ilmizeroth.MobWars.game.player.GamePlayer;
import org.ilmizeroth.MobWars.game.progressor.ArmorProgressor;
import org.ilmizeroth.MobWars.game.progressor.BowProgressor;
import org.ilmizeroth.MobWars.game.progressor.WeaponProgressor;
import org.ilmizeroth.MobWars.handler.GUIHandler;

import java.util.ArrayList;

public class ChangeWeaponGUI implements ChestGUI{
    private final Player player;
    private final Inventory inventory;
    private static final ItemStack STONE_SWORD;
    private static final ItemStack IRON_SWORD;
    private static final ItemStack NETHER_SWORD;
    private static final ItemStack MACE;
    private static final ItemStack CHAINMAIL;
    private static final ItemStack IRON;
    private static final ItemStack DIAMOND;
    private static final ItemStack NETHER;
    private static final ItemStack BOW;
    private static final ItemStack CROSSBOW;
    private static final ItemStack POWERCROSSBOW;
    private static final ItemStack POWERBOW;
    private static final ItemStack SPEED;
    private static final ItemStack DOUBLE_JUMP;
    private static final int STONE_SWORD_COST = 100;
    private static final int IRON_SWORD_COST = 500;
    private static final int NETHER_SWORD_COST = 2000;
    private static final int MACE_COST = 4000;

    private static final int CHAINMAIL_COST = 500;
    private static final int IRON_COST = 750;
    private static final int DIAMOND_COST = 1000;
    private static final int NETHER_COST = 1500;

    private static final int BOW_COST = 100;
    private static final int CROSS_BOW_COST = 500;
    private static final int POWER_CROSS_BOW_COST = 1000;
    private static final int POWER_BOW_COST = 3000;

    private static final int SPEED_COST = 2000;
    private static final int DOUBLE_JUMP_COST = 5000;

    public ChangeWeaponGUI(Player player){
        this.player = player;
        this.inventory = createInventory();
    }
    static{
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§9Стоимость: §f%d⛃");

        STONE_SWORD = new ItemStack(Material.STONE_SWORD);
        ItemMeta stoneSwordMeta = STONE_SWORD.getItemMeta();
        stoneSwordMeta.setItemName("Затычка");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", STONE_SWORD_COST));
        stoneSwordMeta.setLore(lore);
        stoneSwordMeta.setUnbreakable(true);
        STONE_SWORD.setItemMeta(stoneSwordMeta);

        IRON_SWORD = new ItemStack(Material.IRON_SWORD);
        ItemMeta ironSwordMeta = IRON_SWORD.getItemMeta();
        ironSwordMeta.setItemName("Железный прут");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", IRON_SWORD_COST));
        ironSwordMeta.setLore(lore);
        ironSwordMeta.setUnbreakable(true);
        ironSwordMeta.addEnchant(Enchantment.SHARPNESS, 10, true);
        IRON_SWORD.setItemMeta(ironSwordMeta);

        NETHER_SWORD = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta netherSwordMeta = NETHER_SWORD.getItemMeta();
        netherSwordMeta.setItemName("Пронзатель");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", NETHER_SWORD_COST));
        netherSwordMeta.setLore(lore);
        netherSwordMeta.setUnbreakable(true);
        netherSwordMeta.addEnchant(Enchantment.SHARPNESS, 10, true);
        netherSwordMeta.addEnchant(Enchantment.SWEEPING_EDGE, 10, true);
        NETHER_SWORD.setItemMeta(netherSwordMeta);

        MACE = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta maceMeta = MACE.getItemMeta();
        maceMeta.setItemName("Оружие сына *****");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", MACE_COST));
        maceMeta.setLore(lore);
        maceMeta.setUnbreakable(true);
        maceMeta.addEnchant(Enchantment.SHARPNESS, 20, true);
        maceMeta.addEnchant(Enchantment.SWEEPING_EDGE, 20, true);
        MACE.setItemMeta(maceMeta);

        //Armor
        CHAINMAIL = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        ItemMeta chainmailMeta = CHAINMAIL.getItemMeta();
        chainmailMeta.setItemName("Комплект кольчужной брони");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", CHAINMAIL_COST));
        chainmailMeta.setLore(lore);
        chainmailMeta.setUnbreakable(true);
        chainmailMeta.setEnchantmentGlintOverride(true);
        CHAINMAIL.setItemMeta(chainmailMeta);

        IRON = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta ironMeta = IRON.getItemMeta();
        ironMeta.setItemName("Комплект железной брони");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", IRON_COST));
        ironMeta.setLore(lore);
        ironMeta.setUnbreakable(true);
        ironMeta.setEnchantmentGlintOverride(true);
        IRON.setItemMeta(ironMeta);

        DIAMOND = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta diamondMeta = DIAMOND.getItemMeta();
        diamondMeta.setItemName("Комплект алмазной брони");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", DIAMOND_COST));
        diamondMeta.setLore(lore);
        diamondMeta.setUnbreakable(true);
        diamondMeta.setEnchantmentGlintOverride(true);
        DIAMOND.setItemMeta(diamondMeta);

        NETHER = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemMeta netherMeta = NETHER.getItemMeta();
        netherMeta.setItemName("Комплект незеритовой брони");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", NETHER_COST));
        netherMeta.setLore(lore);
        netherMeta.setUnbreakable(true);
        netherMeta.setEnchantmentGlintOverride(true);
        NETHER.setItemMeta(netherMeta);

        //Bow
        BOW = new ItemStack(Material.BOW);
        ItemMeta bowMeta = BOW.getItemMeta();
        bowMeta.setItemName("Потрёпанный лук");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", BOW_COST));
        bowMeta.setLore(lore);
        bowMeta.setUnbreakable(true);
        bowMeta.addEnchant(Enchantment.INFINITY, 1, true);
        BOW.setItemMeta(bowMeta);

        CROSSBOW = new ItemStack(Material.CROSSBOW);
        ItemMeta crossbowMeta = CROSSBOW.getItemMeta();
        crossbowMeta.setItemName("Крутой арбалет");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", CROSS_BOW_COST));
        crossbowMeta.setLore(lore);
        crossbowMeta.setUnbreakable(true);
        crossbowMeta.addEnchant(Enchantment.INFINITY, 1, true);
        crossbowMeta.addEnchant(Enchantment.MULTISHOT, 3, true);
        crossbowMeta.addEnchant(Enchantment.POWER, 3, true);
        CROSSBOW.setItemMeta(crossbowMeta);

        POWERCROSSBOW = new ItemStack(Material.CROSSBOW);
        ItemMeta powercrossbowMeta = POWERCROSSBOW.getItemMeta();
        powercrossbowMeta.setItemName("Имбалансный арбалет");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", POWER_CROSS_BOW_COST));
        powercrossbowMeta.setLore(lore);
        powercrossbowMeta.setUnbreakable(true);
        powercrossbowMeta.addEnchant(Enchantment.INFINITY, 1, true);
        powercrossbowMeta.addEnchant(Enchantment.MULTISHOT, 10, true);
        powercrossbowMeta.addEnchant(Enchantment.PIERCING, 5, true);
        powercrossbowMeta.addEnchant(Enchantment.POWER, 10, true);
        POWERCROSSBOW.setItemMeta(powercrossbowMeta);

        POWERBOW = new ItemStack(Material.BOW);
        ItemMeta powerbowMeta = POWERBOW.getItemMeta();
        powerbowMeta.setItemName("Ебануто имбалансный лук");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", POWER_BOW_COST));
        powerbowMeta.setLore(lore);
        powerbowMeta.setUnbreakable(true);
        powerbowMeta.addEnchant(Enchantment.INFINITY, 1, true);
        powerbowMeta.addEnchant(Enchantment.PIERCING, 5, true);
        powerbowMeta.addEnchant(Enchantment.POWER, 20, true);
        powerbowMeta.addEnchant(Enchantment.MULTISHOT, 3, true);
        POWERBOW.setItemMeta(powerbowMeta);

        SPEED = new ItemStack(Material.ENDER_EYE);
        ItemMeta speedMeta = SPEED.getItemMeta();
        speedMeta.setItemName("Улучшение: Скорость 2");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", SPEED_COST));
        speedMeta.setLore(lore);
        SPEED.setItemMeta(speedMeta);

        DOUBLE_JUMP = new ItemStack(Material.NETHER_STAR);
        ItemMeta doubleJumpMeta = DOUBLE_JUMP.getItemMeta();
        doubleJumpMeta.setItemName("Улучшение: Сила");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", DOUBLE_JUMP_COST));
        doubleJumpMeta.setLore(lore);
        DOUBLE_JUMP.setItemMeta(doubleJumpMeta);
    }

    @Override
    public Inventory createInventory() {
        Inventory inv = Bukkit.createInventory(null, 54, "Магазин полезных штучек");
        initializeItems(inv);
        return inv;
    }

    @Override
    public void initializeItems(Inventory inv) {
        int weaponProgress = 0;
        int armorProgress = 0;
        int bowProgress = 0;
        Game game = null;
        try {
            game = GameController.getGameWithPlayer(player);
            weaponProgress = game.getGamePlayers().get(player).getWeaponProgressor().ordinal();
            armorProgress = game.getGamePlayers().get(player).getArmorProgressor().ordinal();
            bowProgress = game.getGamePlayers().get(player).getBowProgressor().ordinal();
        }catch (Exception e){
        }
        // Weapon
        ItemStack nstoneSword = new ItemStack(weaponProgress >= 0 ? Material.STONE_SWORD : Material.BARRIER);
        nstoneSword.setItemMeta(STONE_SWORD.getItemMeta());

        ItemStack nironSword = new ItemStack(weaponProgress >= 1 ? Material.IRON_SWORD : Material.BARRIER);
        nironSword.setItemMeta(IRON_SWORD.getItemMeta());

        ItemStack nnetherSword = new ItemStack(weaponProgress >= 2 ? Material.DIAMOND_SWORD : Material.BARRIER);
        nnetherSword.setItemMeta(NETHER_SWORD.getItemMeta());

        ItemStack nmace = new ItemStack(weaponProgress >= 3 ? Material.NETHERITE_SWORD : Material.BARRIER);
        nmace.setItemMeta(MACE.getItemMeta());

        //Armor
        ItemStack nchainmail = new ItemStack(armorProgress >= 0 ? Material.CHAINMAIL_CHESTPLATE : Material.BARRIER);
        nchainmail.setItemMeta(CHAINMAIL.getItemMeta());

        ItemStack niron = new ItemStack(armorProgress >= 1 ? Material.IRON_CHESTPLATE : Material.BARRIER);
        niron.setItemMeta(IRON.getItemMeta());

        ItemStack ndiamond = new ItemStack(armorProgress >= 2 ? Material.DIAMOND_CHESTPLATE : Material.BARRIER);
        ndiamond.setItemMeta(DIAMOND.getItemMeta());

        ItemStack nnether = new ItemStack(armorProgress >= 3 ? Material.NETHERITE_CHESTPLATE : Material.BARRIER);
        nnether.setItemMeta(NETHER.getItemMeta());

        //Bow
        ItemStack nbow = new ItemStack(bowProgress >= 0 ? Material.BOW : Material.BARRIER);
        nbow.setItemMeta(BOW.getItemMeta());

        ItemStack ncrossbow = new ItemStack(bowProgress >= 1 ? Material.CROSSBOW : Material.BARRIER);
        ncrossbow.setItemMeta(CROSSBOW.getItemMeta());

        ItemStack npowercrossbow = new ItemStack(bowProgress >= 2 ? Material.CROSSBOW : Material.BARRIER);
        npowercrossbow.setItemMeta(POWERCROSSBOW.getItemMeta());

        ItemStack npowerbow = new ItemStack(bowProgress >= 3 ? Material.BOW : Material.BARRIER);
        npowerbow.setItemMeta(POWERBOW.getItemMeta());

        ItemStack nspeed = new ItemStack(game != null ? game.getGamePlayers().get(player).isSpeedBoosted() && bowProgress >= 3 ? Material.BARRIER : Material.ENDER_EYE : Material.BARRIER);
        nspeed.setItemMeta(SPEED.getItemMeta());

        ItemStack ndoubleJump = new ItemStack(game != null ? game.getGamePlayers().get(player).isDoubleJump() && weaponProgress >= 3 ? Material.BARRIER : Material.NETHER_STAR : Material.BARRIER);
        ndoubleJump.setItemMeta(DOUBLE_JUMP.getItemMeta());

        for(int i = 0; i<54; i++){
            inv.setItem(i, createGuiItem(Material.BLACK_STAINED_GLASS_PANE, " ", 1, false));
        }

        inv.setItem(10, nstoneSword);
        inv.setItem(11, nironSword);
        inv.setItem(12, nnetherSword);
        inv.setItem(13, nmace);

        inv.setItem(37, nbow);
        inv.setItem(38, ncrossbow);
        inv.setItem(39, npowercrossbow);
        inv.setItem(40, npowerbow);

        inv.setItem(15, nchainmail);
        inv.setItem(24, niron);
        inv.setItem(33, ndiamond);
        inv.setItem(42, nnether);

        inv.setItem(26, nspeed);
        inv.setItem(35, ndoubleJump);
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

    }

    @Override
    public void handleClick(Player player, int slot) {
//        inv.setItem(10, stoneSword);
//        inv.setItem(11, ironSword);
//        inv.setItem(12, netherSword);
//        inv.setItem(13, mace);
//
//        inv.setItem(37, bow);
//        inv.setItem(38, crossbow);
//        inv.setItem(39, powercrossbow);
//        inv.setItem(40, powerbow);
//
//        inv.setItem(15, chainmail);
//        inv.setItem(24, iron);
//        inv.setItem(33, diamond);
//        inv.setItem(42, nether);
//
//        inv.setItem(26, speed);
//        inv.setItem(35, doubleJump);
        switch (slot){
            case 10 -> handleStoneSword();
            case 11 -> handleIronSword();
            case 12 -> handleNetherSword();
            case 13 -> handleMace();
            case 37 -> handleBow();
            case 38 -> handleCrossBow();
            case 39 -> handlePowerCrossBow();
            case 40 -> handlePowerBow();
            case 15 -> handleChainmail();
            case 24 -> handleIron();
            case 33 -> handleDiamond();
            case 42 -> handleNether();
            case 26 -> handleSpeedBoost();
            case 35 -> handleDoubleJump();
        }
        updateInventory();
    }
    private void handleSpeedBoost(){
        try{
            GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
            if(!gamePlayer.isSpeedBoosted()){
                if(gamePlayer.getBowProgressor().equals(BowProgressor.POWERED_BOW)) {
                    if (gamePlayer.getPlayerBalance() >= SPEED_COST) {
                        gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance() - SPEED_COST);
                        gamePlayer.setSpeedBoosted(true);
                    }
                }else player.sendMessage("§c[MobWars] §7Для начала прокачайте лук на максимум!");
            }
        }
        catch (Exception e){

        }
    }
    private void handleDoubleJump(){
        try{
            GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
            if(!gamePlayer.isDoubleJump()){
                if(gamePlayer.getWeaponProgressor().equals(WeaponProgressor.MACE)) {
                    if (gamePlayer.getPlayerBalance() >= DOUBLE_JUMP_COST) {
                        gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance() - DOUBLE_JUMP_COST);
                        gamePlayer.setDoubleJump(true);
                    }
                }
                else player.sendMessage("§c[MobWars] §7Для начала прокачайте меч на максимум!");
            }
        }
        catch (Exception e){

        }
    }
    private void handleStoneSword(){
        try{
            GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
            if(gamePlayer.getWeaponProgressor().equals(WeaponProgressor.STICK)){
                if(gamePlayer.getPlayerBalance() >= STONE_SWORD_COST){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()- STONE_SWORD_COST);
                    gamePlayer.setWeaponProgressor(WeaponProgressor.STONE_SWORD);
                    player.getInventory().setItem(0, STONE_SWORD);
                    initializeItems(inventory);
                }
            }
        }
        catch (Exception e){

        }
    }
    private void handleIronSword(){
        try{
            GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
            if(gamePlayer.getWeaponProgressor().equals(WeaponProgressor.STONE_SWORD)){
                if(gamePlayer.getPlayerBalance() >= IRON_SWORD_COST){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()- IRON_SWORD_COST);
                    gamePlayer.setWeaponProgressor(WeaponProgressor.IRON_SWORD);
                    player.getInventory().setItem(0, IRON_SWORD);
                    initializeItems(inventory);
                }
            }
        }
        catch (Exception e){

        }
    }
    private void handleNetherSword(){
        try{
            GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
            if(gamePlayer.getWeaponProgressor().equals(WeaponProgressor.IRON_SWORD)){
                if(gamePlayer.getPlayerBalance() >= NETHER_SWORD_COST){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()- NETHER_SWORD_COST);
                    gamePlayer.setWeaponProgressor(WeaponProgressor.NETHERITE_SWORD);
                    player.getInventory().setItem(0, NETHER_SWORD);
                    initializeItems(inventory);
                }
            }
        }
        catch (Exception e){

        }
    }
    private void handleMace(){
        try{
            GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
            if(gamePlayer.getWeaponProgressor().equals(WeaponProgressor.NETHERITE_SWORD)){
                if(gamePlayer.getPlayerBalance() >= MACE_COST){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()- MACE_COST);
                    gamePlayer.setWeaponProgressor(WeaponProgressor.MACE);
                    player.getInventory().setItem(0, MACE);
                    initializeItems(inventory);
                }
            }
        }
        catch (Exception e){

        }
    }
    private void handleChainmail(){
        try{
            GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
            if(gamePlayer.getArmorProgressor().equals(ArmorProgressor.LEATHER)){
                if(gamePlayer.getPlayerBalance() >= CHAINMAIL_COST){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()- CHAINMAIL_COST);
                    gamePlayer.setArmorProgressor(ArmorProgressor.CHAINMAIL);

                    ItemStack chainHat = new ItemStack(Material.CHAINMAIL_HELMET);
                    ItemMeta chainHatMeta = chainHat.getItemMeta();
                    chainHatMeta.setUnbreakable(true);
                    chainHat.setItemMeta(chainHatMeta);

                    ItemStack chainChest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                    ItemMeta chainChestMeta = chainChest.getItemMeta();
                    chainChestMeta.setUnbreakable(true);
                    chainChest.setItemMeta(chainChestMeta);

                    ItemStack chainLeg = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                    ItemMeta chainLegMeta = chainLeg.getItemMeta();
                    chainLegMeta.setUnbreakable(true);
                    chainLeg.setItemMeta(chainLegMeta);

                    ItemStack chainBoot = new ItemStack(Material.CHAINMAIL_BOOTS);
                    ItemMeta chainBootMeta = chainBoot.getItemMeta();
                    chainBootMeta.setUnbreakable(true);
                    chainBoot.setItemMeta(chainBootMeta);
                    player.getInventory().setItem(39, chainHat);
                    player.getInventory().setItem(38, chainChest);
                    player.getInventory().setItem(37, chainLeg);
                    player.getInventory().setItem(36, chainBoot);

                    initializeItems(inventory);
                }
            }
        }
        catch (Exception e){

        }
    }
    private void handleIron(){
        try{
            GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
            if(gamePlayer.getArmorProgressor().equals(ArmorProgressor.CHAINMAIL)){
                if(gamePlayer.getPlayerBalance() >= IRON_COST){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()- IRON_COST);
                    gamePlayer.setArmorProgressor(ArmorProgressor.IRON);

                    ItemStack chainHat = new ItemStack(Material.IRON_HELMET);
                    ItemMeta chainHatMeta = chainHat.getItemMeta();
                    chainHatMeta.setUnbreakable(true);
                    chainHat.setItemMeta(chainHatMeta);

                    ItemStack chainChest = new ItemStack(Material.IRON_CHESTPLATE);
                    ItemMeta chainChestMeta = chainChest.getItemMeta();
                    chainChestMeta.setUnbreakable(true);
                    chainChest.setItemMeta(chainChestMeta);

                    ItemStack chainLeg = new ItemStack(Material.IRON_LEGGINGS);
                    ItemMeta chainLegMeta = chainLeg.getItemMeta();
                    chainLegMeta.setUnbreakable(true);
                    chainLeg.setItemMeta(chainLegMeta);

                    ItemStack chainBoot = new ItemStack(Material.IRON_BOOTS);
                    ItemMeta chainBootMeta = chainBoot.getItemMeta();
                    chainBootMeta.setUnbreakable(true);
                    chainBoot.setItemMeta(chainBootMeta);
                    player.getInventory().setItem(39, chainHat);
                    player.getInventory().setItem(38, chainChest);
                    player.getInventory().setItem(37, chainLeg);
                    player.getInventory().setItem(36, chainBoot);

                    initializeItems(inventory);
                }
            }
        }
        catch (Exception e){

        }
    }
    private void handleDiamond(){
        try{
            GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
            if(gamePlayer.getArmorProgressor().equals(ArmorProgressor.IRON)){
                if(gamePlayer.getPlayerBalance() >= DIAMOND_COST){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()- DIAMOND_COST);
                    gamePlayer.setArmorProgressor(ArmorProgressor.DIAMOND);

                    ItemStack chainHat = new ItemStack(Material.DIAMOND_HELMET);
                    ItemMeta chainHatMeta = chainHat.getItemMeta();
                    chainHatMeta.setUnbreakable(true);
                    chainHat.setItemMeta(chainHatMeta);

                    ItemStack chainChest = new ItemStack(Material.DIAMOND_CHESTPLATE);
                    ItemMeta chainChestMeta = chainChest.getItemMeta();
                    chainChestMeta.setUnbreakable(true);
                    chainChest.setItemMeta(chainChestMeta);

                    ItemStack chainLeg = new ItemStack(Material.DIAMOND_LEGGINGS);
                    ItemMeta chainLegMeta = chainLeg.getItemMeta();
                    chainLegMeta.setUnbreakable(true);
                    chainLeg.setItemMeta(chainLegMeta);

                    ItemStack chainBoot = new ItemStack(Material.DIAMOND_BOOTS);
                    ItemMeta chainBootMeta = chainBoot.getItemMeta();
                    chainBootMeta.setUnbreakable(true);
                    chainBoot.setItemMeta(chainBootMeta);
                    player.getInventory().setItem(39, chainHat);
                    player.getInventory().setItem(38, chainChest);
                    player.getInventory().setItem(37, chainLeg);
                    player.getInventory().setItem(36, chainBoot);

                    initializeItems(inventory);
                }
            }
        }
        catch (Exception e){

        }
    }
    private void handleNether(){
        try{
            GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
            if(gamePlayer.getArmorProgressor().equals(ArmorProgressor.DIAMOND)){
                if(gamePlayer.getPlayerBalance() >= NETHER_COST){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()- NETHER_COST);
                    gamePlayer.setArmorProgressor(ArmorProgressor.NETHERITE);

                    ItemStack chainHat = new ItemStack(Material.NETHERITE_HELMET);
                    ItemMeta chainHatMeta = chainHat.getItemMeta();
                    chainHatMeta.setUnbreakable(true);
                    chainHat.setItemMeta(chainHatMeta);

                    ItemStack chainChest = new ItemStack(Material.NETHERITE_CHESTPLATE);
                    ItemMeta chainChestMeta = chainChest.getItemMeta();
                    chainChestMeta.setUnbreakable(true);
                    chainChest.setItemMeta(chainChestMeta);

                    ItemStack chainLeg = new ItemStack(Material.NETHERITE_LEGGINGS);
                    ItemMeta chainLegMeta = chainLeg.getItemMeta();
                    chainLegMeta.setUnbreakable(true);
                    chainLeg.setItemMeta(chainLegMeta);

                    ItemStack chainBoot = new ItemStack(Material.NETHERITE_BOOTS);
                    ItemMeta chainBootMeta = chainBoot.getItemMeta();
                    chainBootMeta.setUnbreakable(true);
                    chainBoot.setItemMeta(chainBootMeta);
                    player.getInventory().setItem(39, chainHat);
                    player.getInventory().setItem(38, chainChest);
                    player.getInventory().setItem(37, chainLeg);
                    player.getInventory().setItem(36, chainBoot);
                }
            }
        }
        catch (Exception e){

        }
    }
    private void handleBow(){
        try{
            GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
            if(gamePlayer.getBowProgressor().equals(BowProgressor.NONE)){
                if(gamePlayer.getPlayerBalance() >= BOW_COST){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()- BOW_COST);
                    gamePlayer.setBowProgressor(BowProgressor.BOW);
                    player.getInventory().setItem(1, BOW);
                    player.getInventory().setItem(2, new ItemStack(Material.ARROW));

                    initializeItems(inventory);
                }
            }
        }
        catch (Exception e){

        }
    }
    private void handleCrossBow(){
        try{
            GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
            if(gamePlayer.getBowProgressor().equals(BowProgressor.BOW)){
                if(gamePlayer.getPlayerBalance() >= CROSS_BOW_COST){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()- CROSS_BOW_COST);
                    gamePlayer.setBowProgressor(BowProgressor.CROSSBOW);
                    player.getInventory().setItem(1, CROSSBOW);

                    initializeItems(inventory);
                }
            }
        }
        catch (Exception e){

        }
    }
    private void handlePowerCrossBow(){
        try{
            GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
            if(gamePlayer.getBowProgressor().equals(BowProgressor.CROSSBOW)){
                if(gamePlayer.getPlayerBalance() >= POWER_CROSS_BOW_COST){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()- POWER_CROSS_BOW_COST);
                    gamePlayer.setBowProgressor(BowProgressor.POWERED_CROSSBOW);
                    player.getInventory().setItem(1, POWERCROSSBOW);

                    initializeItems(inventory);
                }
            }
        }
        catch (Exception e){

        }
    }
    private void handlePowerBow(){
        try{
            GamePlayer gamePlayer = GameController.getGameWithPlayer(player).getGamePlayers().get(player);
            if(gamePlayer.getBowProgressor().equals(BowProgressor.POWERED_CROSSBOW)){
                if(gamePlayer.getPlayerBalance() >= POWER_BOW_COST){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()- POWER_BOW_COST);
                    gamePlayer.setBowProgressor(BowProgressor.POWERED_BOW);
                    player.getInventory().setItem(1, POWERBOW);

                    initializeItems(inventory);
                }
            }
        }
        catch (Exception e){

        }
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
