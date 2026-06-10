package com.rsscripting.rsauger.listeners;

import com.rsscripting.rsauger.machine.AugerEndpoints;
import com.rsscripting.rsauger.machine.MachineError;
import com.rsscripting.rsauger.managers.MachineManager;
import com.rsscripting.rsauger.gui.*;
import com.rsscripting.rsauger.utils.RSConstants;
import com.rsscripting.rsauger.utils.RSKeys;
import com.rsscripting.rsauger.utils.RSMessageUtils;
import com.rsscripting.rsauger.machine.AugerValidator;
import com.rsscripting.rsauger.machine.AugerValidationResult;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RSMenuListener
        implements Listener {

    private final RSConvertListener
            conversionListener;

    public RSMenuListener(
            RSConvertListener conversionListener
    ) {

        this.conversionListener =
                conversionListener;

    }

    @EventHandler
    public void onInventoryClick(
            InventoryClickEvent event
    ) {

        Player player =
                (Player) event.getWhoClicked();

        /*
        |--------------------------------------------------------------------------
        | CONVERSION MENU
        |--------------------------------------------------------------------------
        */

        if (event.getInventory()
                .getHolder()
                instanceof RSMenuHolder holder
                && holder.getMenuId()
                .equals(
                        "CONVERT"
                )) {

            event.setCancelled(true);

            if (event.getCurrentItem() == null) {
                return;
            }

            if (event.getRawSlot() == 4) {

                Block block =
                        conversionListener.getSelectedBlock(player);

                if (block == null){

                    player.closeInventory();

                    return;

                }

                AugerValidationResult result =
                        AugerValidator.validate(
                                block
                        );

                if (!result.isValid()) {

                    for (String message :
                            result.getMessages()) {

                        RSMessageUtils.error(
                                player,
                                message
                        );

                    }

                    player.closeInventory();

                    return;

                }

                MachineManager.insertMachine(
                        block
                );

                AugerEndpoints endpoints =
                        result.getEndpoints();

                MachineManager.setEndpoints(
                        block,
                        endpoints.getSource(),
                        endpoints.getDestination()
                );

                MachineManager.setOwner(
                        block,
                        player
                );

                MachineManager.setPaused(
                        block,
                        false
                );

                MachineManager.setFilterMode(
                        block,
                        RSKeys.FILTER_ALLOW_ALL
                );

                RSMessageUtils.success(
                        player,
                        "Converted Successfully."
                );

                RSMainMenu.open(
                        player,
                        block
                );

            }

            return;

        }

        /*
        |--------------------------------------------------------------------------
        | MAIN MENU
        |--------------------------------------------------------------------------
        */

        if (event.getInventory()
                .getHolder()
                instanceof RSMenuHolder holder
                && holder.getMenuId()
                .equals(
                        "MAIN"
                )) {

            event.setCancelled(true);

            if (event.getCurrentItem() == null) {
                return;
            }

            Block block =
                    conversionListener.getSelectedBlock(player);

            if (block == null) {

                player.closeInventory();

                return;

            }

            /*
            |--------------------------------------------------------------------------
            | CONVERT BACK
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 2) {

                conversionListener.convertBack(
                        player
                );

                return;

            }

            /*
            |--------------------------------------------------------------------------
            | PAUSE / RESUME
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 6) {

                if (

                        MachineManager.getMachineError(
                                block
                        ) != MachineError.NONE

                ) {

                    AugerValidationResult result =
                            AugerValidator.validate(
                                    block
                            );

                    if (!result.isValid()) {

                        RSMessageUtils.error(
                                player,
                                "Cannot restart machine until the error is repaired."
                        );

                        RSMainMenu.open(
                                player,
                                block
                        );

                        return;

                    }

                    MachineManager.setEndpoints(
                            block,
                            result.getEndpoints()
                    );

                    MachineManager.clearMachineError(
                            block
                    );

                    MachineManager.setPaused(
                            block,
                            false
                    );

                }

                else {

                    boolean paused =
                            MachineManager.isPaused(
                                    block
                            );

                    MachineManager.setPaused(
                            block,
                            !paused
                    );

                }

                RSMainMenu.open(
                        player,
                        block
                );

                return;

            }

            /*
            |--------------------------------------------------------------------------
            | FILTER
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 13) {

                RSFilterMenu.open(
                        player,
                        block
                );

                return;

            }

            /*
            |--------------------------------------------------------------------------
            | ADMIN MENU
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 4) {

                if (!player.isOp()
                        ||

                        !player.hasPermission(
                                RSConstants.ADMIN_PERMISSION
                        )) {

                    return;

                }

                RSAdminMenu.open(
                        player
                );

            }

        }

    }

}