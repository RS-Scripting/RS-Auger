package com.rsscripting.rsauger.machine;

import com.rsscripting.rsauger.config.ConfigManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

public class AugerValidator {

    private static BlockFace getSourceDirection(
            Block machine
    ) {

        if (!(machine.getBlockData()
                instanceof org.bukkit.block.data.type.LightningRod rod)) {

            return null;

        }

        return rod.getFacing();

    }

    private static BlockFace getDestinationDirection(
            Block machine
    ) {

        return getSourceDirection(
                machine
        );

    }

    private static boolean isValidContainer(
            Material material
    ) {

        return material == Material.CHEST
                || material == Material.TRAPPED_CHEST
                || material == Material.BARREL
                || material == Material.HOPPER;

    }

    private static boolean isValidShaftBlock(
            Material material
    ) {

        return material.name()
                .endsWith(
                        "LIGHTNING_ROD"
                );

    }

    public static AugerValidationResult validate(
            Block machine
    ) {

        List<String> messages =
                new ArrayList<>();

        BlockFace sourceDirection =
                getSourceDirection(
                        machine
                );

        if (sourceDirection == null) {

            messages.add(
                    "Unable to determine Auger direction."
            );

            return new AugerValidationResult(
                    false,
                    null,
                    messages,
                    MachineError.INVALID_CONFIGURATION
            );

        }

        Block source =
                machine.getRelative(
                        sourceDirection.getOppositeFace()
                );

        if (!isValidContainer(
                source.getType()
        )) {

            messages.add(
                    "No valid source container found."
            );

            return new AugerValidationResult(
                    false,
                    null,
                    messages,
                    MachineError.MISSING_SOURCE
            );

        }

        BlockFace destinationDirection =
                getDestinationDirection(
                        machine
                );

        if (destinationDirection == null) {

            messages.add(
                    "Could not determine destination direction."
            );

            return new AugerValidationResult(
                    false,
                    null,
                    messages,
                    MachineError.INVALID_CONFIGURATION
            );

        }

        Block current =
                machine.getRelative(
                        destinationDirection
                );

        int length = 0;

        while (length < ConfigManager.getMaxMachineLength()) {

            Material type =
                    current.getType();

            if (isValidContainer(type)) {

                AugerEndpoints endpoints =
                        new AugerEndpoints(
                                source,
                                current
                        );

                messages.add(
                        "Validation successful."
                );

                return new AugerValidationResult(
                        true,
                        endpoints,
                        messages,
                        MachineError.NONE
                );

            }

            if (type == Material.AIR) {

                messages.add(
                        "No destination container found."
                );

                return new AugerValidationResult(
                        false,
                        null,
                        messages,
                        MachineError.MISSING_TARGET
                );

            }

            if (!isValidShaftBlock(type)) {

                messages.add(
                        "Invalid block detected in Auger."
                );

                return new AugerValidationResult(
                        false,
                        null,
                        messages,
                        MachineError.INVALID_TARGET
                );

            }

            current =
                    current.getRelative(
                            destinationDirection
                    );

            length++;

        }

        messages.add(
                "Auger too long."
        );

        return new AugerValidationResult(
                false,
                null,
                messages,
                MachineError.INVALID_CONFIGURATION
        );

    }

}