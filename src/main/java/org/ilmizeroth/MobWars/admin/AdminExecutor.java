package org.ilmizeroth.MobWars.admin;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ilmizeroth.MobWars.game.Map;
import org.ilmizeroth.MobWars.game.manager.MapCreationManager;
import org.ilmizeroth.MobWars.game.repository.MapRepository;
import org.ilmizeroth.MobWars.game.Sign;
import org.ilmizeroth.MobWars.game.manager.SignManager;
import org.ilmizeroth.MobWars.game.repository.SignRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AdminExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эта команда только для игроков!");
            return true;
        }
        if(args.length < 1){
            sender.sendMessage("Эта команда только для админов!");
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "addmap":
                return handleAddMap(((Player) sender).getPlayer(), args);
            case "fteamspawn":
                return setFirstTeamSpawn(((Player) sender).getPlayer());
            case "fteambase":
                return setFirstTeamBase(((Player) sender).getPlayer());
            case "steamspawn":
                return setSecondTeamSpawn(((Player) sender).getPlayer());
            case "steambase":
                return setSecondTeamBase(((Player) sender).getPlayer());
            case "createsign":
                return createSign(((Player) sender).getPlayer(), args);
            case "cancel":
                MapCreationManager.cancelSession(((Player) sender).getPlayer());
                sender.sendMessage("Создание карты отменено");
                return true;
            default:
                return false;
        }
    }
    private boolean setFirstTeamSpawn(Player player){
        if (!MapCreationManager.hasActiveSession(player)) {
            player.sendMessage("У вас нет активной сессии создания карты. Создайте её, используя /mwa addmap");
            return false;
        }
        MapCreationManager.getSession(player).setFirstTeamSpawn(player.getLocation());
        player.sendMessage("Точка появления первой команды установлена!");
        sendCompleteMessage(player);
        return true;
    }
    private boolean setSecondTeamSpawn(Player player){
        if (!MapCreationManager.hasActiveSession(player)) {
            player.sendMessage("У вас нет активной сессии создания карты. Создайте её, используя /mwa addmap");
            return false;
        }
        MapCreationManager.getSession(player).setSecondTeamSpawn(player.getLocation());
        player.sendMessage("Точка появления второй команды установлена!");
        sendCompleteMessage(player);
        return true;
    }
    private boolean setFirstTeamBase(Player player){
        if (!MapCreationManager.hasActiveSession(player)) {
            player.sendMessage("У вас нет активной сессии создания карты. Создайте её, используя /mwa addmap");
            return false;
        }
        MapCreationManager.getSession(player).setFirstTeamBase(player.getLocation());
        player.sendMessage("База первой команды установлена!");
        sendCompleteMessage(player);
        return true;
    }
    private boolean setSecondTeamBase(Player player){
        if (!MapCreationManager.hasActiveSession(player)) {
            player.sendMessage("У вас нет активной сессии создания карты. Создайте её, используя /mwa addmap");
            return false;
        }
        MapCreationManager.getSession(player).setSecondTeamBase(player.getLocation());
        player.sendMessage("База второй команды установлена!");
        sendCompleteMessage(player);
        return true;
    }

    private boolean createSign(Player player, String... args){
        if (args.length < 2) {
            player.sendMessage("Использование: /mwa createsign <название>");
            return true;
        }
        if(MapRepository.getMap(args[1]) == null) {
            player.sendMessage("Указанная карта не найдена!");
            return false;
        }
        Map map = MapRepository.getMap(args[1]);
        Set<Material> transparent = new HashSet<>();
        transparent.add(Material.AIR);

        Block targetBlock = player.getTargetBlock(transparent, 5);
        if (targetBlock.getState() instanceof org.bukkit.block.Sign) {
            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) targetBlock.getState();
            sign.setLine(0, "§c[MobWars]");
            sign.setLine(1, map.getMapName());
            sign.setLine(2, "Checking...");
            sign.setLine(3, "0/"+map.getMapMaxPlayers());
            sign.update();
            SignRepository.addSignToFile(new Sign(map.getMapName(), map.getMapMaxPlayers(), targetBlock.getLocation()));
            SignManager.updateAllSigns();
        }
        else{
            player.sendMessage("Вы должны смотреть на табличку, используя команду!");
            return false;
        }
        return true;
    }


    private void sendCompleteMessage(Player player){
        if(!MapCreationManager.hasActiveSession(player)){
            player.sendMessage("Карта была успешно добавлена!");
        }
    }
    private boolean handleAddMap(Player player, String... args){
        if (args.length < 3) {
            player.sendMessage("Использование: /mwa addmap <макс игроков> <название>");
            return true;
        }

        if (MapCreationManager.hasActiveSession(player)) {
            player.sendMessage("У вас уже есть активная сессия создания карты. Закончите её или отмените");
            return true;
        }

        try {
            String mapName = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
            int maxPlayers = Integer.parseInt(args[1]);

            if (maxPlayers < 2) {
                player.sendMessage("Минимальное количество игроков - 2");
                return true;
            }

            MapCreationManager.startNewSession(player, mapName, maxPlayers);
            player.sendMessage("Начато создание карты " + mapName +
                    "\nУкажите позиции с помощью /mwa fteamspawn, /mwa fteambase, /mwa steamspawn, /mwa steambase.");
            return true;
        } catch (NumberFormatException e) {
            player.sendMessage("Некорректное число игроков");
            return true;
        }
    }
}
