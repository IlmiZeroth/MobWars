package org.giperfire.mobWars.gui;

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.giperfire.mobWars.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChangeWeaponGUI implements ChestGUI{
    private final Player player;
    private final Inventory inventory;
    private static ItemStack stoneSword;
    private static ItemStack ironSword;
    private static ItemStack netherSword;
    private static ItemStack mace;
    private static ItemStack chainmail;
    private static ItemStack iron;
    private static ItemStack diamond;
    private static ItemStack nether;
    private static ItemStack bow;
    private static ItemStack crossbow;
    private static ItemStack powercrossbow;
    private static ItemStack powerbow;
    private static ItemStack speed;
    private static ItemStack doubleJump;
    private static int stoneSwordCost = 100;
    private static int ironSwordCost = 500;
    private static int netherSwordCost = 2000;
    private static int maceCost = 4000;

    private static int chainmailCost = 500;
    private static int ironCost = 750;
    private static int diamondCost = 1000;
    private static int netherCost = 1500;

    private static int bowCost = 100;
    private static int crossBowCost = 500;
    private static int powerCrossBowCost = 1000;
    private static int powerBowCost = 3000;

    private static int speedCost = 2000;
    private static int doubleJumpCost = 5000;
    public ChangeWeaponGUI(Player player){
        this.player = player;
        this.inventory = createInventory();
    }
    static{
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§9Стоимость: §f%d⛃");

        stoneSword = new ItemStack(Material.STONE_SWORD);
        ItemMeta stoneSwordMeta = stoneSword.getItemMeta();
        stoneSwordMeta.setItemName("Затычка");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", stoneSwordCost));
        stoneSwordMeta.setLore(lore);
        stoneSwordMeta.setUnbreakable(true);
        stoneSword.setItemMeta(stoneSwordMeta);

        ironSword = new ItemStack(Material.IRON_SWORD);
        ItemMeta ironSwordMeta = ironSword.getItemMeta();
        ironSwordMeta.setItemName("Железный прут");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", ironSwordCost));
        ironSwordMeta.setLore(lore);
        ironSwordMeta.setUnbreakable(true);
        ironSwordMeta.addEnchant(Enchantment.SHARPNESS, 10, true);
        ironSword.setItemMeta(ironSwordMeta);

        netherSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta netherSwordMeta = netherSword.getItemMeta();
        netherSwordMeta.setItemName("Пронзатель");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", netherSwordCost));
        netherSwordMeta.setLore(lore);
        netherSwordMeta.setUnbreakable(true);
        netherSwordMeta.addEnchant(Enchantment.SHARPNESS, 10, true);
        netherSwordMeta.addEnchant(Enchantment.SWEEPING_EDGE, 10, true);
        netherSword.setItemMeta(netherSwordMeta);

        mace = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta maceMeta = mace.getItemMeta();
        maceMeta.setItemName("Оружие сына *****");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", maceCost));
        maceMeta.setLore(lore);
        maceMeta.setUnbreakable(true);
        maceMeta.addEnchant(Enchantment.SHARPNESS, 20, true);
        maceMeta.addEnchant(Enchantment.SWEEPING_EDGE, 20, true);
        mace.setItemMeta(maceMeta);

        //Armor
        chainmail = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        ItemMeta chainmailMeta = chainmail.getItemMeta();
        chainmailMeta.setItemName("Комплект кольчужной брони");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", chainmailCost));
        chainmailMeta.setLore(lore);
        chainmailMeta.setUnbreakable(true);
        chainmailMeta.setEnchantmentGlintOverride(true);
        chainmail.setItemMeta(chainmailMeta);

        iron = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta ironMeta = iron.getItemMeta();
        ironMeta.setItemName("Комплект железной брони");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", ironCost));
        ironMeta.setLore(lore);
        ironMeta.setUnbreakable(true);
        ironMeta.setEnchantmentGlintOverride(true);
        iron.setItemMeta(ironMeta);

        diamond = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta diamondMeta = diamond.getItemMeta();
        diamondMeta.setItemName("Комплект алмазной брони");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", diamondCost));
        diamondMeta.setLore(lore);
        diamondMeta.setUnbreakable(true);
        diamondMeta.setEnchantmentGlintOverride(true);
        diamond.setItemMeta(diamondMeta);

        nether = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemMeta netherMeta = nether.getItemMeta();
        netherMeta.setItemName("Комплект незеритовой брони");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", netherCost));
        netherMeta.setLore(lore);
        netherMeta.setUnbreakable(true);
        netherMeta.setEnchantmentGlintOverride(true);
        nether.setItemMeta(netherMeta);

        //Bow
        bow = new ItemStack(Material.BOW);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.setItemName("Потрёпанный лук");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", bowCost));
        bowMeta.setLore(lore);
        bowMeta.setUnbreakable(true);
        bowMeta.addEnchant(Enchantment.INFINITY, 1, true);
        bow.setItemMeta(bowMeta);

        crossbow = new ItemStack(Material.CROSSBOW);
        ItemMeta crossbowMeta = crossbow.getItemMeta();
        crossbowMeta.setItemName("Крутой арбалет");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", crossBowCost));
        crossbowMeta.setLore(lore);
        crossbowMeta.setUnbreakable(true);
        crossbowMeta.addEnchant(Enchantment.INFINITY, 1, true);
        crossbowMeta.addEnchant(Enchantment.MULTISHOT, 3, true);
        crossbowMeta.addEnchant(Enchantment.POWER, 3, true);
        crossbow.setItemMeta(crossbowMeta);

        powercrossbow = new ItemStack(Material.CROSSBOW);
        ItemMeta powercrossbowMeta = powercrossbow.getItemMeta();
        powercrossbowMeta.setItemName("Имбалансный арбалет");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", powerCrossBowCost));
        powercrossbowMeta.setLore(lore);
        powercrossbowMeta.setUnbreakable(true);
        powercrossbowMeta.addEnchant(Enchantment.INFINITY, 1, true);
        powercrossbowMeta.addEnchant(Enchantment.MULTISHOT, 10, true);
        powercrossbowMeta.addEnchant(Enchantment.PIERCING, 5, true);
        powercrossbowMeta.addEnchant(Enchantment.POWER, 10, true);
        powercrossbow.setItemMeta(powercrossbowMeta);

        powerbow = new ItemStack(Material.BOW);
        ItemMeta powerbowMeta = powerbow.getItemMeta();
        powerbowMeta.setItemName("Ебануто имбалансный лук");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", powerBowCost));
        powerbowMeta.setLore(lore);
        powerbowMeta.setUnbreakable(true);
        powerbowMeta.addEnchant(Enchantment.INFINITY, 1, true);
        powerbowMeta.addEnchant(Enchantment.PIERCING, 5, true);
        powerbowMeta.addEnchant(Enchantment.POWER, 20, true);
        powerbowMeta.addEnchant(Enchantment.MULTISHOT, 3, true);
        powerbow.setItemMeta(powerbowMeta);

        speed = new ItemStack(Material.ENDER_EYE);
        ItemMeta speedMeta = speed.getItemMeta();
        speedMeta.setItemName("Улучшение: Скорость 2");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", speedCost));
        speedMeta.setLore(lore);
        speed.setItemMeta(speedMeta);

        doubleJump = new ItemStack(Material.NETHER_STAR);
        ItemMeta doubleJumpMeta = doubleJump.getItemMeta();
        doubleJumpMeta.setItemName("Улучшение: Сила");
        lore.set(0, String.format("§9Стоимость: §f%d⛃", doubleJumpCost));
        doubleJumpMeta.setLore(lore);
        doubleJump.setItemMeta(doubleJumpMeta);
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
        nstoneSword.setItemMeta(stoneSword.getItemMeta());

        ItemStack nironSword = new ItemStack(weaponProgress >= 1 ? Material.IRON_SWORD : Material.BARRIER);
        nironSword.setItemMeta(ironSword.getItemMeta());

        ItemStack nnetherSword = new ItemStack(weaponProgress >= 2 ? Material.DIAMOND_SWORD : Material.BARRIER);
        nnetherSword.setItemMeta(netherSword.getItemMeta());

        ItemStack nmace = new ItemStack(weaponProgress >= 3 ? Material.NETHERITE_SWORD : Material.BARRIER);
        nmace.setItemMeta(mace.getItemMeta());

        //Armor
        ItemStack nchainmail = new ItemStack(armorProgress >= 0 ? Material.CHAINMAIL_CHESTPLATE : Material.BARRIER);
        nchainmail.setItemMeta(chainmail.getItemMeta());

        ItemStack niron = new ItemStack(armorProgress >= 1 ? Material.IRON_CHESTPLATE : Material.BARRIER);
        niron.setItemMeta(iron.getItemMeta());

        ItemStack ndiamond = new ItemStack(armorProgress >= 2 ? Material.DIAMOND_CHESTPLATE : Material.BARRIER);
        ndiamond.setItemMeta(diamond.getItemMeta());

        ItemStack nnether = new ItemStack(armorProgress >= 3 ? Material.NETHERITE_CHESTPLATE : Material.BARRIER);
        nnether.setItemMeta(nether.getItemMeta());

        //Bow
        ItemStack nbow = new ItemStack(bowProgress >= 0 ? Material.BOW : Material.BARRIER);
        nbow.setItemMeta(bow.getItemMeta());

        ItemStack ncrossbow = new ItemStack(bowProgress >= 1 ? Material.CROSSBOW : Material.BARRIER);
        ncrossbow.setItemMeta(crossbow.getItemMeta());

        ItemStack npowercrossbow = new ItemStack(bowProgress >= 2 ? Material.CROSSBOW : Material.BARRIER);
        npowercrossbow.setItemMeta(powercrossbow.getItemMeta());

        ItemStack npowerbow = new ItemStack(bowProgress >= 3 ? Material.BOW : Material.BARRIER);
        npowerbow.setItemMeta(powerbow.getItemMeta());

        ItemStack nspeed = new ItemStack(game != null ? game.getGamePlayers().get(player).isSpeedBoosted() && bowProgress >= 3 ? Material.BARRIER : Material.ENDER_EYE : Material.BARRIER);
        nspeed.setItemMeta(speed.getItemMeta());

        ItemStack ndoubleJump = new ItemStack(game != null ? game.getGamePlayers().get(player).isDoubleJump() && weaponProgress >= 3 ? Material.BARRIER : Material.NETHER_STAR : Material.BARRIER);
        ndoubleJump.setItemMeta(doubleJump.getItemMeta());

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
        GUIListener.openGUI(p, this);
    }

    @Override
    public void closeInventory(Player p) {
        GUIListener.closeGUI(p);
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
                    if (gamePlayer.getPlayerBalance() >= speedCost) {
                        gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance() - speedCost);
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
                    if (gamePlayer.getPlayerBalance() >= doubleJumpCost) {
                        gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance() - doubleJumpCost);
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
                if(gamePlayer.getPlayerBalance() >= stoneSwordCost){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()-stoneSwordCost);
                    gamePlayer.setWeaponProgressor(WeaponProgressor.STONE_SWORD);
                    player.getInventory().setItem(0, stoneSword);
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
                if(gamePlayer.getPlayerBalance() >= ironSwordCost){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()-ironSwordCost);
                    gamePlayer.setWeaponProgressor(WeaponProgressor.IRON_SWORD);
                    player.getInventory().setItem(0, ironSword);
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
                if(gamePlayer.getPlayerBalance() >= netherSwordCost){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()-netherSwordCost);
                    gamePlayer.setWeaponProgressor(WeaponProgressor.NETHERITE_SWORD);
                    player.getInventory().setItem(0, netherSword);
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
                if(gamePlayer.getPlayerBalance() >= maceCost){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()-maceCost);
                    gamePlayer.setWeaponProgressor(WeaponProgressor.MACE);
                    player.getInventory().setItem(0, mace);
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
                if(gamePlayer.getPlayerBalance() >= chainmailCost){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()-chainmailCost);
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
                if(gamePlayer.getPlayerBalance() >= ironCost){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()-ironCost);
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
                if(gamePlayer.getPlayerBalance() >= diamondCost){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()-diamondCost);
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
                if(gamePlayer.getPlayerBalance() >= netherCost){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()-netherCost);
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
                if(gamePlayer.getPlayerBalance() >= bowCost){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()-bowCost);
                    gamePlayer.setBowProgressor(BowProgressor.BOW);
                    player.getInventory().setItem(1, bow);
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
                if(gamePlayer.getPlayerBalance() >= crossBowCost){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()-crossBowCost);
                    gamePlayer.setBowProgressor(BowProgressor.CROSSBOW);
                    player.getInventory().setItem(1, crossbow);

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
                if(gamePlayer.getPlayerBalance() >= powerCrossBowCost){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()-powerCrossBowCost);
                    gamePlayer.setBowProgressor(BowProgressor.POWERED_CROSSBOW);
                    player.getInventory().setItem(1, powercrossbow);

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
                if(gamePlayer.getPlayerBalance() >= powerBowCost){
                    gamePlayer.setPlayerBalance(gamePlayer.getPlayerBalance()-powerBowCost);
                    gamePlayer.setBowProgressor(BowProgressor.POWERED_BOW);
                    player.getInventory().setItem(1, powerbow);

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
