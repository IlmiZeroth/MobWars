package org.ilmizeroth.MobWars;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.EntityInsentient;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityRemoveEvent;
import org.ilmizeroth.MobWars.mob.BlockMob;

/**
 * PathFinder для BlockMob.
 * Делает так, чтобы все мобы, использующие его
 * шли к определенным координатам.
 * Когда сущность, использующая этот PathFinder
 * доходит до цели - она исчезает
 */
public class PathFinderMoveToLocation extends PathfinderGoal {
    private final EntityInsentient entity;
    private final double x, y, z;
    private final double speed;
    private int timeToRecalcPath;
    private boolean reached = false;
    private final BlockMob blockMob;

    public PathFinderMoveToLocation(EntityInsentient entity, double x, double y, double z, double speed, BlockMob blockMob) {
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
        this.speed = speed;
        this.blockMob = blockMob;
    }

    @Override
    public boolean c() //check reached or not (every tick from getNavigation.a())
    {
        return !reached; // Прекращаем цель, если достигли точки
    }

    @Override
    public boolean b() {
        return !reached && !this.entity.S().m(); // Продолжаем, если не достигли и путь не завершён
    }

    @Override
    public void d() // start
    {
        this.timeToRecalcPath = 0;
        this.entity.S().a(this.x, this.y, this.z, this.speed);
    }

    @Override
    public void a() //every tick do something
    {
        if(this.entity.e() != null){
            return;
        }
        if (reached) return;
        this.entity.S().a(this.x, this.y, this.z, this.speed);
        // Проверяем расстояние до цели
        double distanceSquared = entity.h(x, y, z);
        if (distanceSquared < 3) { // Если ближе чем 1.73 блок (√3)
            reached = true;
            blockMob.damageEnemyBase();
            // Убиваем зомби через 20 тиков (1 секунда)
            Bukkit.getScheduler().runTaskLater(MobWars.getInstance(), () -> {
                entity.remove(Entity.RemovalReason.a, EntityRemoveEvent.Cause.PLUGIN);
            }, 20);
        } else if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            this.entity.S().a(this.x, this.y, this.z, this.speed);
        }
    }
}