package org.ilmizeroth.MobWars.game.player;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.UUID;

public class PlayerInformation implements ConfigurationSerializable {
    private ItemStack[] playerInventory;
    private UUID uuid;
    private Location playerLocation;
    private GameMode gameMode;
    public PlayerInformation(UUID uuid, Inventory playerInventory, Location playerLocation, GameMode gameMode){
        this.playerInventory = playerInventory.getContents();
        this.uuid = uuid;
        this.playerLocation = playerLocation;
        this.gameMode = gameMode;
    }

    public static Inventory setGameInventory(Player player){
        Inventory playerInventory = player.getInventory();
        playerInventory.clear();

        ItemStack weapon = new ItemStack(Material.STICK);
        ItemMeta weaponMeta = weapon.getItemMeta();
        weaponMeta.addEnchant(Enchantment.KNOCKBACK, 3, true);
        weaponMeta.setItemName("§aПалка-убивалка");
        weapon.setItemMeta(weaponMeta);

        ItemStack weaponStore = new ItemStack(Material.END_CRYSTAL);
        ItemMeta weaponStoreMeta = weaponStore.getItemMeta();
        weaponStoreMeta.setItemName("§aБроня и снаряжение");
        weaponStore.setItemMeta(weaponStoreMeta);

        ItemStack summon = new ItemStack(Material.EMERALD);
        ItemMeta summonMeta = summon.getItemMeta();
        summonMeta.setItemName("§aПризыв монстров");
        summon.setItemMeta(summonMeta);

        ItemStack leatherHat = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta hatMeta = leatherHat.getItemMeta();
        hatMeta.setUnbreakable(true);
        leatherHat.setItemMeta(hatMeta);

        ItemStack leatherChest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemMeta chestMeta = leatherChest.getItemMeta();
        chestMeta.setUnbreakable(true);
        leatherChest.setItemMeta(chestMeta);

        ItemStack leatherLeg = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemMeta legMeta = leatherLeg.getItemMeta();
        legMeta.setUnbreakable(true);
        leatherLeg.setItemMeta(legMeta);

        ItemStack leatherBoot = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta bootMeta = leatherBoot.getItemMeta();
        bootMeta.setUnbreakable(true);
        leatherBoot.setItemMeta(bootMeta);

        ItemStack emptyBow = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta emptyBowMeta = emptyBow.getItemMeta();
        emptyBowMeta.setItemName(" ");
        emptyBow.setItemMeta(emptyBowMeta);

        playerInventory.setItem(0, weapon);
        playerInventory.setItem(1, emptyBow);
        playerInventory.setItem(2, emptyBow);
        playerInventory.setItem(7, weaponStore);
        playerInventory.setItem(8, summon);
        playerInventory.setItem(39, leatherHat);
        playerInventory.setItem(38, leatherChest);
        playerInventory.setItem(37, leatherLeg);
        playerInventory.setItem(36, leatherBoot);


        return playerInventory;
    }
    public static Inventory setLobbyInventory(Player player){
        if(player == null) return null;
        Inventory playerInventory = player.getInventory();
        playerInventory.clear();

        ItemStack leave = new ItemStack(Material.IRON_DOOR);
        ItemMeta leaveMeta = leave.getItemMeta();
        leaveMeta.setItemName("§aПокинуть лобби");
        leave.setItemMeta(leaveMeta);

        playerInventory.setItem(8, leave);

        return playerInventory;
    }
    public ItemStack[] getPlayerInventory(){
        return playerInventory;
    }
    public Location getPlayerLocation(){
        return playerLocation;
    }
    public GameMode getGameMode(){
        return gameMode;
    }
    @Override
    public Map<String, Object> serialize() {
        return Map.of("playerInventory", playerInventory,
                "playerLocation", playerLocation,
                "gameMode", gameMode.getValue());
    }
}

