package org.giperfire.mobWars.mob;

import kr.toxicity.model.api.BetterModel;
import kr.toxicity.model.api.animation.AnimationModifier;
import kr.toxicity.model.api.tracker.EntityTracker;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalZombieAttack;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.monster.EntityPigZombie;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R5.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.giperfire.mobWars.MobWars;
import org.giperfire.mobWars.PathfinderGoalMoveToLocation;
import org.giperfire.mobWars.game.GameTeam;

import javax.annotation.Nullable;

public class IronMob extends EntityZombie implements BlockMob{
    private Entity entity;
    private final GameTeam team;
    public static final int baseDamage = 12;
    private static final double attackDamage = 4;
    private static final double attackSpeed = 1.2;
    private static final double moveSpeed = 0.1;
    private static final double maxHealth = 80;
    private static final double cost = 320;
    private static final double paydayIncrease = (double) 80/4;
    private static final double cooldown = 100;
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

    public IronMob(Location location, Location targetLoc, GameTeam team){
        super(EntityTypes.bQ, ((CraftWorld) location.getWorld()).getHandle());
        this.o(location.getX(), location.getY(), location.getZ()); //setPosRaw and then spawn entity:
        this.spawnLocation = location;
        this.targetLocation = location;
        this.team = team;
        // ci - targetSelector
        // ch - goalSelector
        // S() - getNavigation

        // Добавляем новую цель - движение к координатам
        this.ch.a(1, new PathfinderGoalMoveToLocation(
                this,
                targetLoc.getX(),
                targetLoc.getY(),
                targetLoc.getZ(),
                1,this
        ));

        this.h(GenericAttributes.w).a(moveSpeed); //get and change speed
        this.h(GenericAttributes.t).a(maxHealth); //get and change max health
        this.h(GenericAttributes.c).a(attackDamage); //attack damage
        this.gK().a(GenericAttributes.e, attackSpeed);
        this.x((float) maxHealth);
//        this.h(GenericAttributes.e).a(1.2); //attack speed

    }
    public boolean spawnMob(){
        try {
            ((CraftWorld) spawnLocation.getWorld()).getHandle().addFreshEntity(this, CreatureSpawnEvent.SpawnReason.COMMAND);
            Zombie glass = (Zombie) this.getBukkitEntity(); // cast Entity to Bukkit Pig?
            glass.setBaby();
            glass.setMetadata("mobType", new FixedMetadataValue(MobWars.getInstance(), "ironBlock"));
            this.entity = glass;
            EntityTracker tracker = BetterModel.model("iron")
                    .map(r -> r.getOrCreate(entity)) //Gets or creates entity tracker by this renderer to some entity.
                    .orElse(null);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    protected void H() {
        this.m();
    }
    @Override
    protected void m() {
        // ci - targetSelector
        // ch - goalSelector
        // S() - getNavigation

        this.ci.a();
        this.ch.a();
        // Добавляем новую цель - движение к координатам

        this.ci.a(0, (new PathfinderGoalHurtByTarget(this, new Class[0])));
        this.ch.a(2, new PathfinderGoalZombieAttack(this, 1.0, false));
    }
    @Override
    public boolean c(WorldServer worldserver, net.minecraft.world.entity.Entity entity){
        BetterModel.registry(this.entity) //Gets tracker registry.
                .map(reg -> reg.tracker("iron")) //Gets tracker by it's name
                .ifPresent(tracker -> tracker.animate("attack", AnimationModifier.DEFAULT_WITH_PLAY_ONCE, () -> {
                    //Do stuff when this animation is being removed.
                }));
        return super.c(worldserver, entity);
    }

    //Hit sound
    @Override
    protected SoundEffect e(DamageSource damagesource) {
        return SoundEffects.U;
    }

    //Death sound to break
    @Override
    protected SoundEffect f_() {
        return SoundEffects.V;
    }

    //Ambient sound to step
    @Override
    protected SoundEffect p() {
        return SoundEffects.ab;
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
                        "§4Урон по игрокам: §f%.1f❤/удар\n" +
                        "§bСкорость атаки: §f%.1f ударов/сек\n" +
                        "§9Стоимость: §f%.0f⛃\n" +
                        "§2Доход: §f+%.0f⛃/сек\n" +
                        "§8Кулдаун: §f%.0f сек",
                maxHealth,
                moveSpeed,
                baseDamage,
                attackDamage,
                attackSpeed,
                cost,
                paydayIncrease,
                cooldown
        );
    }
}
