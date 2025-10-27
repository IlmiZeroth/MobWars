package org.ilmizeroth.MobWars.mob;

public record MobStats(int baseDamage, double attackDamage, double attackSpeed, double moveSpeed, double maxHealth,
                       double cost, double paydayIncrease, double cooldown) {}