package org.ilmizeroth.MobWars.mob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobController {
    private static final HashMap<Class, Integer> ALL_BLOCK_MOBS = new HashMap<>();
    private static final HashMap<Class, Double> ALL_BLOCK_MOBS_COOLDOWN = new HashMap<>();
    private static final HashMap<Class, Integer> ALL_BLOCK_MOBS_LIMITS = new HashMap<>();
    private static final List<Class> MOBS_HIERARCHY = new ArrayList<>();

    static{
        ALL_BLOCK_MOBS.put(GlassMob.class, 0);
        ALL_BLOCK_MOBS.put(SandMob.class, 0);
        ALL_BLOCK_MOBS.put(DirtMob.class, 0);
        ALL_BLOCK_MOBS.put(OakMob.class, 0);
        ALL_BLOCK_MOBS.put(CobblestoneMob.class, 0);
        ALL_BLOCK_MOBS.put(IronMob.class, 0);
        ALL_BLOCK_MOBS.put(GoldMob.class, 0);
        ALL_BLOCK_MOBS.put(DiamondMob.class, 0);
        ALL_BLOCK_MOBS.put(ObsidianMob.class, 0);
        ALL_BLOCK_MOBS.put(BedrockMob.class, 0);

        ALL_BLOCK_MOBS_COOLDOWN.put(GlassMob.class, GlassMob.getClassStats().cooldown());
        ALL_BLOCK_MOBS_COOLDOWN.put(SandMob.class, SandMob.getClassStats().cooldown());
        ALL_BLOCK_MOBS_COOLDOWN.put(DirtMob.class, DirtMob.getClassStats().cooldown());
        ALL_BLOCK_MOBS_COOLDOWN.put(OakMob.class, OakMob.getClassStats().cooldown());
        ALL_BLOCK_MOBS_COOLDOWN.put(CobblestoneMob.class, CobblestoneMob.getClassStats().cooldown());
        ALL_BLOCK_MOBS_COOLDOWN.put(IronMob.class, IronMob.getClassStats().cooldown());
        ALL_BLOCK_MOBS_COOLDOWN.put(GoldMob.class, GoldMob.getClassStats().cooldown());
        ALL_BLOCK_MOBS_COOLDOWN.put(DiamondMob.class, DiamondMob.getClassStats().cooldown());
        ALL_BLOCK_MOBS_COOLDOWN.put(ObsidianMob.class, ObsidianMob.getClassStats().cooldown());
        ALL_BLOCK_MOBS_COOLDOWN.put(BedrockMob.class, BedrockMob.getClassStats().cooldown());

        ALL_BLOCK_MOBS_LIMITS.put(GlassMob.class, 15);
        ALL_BLOCK_MOBS_LIMITS.put(SandMob.class, 10);
        ALL_BLOCK_MOBS_LIMITS.put(DirtMob.class, 10);
        ALL_BLOCK_MOBS_LIMITS.put(OakMob.class, 5);
        ALL_BLOCK_MOBS_LIMITS.put(CobblestoneMob.class, 5);
        ALL_BLOCK_MOBS_LIMITS.put(IronMob.class, 4);
        ALL_BLOCK_MOBS_LIMITS.put(GoldMob.class, 3);
        ALL_BLOCK_MOBS_LIMITS.put(DiamondMob.class, 2);
        ALL_BLOCK_MOBS_LIMITS.put(ObsidianMob.class, 2);
        ALL_BLOCK_MOBS_LIMITS.put(BedrockMob.class, 1);

        MOBS_HIERARCHY.add(GlassMob.class);
        MOBS_HIERARCHY.add(SandMob.class);
        MOBS_HIERARCHY.add(DirtMob.class);
        MOBS_HIERARCHY.add(OakMob.class);
        MOBS_HIERARCHY.add(CobblestoneMob.class);
        MOBS_HIERARCHY.add(IronMob.class);
        MOBS_HIERARCHY.add(GoldMob.class);
        MOBS_HIERARCHY.add(DiamondMob.class);
        MOBS_HIERARCHY.add(ObsidianMob.class);
        MOBS_HIERARCHY.add(BedrockMob.class);
    }

    public static HashMap<Class, Integer> getAllBlockMobs(){
        return ALL_BLOCK_MOBS;
    }
    public static HashMap<Class, Double> getAllBlockMobsCooldown(){
        return ALL_BLOCK_MOBS_COOLDOWN;
    }
    public static HashMap<Class, Integer> getAllBlockMobsLimits(){
        return ALL_BLOCK_MOBS_LIMITS;
    }
    public static List<Class> getMobsHierarchy(){
        return MOBS_HIERARCHY;
    }
}
