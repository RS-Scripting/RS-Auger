package com.rsscripting.rsauger.gui;

import com.rsscripting.rsauger.utils.RSMenuUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RSConversionMenu {

    public static final String MENU_TITLE = "Activate Auger";

    public static void open(
            Player player
    ) {

        Inventory menu =
                Bukkit.createInventory(
                        new RSMenuHolder(
                                "CONVERT"
                        ),
                        9,
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
        | CONVERT BUTTON
        |--------------------------------------------------------------------------
        */

        menu.setItem(
                4,
                RSMenuUtils.createMenuItem(
                        Material.CRAFTING_TABLE,
                        "§aConvert"
                )
        );

        player.openInventory(menu);

    }

}