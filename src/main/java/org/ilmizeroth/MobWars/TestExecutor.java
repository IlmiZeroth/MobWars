//package org.giperfire.mobWars;
//
//import kr.toxicity.model.api.BetterModel;
//import kr.toxicity.model.api.tracker.EntityTracker;
//import org.bukkit.Location;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.Player;
//import org.giperfire.mobWars.game.GameTeam;
//import org.giperfire.mobWars.mob.*;
//
//import java.util.ArrayList;
//
//
//public class TestExecutor implements CommandExecutor {
//    @Override
//    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
//        if (!(sender instanceof Player)) {
//            sender.sendMessage("Эта команда только для игроков!");
//            return true;
//        }
//        Player player = (Player) sender;
//        Location targetLoc = player.getLocation().add(20, 0, 0);
//        GameTeam newteam = new GameTeam(new ArrayList<>(), player.getLocation(), targetLoc);
//        GameTeam enemyteam = new GameTeam(new ArrayList<>(), player.getLocation(), targetLoc);
//        newteam.setEnemy(enemyteam);
//        enemyteam.setEnemy(newteam);
//
//        Entity entity = new CobblestoneMob(player.getLocation(), targetLoc, newteam).getEntity();
//        EntityTracker tracker = BetterModel.model("cobblestone")
//                .map(r -> r.getOrCreate(entity)) //Gets or creates entity tracker by this renderer to some entity.
//                .orElse(null);
//        Entity entity1 = new GlassMob(player.getLocation(), targetLoc, newteam).getEntity();
//        EntityTracker tracker1 = BetterModel.model("glass")
//                .map(r -> r.getOrCreate(entity1)) //Gets or creates entity tracker by this renderer to some entity.
//                .orElse(null);
//        Entity entity2 = new SandMob(player.getLocation(), targetLoc, newteam).getEntity();
//        EntityTracker tracker2 = BetterModel.model("sand")
//                .map(r -> r.getOrCreate(entity2)) //Gets or creates entity tracker by this renderer to some entity.
//                .orElse(null);
//        Entity entity3 = new DirtMob(player.getLocation(), targetLoc, newteam).getEntity();
//        EntityTracker tracker3 = BetterModel.model("dirt")
//                .map(r -> r.getOrCreate(entity3)) //Gets or creates entity tracker by this renderer to some entity.
//                .orElse(null);
//        Entity entity4 = new OakMob(player.getLocation(), targetLoc, newteam).getEntity();
//        EntityTracker tracker4 = BetterModel.model("oak")
//                .map(r -> r.getOrCreate(entity4)) //Gets or creates entity tracker by this renderer to some entity.
//                .orElse(null);
//        Entity entity5 = new IronMob(player.getLocation(), targetLoc, newteam).getEntity();
//        EntityTracker tracker5 = BetterModel.model("iron")
//                .map(r -> r.getOrCreate(entity5)) //Gets or creates entity tracker by this renderer to some entity.
//                .orElse(null);
//        Entity entity6 = new GoldMob(player.getLocation(), targetLoc, newteam).getEntity();
//        EntityTracker tracker6 = BetterModel.model("gold")
//                .map(r -> r.getOrCreate(entity6)) //Gets or creates entity tracker by this renderer to some entity.
//                .orElse(null);
//        Entity entity7 = new DiamondMob(player.getLocation(), targetLoc, newteam).getEntity();
//        EntityTracker tracker7 = BetterModel.model("diamond")
//                .map(r -> r.getOrCreate(entity7)) //Gets or creates entity tracker by this renderer to some entity.
//                .orElse(null);
//        Entity entity8 = new ObsidianMob(player.getLocation(), targetLoc, newteam).getEntity();
//        EntityTracker tracker8 = BetterModel.model("obsidian")
//                .map(r -> r.getOrCreate(entity8)) //Gets or creates entity tracker by this renderer to some entity.
//                .orElse(null);
//        Entity entity9 = new BedrockMob(player.getLocation(), targetLoc, newteam).getEntity();
//        EntityTracker tracker9 = BetterModel.model("bedrock")
//                .map(r -> r.getOrCreate(entity9)) //Gets or creates entity tracker by this renderer to some entity.
//                .orElse(null);
//        player.sendMessage("Зомби направляется к координатам: " + targetLoc.getX() + ", " + targetLoc.getZ());
//        return false;
//    }
//}
