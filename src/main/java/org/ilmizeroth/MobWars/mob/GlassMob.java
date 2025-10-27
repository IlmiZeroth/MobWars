package org.ilmizeroth.MobWars.mob;

import kr.toxicity.model.api.BetterModel;
import kr.toxicity.model.api.tracker.EntityTracker;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.animal.EntityPig;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R5.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.ilmizeroth.MobWars.MobWars;
import org.ilmizeroth.MobWars.PathFinderMoveToLocation;
import org.ilmizeroth.MobWars.game.GameTeam;

import javax.annotation.Nullable;

public class GlassMob extends EntityPig implements BlockMob{

    private Entity entity;
    private final GameTeam team;
    private static final int baseDamage = 1;
    private static final double attackDamage = 0;
    private static final double attackSpeed = 0;
    private static final double moveSpeed = 0.15;
    private static final double maxHealth = 5;
    private static final double cost = 10;
    private static final double paydayIncrease = (double) 10/4;
    private static final double cooldown = 10;
    private final Location spawnLocation;
    private final Location targetLocation;
    private static final MobStats STATS = new MobStats(baseDamage, attackDamage, attackSpeed,
            moveSpeed, maxHealth, cost, paydayIncrease, cooldown);

    @Override
    public MobStats getStats() {
        return STATS;
    }
    @Override
    public void damageEnemyBase(){
        team.getEnemyTeam().getDamage(this);
    }
    public static MobStats getClassStats(){
        return STATS;
    }

    @Nullable
    public Entity getEntity(){
        return entity;
    }

    public GlassMob(Location location, Location targetLoc, GameTeam team){
        super(EntityTypes.aS, ((CraftWorld) location.getWorld()).getHandle());
        this.o(location.getX(), location.getY(), location.getZ()); //setPosRaw and then spawn entity:
        this.spawnLocation = location;
        this.targetLocation = location;
        this.team = team;

            // ci - targetSelector
            // ch - goalSelector
            // S() - getNavigation

        // Очищаем текущие цели (чтобы не было конфликтов с AI)
        this.ci.a();

        // Добавляем новую цель - движение к координатам
        this.h(GenericAttributes.w).a(moveSpeed); //get and change speed
        this.h(GenericAttributes.t).a(maxHealth); //get and change max health
        this.x((float) maxHealth);
        this.ch.a(0, new PathFinderMoveToLocation(
                    this,
                    targetLoc.getX(),
                    targetLoc.getY(),
                    targetLoc.getZ(),
                    1, this
        ));
    }

    public boolean spawnMob(){
        try {
            ((CraftWorld) spawnLocation.getWorld()).getHandle().addFreshEntity(this, CreatureSpawnEvent.SpawnReason.COMMAND);
            Pig glass = (Pig) this.getBukkitEntity(); // cast Entity to Bukkit Pig?
            glass.setBaby();
            glass.setMetadata("mobType", new FixedMetadataValue(MobWars.getInstance(), "glassBlock"));
            this.entity = glass;
            EntityTracker tracker = BetterModel.model("glass")
                    .map(r -> r.getOrCreate(entity)) //Gets or creates entity tracker by this renderer to some entity.
                    .orElse(null);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //Hit sound
    @Override
    protected SoundEffect e(DamageSource damagesource) {
            return SoundEffects.lo;
        }

    //Death sound to break
    @Override
    protected SoundEffect f_() {
            return SoundEffects.lm;
        }

    //Ambient sound to step
    @Override
    protected SoundEffect p() {
        return SoundEffects.lq;
    }

    @Override
    protected boolean eu(){
        return false;
    }
    @Override
    public String toString() {
        return String.format(
                "§7=== §6Характеристики моба §7===\n" +
                        "§cЗдоровье: §f%.0f❤\n" +
                        "§aСкорость: §f%.2f блоков/сек\n" +
                        "§6Урон по базе: §f%d⚔\n" +
                        "§9Стоимость: §f%.0f⛃\n" +
                        "§2Доход: §f+%.0f⛃/сек\n" +
                        "§8Кулдаун: §f%.0f сек",
                maxHealth,
                moveSpeed,
                baseDamage,
                cost,
                paydayIncrease,
                cooldown
        );
    }

    @Nullable
    @Override
    public EntityAgeable a(WorldServer worldServer, EntityAgeable entityAgeable) {
        return null;
    }
}
