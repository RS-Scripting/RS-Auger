package com.rsscripting.rsauger.gui;

import com.rsscripting.rsauger.machine.MachineError;
import com.rsscripting.rsauger.utils.RSConstants;
import com.rsscripting.rsauger.utils.RSMenuUtils;
import com.rsscripting.rsauger.managers.MachineManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RSMainMenu {

    public static final String MENU_TITLE = "Main";

    public static void open(
            Player player,
            Block block
    ) {

        Inventory menu =
                Bukkit.createInventory(
                        new RSMenuHolder(
                                "MAIN"
                        ),
                        18,
                        Component.text(
                                MENU_TITLE
                        ).color(
                                NamedTextColor.BLUE
                        )
                );

        /*
        |--------------------------------------------------------------------------
        | FILLER
        |--------------------------------------------------------------------------
        */

        ItemStack filler =
                RSMenuUtils.createFillerPane();

        for (int i = 0; i < menu.getSize(); i++) {

            menu.setItem(i, filler);

        }

        /*
        |--------------------------------------------------------------------------
        | CONVERT BACK
        |--------------------------------------------------------------------------
        */

        menu.setItem(
                2,
                RSMenuUtils.createMenuItem(
                        Material.COMPOSTER,
                        "§6Convert Back"
                )
        );

        /*
        |--------------------------------------------------------------------------
        | PAUSE / RESUME
        |--------------------------------------------------------------------------
        */

        boolean paused =
                MachineManager.isPaused(
                        block
                );

        MachineError error =
                MachineManager.getMachineError(
                        block
                );

        if (error != MachineError.NONE) {

            String errorMessage =
                    switch (error) {

                        case MISSING_TARGET ->
                                "§7Missing target container.";

                        case MISSING_SOURCE ->
                                "§7Missing source container.";

                        case INVALID_TARGET ->
                                "§7Target container is invalid.";

                        case INVALID_SOURCE ->
                                "§7Source container is invalid.";

                        case INVALID_CONFIGURATION ->
                                "§7Machine configuration is invalid.";

                        case SHAFT_BROKEN ->
                                "§7Lightning rod shaft is broken.";

                        default ->
                                "§7Unknown error.";

                    };

            menu.setItem(
                    6,
                    RSMenuUtils.createMenuItem(

                            Material.RED_DYE,

                            "§cError",

                            List.of(
                                    errorMessage,
                                    "§7",
                                    "§7Machine is paused.",
                                    "§7Repair and recalibrate."
                            )

                    )
            );

        }
        else {

            menu.setItem(
                    6,
                    RSMenuUtils.createMenuItem(

                            paused
                                    ? Material.RED_DYE
                                    : Material.GREEN_DYE,

                            paused
                                    ? "§cPaused"
                                    : "§aRunning"

                    )
            );

        }

        /*
        |--------------------------------------------------------------------------
        | FILTER
        |--------------------------------------------------------------------------
        */

        menu.setItem(
                13,
                RSMenuUtils.createMenuItem(

                        Material.HOPPER,

                        "§eFilter",

                        List.of(
                                "§7Mode: §f"
                                        + MachineManager.getFilterMode(block)
                        )

                )
        );

        /*
        |--------------------------------------------------------------------------
        | ADMIN
        |--------------------------------------------------------------------------
        */

        if (player.hasPermission(
                RSConstants.ADMIN_PERMISSION
        )) {

            menu.setItem(
                    4,
                    RSMenuUtils.createMenuItem(
                            Material.COMMAND_BLOCK,
                            "§cAdmin"
                    )
            );

        }

        player.openInventory(menu);

    }

}