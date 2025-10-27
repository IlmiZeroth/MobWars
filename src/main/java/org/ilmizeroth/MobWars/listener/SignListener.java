package org.ilmizeroth.MobWars.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.ilmizeroth.MobWars.handler.SignHandler;

public class SignListener implements Listener {
    private final SignHandler signHandler;

    public SignListener(SignHandler signHandler) {
        this.signHandler = signHandler;
    }

    @EventHandler
    public void onSignBreak(BlockBreakEvent event){
        if(event.getBlock().getState() instanceof org.bukkit.block.Sign) {
            signHandler.handleSignBreak(event);
        }
    }

    @EventHandler
    public void interactWithSign(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getClickedBlock().getState() instanceof org.bukkit.block.Sign){
                signHandler.handleInteractWithSign(event);
            }
        }
    }
}
