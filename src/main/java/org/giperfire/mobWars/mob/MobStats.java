package org.giperfire.mobWars.mob;

public class MobStats {
    private final int baseDamage;
    private final double attackDamage;
    private final double attackSpeed;
    private final double moveSpeed;
    private final double maxHealth;
    private final double cost;
    private final double paydayIncrease;
    private final double cooldown;

    public MobStats(int baseDamage, double attackDamage, double attackSpeed,
                    double moveSpeed, double maxHealth, double cost, double paydayIncrease,
                    double cooldown){
        this.baseDamage = baseDamage;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.moveSpeed = moveSpeed;
        this.maxHealth = maxHealth;
        this.cost = cost;
        this.paydayIncrease = paydayIncrease;
        this.cooldown = cooldown;
    }

    public double getMaxHealth() {return maxHealth;}
    public double getMoveSpeed() {return moveSpeed;}
    public double getBaseDamage() {return baseDamage;}
    public double getAttackDamage() {return attackDamage;}
    public double getAttackSpeed() {return attackSpeed;}
    public double getCost() {return cost;}
    public double getPaydayIncrease() {return paydayIncrease;}
    public int getCooldown() {return (int) cooldown;}

}
