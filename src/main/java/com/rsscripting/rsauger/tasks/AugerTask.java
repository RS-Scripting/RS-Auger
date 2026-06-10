package com.rsscripting.rsauger.tasks;

import com.rsscripting.rsauger.machine.AugerValidationResult;
import com.rsscripting.rsauger.machine.AugerValidator;
import com.rsscripting.rsauger.machine.MachineState;
import com.rsscripting.rsauger.managers.MachineManager;
import com.rsscripting.rsauger.machine.AugerEndpoints;

import com.rsscripting.rsauger.managers.RSConversionManager;
import com.rsscripting.rsauger.utils.RSKeys;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Particle;
import org.bukkit.block.data.type.LightningRod;
import org.bukkit.block.BlockFace;

public class AugerTask extends BukkitRunnable {

    @Override
    public void run() {

        for (Block machine :
                MachineManager.getAllMachines()) {

            if (

                    MachineManager.getMachineState(
                            machine
                    ) == MachineState.ERROR

            ) {

                LightningRod rod =
                        (LightningRod) machine.getBlockData();

                BlockFace facing =
                        rod.getFacing();

                double yOffset = 0.35;

                if (facing == BlockFace.UP
                        || facing == BlockFace.DOWN) {

                    yOffset = 0.2;

                }

                machine.getWorld().spawnParticle(
                        Particle.ANGRY_VILLAGER,
                        machine.getLocation().add(
                                0.5,
                                yOffset,
                                0.5
                        ),
                        3
                );

                continue;

            }

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

                String filterMode =
                        MachineManager.getFilterMode(
                                machine
                        );

                boolean matchesFilter =
                        RSConversionManager.containsFilterItem(
                                machine,
                                item
                        );

                if (filterMode.equals(
                        RSKeys.FILTER_WHITELIST
                )) {

                    if (!matchesFilter) {

                        continue;

                    }

                }

                else if (filterMode.equals(
                        RSKeys.FILTER_BLACKLIST
                )) {

                    if (matchesFilter) {

                        continue;

                    }

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