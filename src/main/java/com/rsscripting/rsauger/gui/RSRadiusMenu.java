package com.rsscripting.rsauger.gui;

import com.rsscripting.rsauger.managers.MachineManager;
import com.rsscripting.rsauger.utils.RSMenuUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RSRadiusMenu {


    public static void open(
            Player player,
            Block block
    ) {

        Inventory menu =
                Bukkit.createInventory(
                        new RSMenuHolder(
                                "RADIUS"
                        ),
                        18,
                        Component.text(
                                "Radius: "
                                        + MachineManager.getRadius(
                                        block
                                )
                        ).color(NamedTextColor.BLUE)
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
| BACK
|--------------------------------------------------------------------------
*/

        menu.setItem(
                4,
                RSMenuUtils.createMenuItem(

                        Material.OAK_DOOR,

                        "§eBack"

                )
        );

        /*
        |--------------------------------------------------------------------------
        | CURRENT RADIUS
        |--------------------------------------------------------------------------
        */

        menu.setItem(
                13,
                RSMenuUtils.createMenuItem(

                        Material.COMPASS,

                        "§bCurrent Radius",

                        List.of(
                                "§7Radius: §f"
                                        + MachineManager.getRadius(
                                        block
                                )
                        )

                )
        );

        /*
        |--------------------------------------------------------------------------
        | NEGATIVE BUTTONS
        |--------------------------------------------------------------------------
        */

        menu.setItem(
                10,
                RSMenuUtils.createMenuItem(
                        Material.RED_WOOL,
                        "§c-10"
                )
        );

        menu.setItem(
                11,
                RSMenuUtils.createMenuItem(
                        Material.RED_WOOL,
                        "§c-5"
                )
        );

        menu.setItem(
                12,
                RSMenuUtils.createMenuItem(
                        Material.RED_WOOL,
                        "§c-1"
                )
        );

        /*
        |--------------------------------------------------------------------------
        | POSITIVE BUTTONS
        |--------------------------------------------------------------------------
        */

        menu.setItem(
                14,
                RSMenuUtils.createMenuItem(
                        Material.GREEN_WOOL,
                        "§a+1"
                )
        );

        menu.setItem(
                15,
                RSMenuUtils.createMenuItem(
                        Material.GREEN_WOOL,
                        "§a+5"
                )
        );

        menu.setItem(
                16,
                RSMenuUtils.createMenuItem(
                        Material.GREEN_WOOL,
                        "§a+10"
                )
        );

        player.openInventory(menu);

    }

}