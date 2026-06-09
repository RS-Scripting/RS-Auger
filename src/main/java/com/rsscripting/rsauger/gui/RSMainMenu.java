package com.rsscripting.rsauger.gui;

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

        /*
        |--------------------------------------------------------------------------
        | RADIUS
        |--------------------------------------------------------------------------
        */

        menu.setItem(
                11,
                RSMenuUtils.createMenuItem(

                        Material.COMPASS,

                        "§bRadius",

                        List.of(
                                "§7Current Radius: §f"
                                        + MachineManager.getRadius(block)
                        )

                )
        );

        /*
        |--------------------------------------------------------------------------
        | FILTER
        |--------------------------------------------------------------------------
        */

        menu.setItem(
                15,
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