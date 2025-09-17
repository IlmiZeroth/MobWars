package org.giperfire.mobWars.game;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathHandler implements Listener {

    @EventHandler
    public void onDeath(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();

            if(GameController.isPlayerInGame(player)){
                // Проверяем, держит ли игрок тотем в основной или дополнительной руке
                boolean hasTotemInHand = hasTotemInAnyHand(player);

                if (hasTotemInHand) {
                    // Игрок держит тотем - не отменяем ивент, пусть Minecraft обработает его стандартно
                    return;
                }
                else if (player.getHealth() - event.getDamage() <= 0) {
                    // Игрок умирает и не держит тотем - отменяем стандартную смерть
                    event.setCancelled(true);
                    player.setHealth(20);
                    GameController.getGameWithPlayer(player).playerDeath(player);
                }
            }
        }
    }

    /**
     * Проверяет, держит ли игрок тотем в основной или дополнительной руке
     */
    private boolean hasTotemInAnyHand(Player player) {
        // Проверяем основную руку
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        if (mainHand != null && mainHand.getType() == Material.TOTEM_OF_UNDYING) {
            return true;
        }

        // Проверяем дополнительную руку
        ItemStack offHand = player.getInventory().getItemInOffHand();
        if (offHand != null && offHand.getType() == Material.TOTEM_OF_UNDYING) {
            return true;
        }

        return false;
    }
}