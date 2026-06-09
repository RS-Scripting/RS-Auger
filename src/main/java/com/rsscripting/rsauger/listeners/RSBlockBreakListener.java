package com.rsscripting.rsauger.listeners;

import com.rsscripting.rsauger.managers.MachineManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class RSBlockBreakListener
        implements Listener {

    @EventHandler
    public void onBlockBreak(
            BlockBreakEvent event
    ) {

        if (MachineManager.machineExists(
                event.getBlock()
        )) {

            event.setCancelled(
                    true
            );

        }

    }

}