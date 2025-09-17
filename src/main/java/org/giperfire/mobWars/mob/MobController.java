package org.giperfire.mobWars.mob;

import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobController {
    private static HashMap<Class, Integer> allBlockMobs = new HashMap<>();
    private static HashMap<Class, Integer> allBlockMobsCooldown = new HashMap<>();
    private static HashMap<Class, Integer> allBlockMobsLimits = new HashMap<>();
    private static List<Class> mobsHierarchy = new ArrayList<>();

    static{
        allBlockMobs.put(GlassMob.class, 0);
        allBlockMobs.put(SandMob.class, 0);
        allBlockMobs.put(DirtMob.class, 0);
        allBlockMobs.put(OakMob.class, 0);
        allBlockMobs.put(CobblestoneMob.class, 0);
        allBlockMobs.put(IronMob.class, 0);
        allBlockMobs.put(GoldMob.class, 0);
        allBlockMobs.put(DiamondMob.class, 0);
        allBlockMobs.put(ObsidianMob.class, 0);
        allBlockMobs.put(BedrockMob.class, 0);

        allBlockMobsCooldown.put(GlassMob.class, GlassMob.getClassStats().getCooldown());
        allBlockMobsCooldown.put(SandMob.class, SandMob.getClassStats().getCooldown());
        allBlockMobsCooldown.put(DirtMob.class, DirtMob.getClassStats().getCooldown());
        allBlockMobsCooldown.put(OakMob.class, OakMob.getClassStats().getCooldown());
        allBlockMobsCooldown.put(CobblestoneMob.class, CobblestoneMob.getClassStats().getCooldown());
        allBlockMobsCooldown.put(IronMob.class, IronMob.getClassStats().getCooldown());
        allBlockMobsCooldown.put(GoldMob.class, GoldMob.getClassStats().getCooldown());
        allBlockMobsCooldown.put(DiamondMob.class, DiamondMob.getClassStats().getCooldown());
        allBlockMobsCooldown.put(ObsidianMob.class, ObsidianMob.getClassStats().getCooldown());
        allBlockMobsCooldown.put(BedrockMob.class, BedrockMob.getClassStats().getCooldown());

        allBlockMobsLimits.put(GlassMob.class, 15);
        allBlockMobsLimits.put(SandMob.class, 10);
        allBlockMobsLimits.put(DirtMob.class, 10);
        allBlockMobsLimits.put(OakMob.class, 5);
        allBlockMobsLimits.put(CobblestoneMob.class, 5);
        allBlockMobsLimits.put(IronMob.class, 4);
        allBlockMobsLimits.put(GoldMob.class, 3);
        allBlockMobsLimits.put(DiamondMob.class, 2);
        allBlockMobsLimits.put(ObsidianMob.class, 2);
        allBlockMobsLimits.put(BedrockMob.class, 1);

        mobsHierarchy.add(GlassMob.class);
        mobsHierarchy.add(SandMob.class);
        mobsHierarchy.add(DirtMob.class);
        mobsHierarchy.add(OakMob.class);
        mobsHierarchy.add(CobblestoneMob.class);
        mobsHierarchy.add(IronMob.class);
        mobsHierarchy.add(GoldMob.class);
        mobsHierarchy.add(DiamondMob.class);
        mobsHierarchy.add(ObsidianMob.class);
        mobsHierarchy.add(BedrockMob.class);
    }

    public static HashMap<Class, Integer> getAllBlockMobs(){
        return allBlockMobs;
    }
    public static HashMap<Class, Integer> getAllBlockMobsCooldown(){
        return allBlockMobsCooldown;
    }
    public static HashMap<Class, Integer> getAllBlockMobsLimits(){
        return allBlockMobsLimits;
    }
    public static List<Class> getMobsHierarchy(){
        return mobsHierarchy;
    }
}
