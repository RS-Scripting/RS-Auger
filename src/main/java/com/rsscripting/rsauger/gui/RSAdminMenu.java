package com.rsscripting.rsauger.gui;


import com.rsscripting.rsauger.utils.RSMenuUtils;
import com.rsscripting.rsauger.utils.VersionUtils;
import com.rsscripting.rsauger.utils.GitHubUpdateChecker;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import net.kyori.adventure.text.Component;

import java.util.List;

public class RSAdminMenu {

    public static final String MENU_TITLE = "Admin";

    public static void open(
            Player player
    ) {

        Inventory menu =
                Bukkit.createInventory(
                        new RSMenuHolder(
                                "ADMIN"
                        ),
                        18,
                        Component.text(
                                MENU_TITLE
                        ).color(
                                NamedTextColor.GOLD
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
        | RELOAD
        |--------------------------------------------------------------------------
        */

        menu.setItem(
                12,
                RSMenuUtils.createMenuItem(

                        Material.ENCHANTED_BOOK,

                        "§cReload Plugin"

                )
        );

        /*
        |--------------------------------------------------------------------------
        | VERSION
        |--------------------------------------------------------------------------
        */

        menu.setItem(
                14,
                RSMenuUtils.createMenuItem(

                        Material.BOOK,

                        "§ePlugin Version",

                        List.of(

                                "§7Installed: §f"
                                        + VersionUtils.getVersion(),

                                "",

                                "§7Status: §f"
                                        + GitHubUpdateChecker.getUpdateStatus(),

                                "",

                                "§7Latest: §f"
                                        + (
                                        GitHubUpdateChecker.getLatestVersion()
                                                == null
                                                ? "Unknown"
                                                : GitHubUpdateChecker.getLatestVersion()
                                )

                        )

                )
        );

        player.openInventory(menu);

    }

}