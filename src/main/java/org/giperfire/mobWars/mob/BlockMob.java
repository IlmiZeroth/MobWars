package org.giperfire.mobWars.mob;

import org.bukkit.craftbukkit.v1_21_R5.CraftWorld;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;

public interface BlockMob {
    MobStats getStats();
    boolean spawnMob();
    void damageEnemyBase();
}
