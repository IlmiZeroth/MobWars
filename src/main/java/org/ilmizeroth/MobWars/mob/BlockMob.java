package org.ilmizeroth.MobWars.mob;

public interface BlockMob {
    MobStats getStats();
    boolean spawnMob();
    void damageEnemyBase();
}
