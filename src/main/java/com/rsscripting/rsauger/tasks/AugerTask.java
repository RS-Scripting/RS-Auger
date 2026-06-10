package com.rsscripting.rsauger.tasks;

import com.rsscripting.rsauger.machine.AugerValidationResult;
import com.rsscripting.rsauger.machine.AugerValidator;
import com.rsscripting.rsauger.managers.MachineManager;
import com.rsscripting.rsauger.machine.AugerEndpoints;

import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class AugerTask extends BukkitRunnable {

    @Override
    public void run() {

        for (Block machine :
                MachineManager.getAllMachines()) {

            if (MachineManager.isPaused(
                    machine
            )) {

                continue;

            }

            AugerValidationResult result =
                    AugerValidator.validate(
                            machine
                    );

            if (!result.isValid()) {

                MachineManager.setPaused(
                        machine,
                        true
                );

                MachineManager.setMachineError(
                        machine,
                        result.getPrimaryError()
                );

                continue;

            }

            MachineManager.clearMachineError(
                    machine
            );

            AugerEndpoints endpoints =
                    MachineManager.getEndpoints(
                            machine
                    );

            if (endpoints == null) {

                continue;

            }

            if (!(endpoints.getSource()
                    .getState() instanceof Container sourceContainer)) {

                continue;

            }

            if (!(endpoints.getDestination()
                    .getState() instanceof Container destinationContainer)) {

                continue;

            }

            Inventory sourceInventory =
                    sourceContainer.getInventory();

            Inventory destinationInventory =
                    destinationContainer.getInventory();

            for (int slot = 0;
                 slot < sourceInventory.getSize();
                 slot++) {

                ItemStack item =
                        sourceInventory.getItem(
                                slot
                        );

                if (item == null
                        || item.getType().isAir()) {

                    continue;

                }

                ItemStack transfer =
                        item.clone();

                java.util.HashMap<Integer, ItemStack> leftovers =
                        destinationInventory.addItem(
                                transfer
                        );

                if (leftovers.isEmpty()) {

                    sourceInventory.setItem(
                            slot,
                            null
                    );

                }
                else {

                    ItemStack remaining =
                            leftovers.values()
                                    .iterator()
                                    .next();

                    sourceInventory.setItem(
                            slot,
                            remaining
                    );

                }

                break;

            }

        }

    }

}