package com.rsscripting.rsauger.gui;

import com.rsscripting.rsauger.managers.MachineManager;
import com.rsscripting.rsauger.utils.RSKeys;
import com.rsscripting.rsauger.utils.RSMenuUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class RSFilterMenu {

    public static final String MENU_TITLE =
            "Filter";

    public static void open(
            Player player,
            Block block
    ) {

        open(
                player,
                block,
                1
        );

    }

    public static void open(
            Player player,
            Block block,
            int page
    ) {

        RSMenuHolder holder =
                new RSMenuHolder(
                        "FILTER"
                );

        holder.setPage(
                page
        );

        Inventory menu =
                Bukkit.createInventory(
                        holder,
                        54,
                        Component.text(
                                MENU_TITLE
                                        + " ["
                                        + holder.getPage()
                                        + "]"
                        ).color(
                                NamedTextColor.BLUE
                        )
                );

        /*
        |--------------------------------------------------------------------------
        | TOP ROW FILLER
        |--------------------------------------------------------------------------
        */

        ItemStack filler =
                RSMenuUtils.createFillerPane();

        for (int i = 0; i < 9; i++) {

            menu.setItem(
                    i,
                    filler
            );

        }

/*
|--------------------------------------------------------------------------
| FILTER MODE
|--------------------------------------------------------------------------
*/

        String filterMode =
                MachineManager.getFilterMode(
                        block
                );

        Material filterMaterial;

        String filterName;

        if (filterMode.equals(
                RSKeys.FILTER_WHITELIST
        )) {

            filterMaterial =
                    Material.WHITE_WOOL;

            filterName =
                    "§aWhitelist";

        }

        else if (filterMode.equals(
                RSKeys.FILTER_BLACKLIST
        )) {

            filterMaterial =
                    Material.BLACK_WOOL;

            filterName =
                    "§cBlacklist";

        }

        else {

            filterMaterial =
                    Material.LIME_WOOL;

            filterName =
                    "§aAllow All";

        }

        menu.setItem(
                3,
                RSMenuUtils.createMenuItem(
                        filterMaterial,
                        filterName
                )
        );

                /*
        |--------------------------------------------------------------------------
        | PREVIOUS PAGE
        |--------------------------------------------------------------------------
        */

        if (holder.getPage() > 1) {

            menu.setItem(
                    0,
                    RSMenuUtils.createMenuItem(
                            Material.ARROW,
                            "§ePrevious Page (" + holder.getPage() + ")"
                    )
            );

        }

                /*
        |--------------------------------------------------------------------------
        | FILTER ITEMS
        |--------------------------------------------------------------------------
        */

        Map<Integer, ItemStack> filterItems =
                MachineManager.getFilterItems(
                        block,
                        holder.getPage()
                );

        for (Integer slot : filterItems.keySet()) {

            menu.setItem(
                    slot,
                    filterItems.get(
                            slot
                    )
            );

        }

                /*
        |--------------------------------------------------------------------------
        | NEXT PAGE
        |--------------------------------------------------------------------------
        */

        int highestPage =
                MachineManager.getHighestFilterPage(
                        block
                );

        if (holder.getPage()
                <= highestPage) {

            menu.setItem(
                    8,
                    RSMenuUtils.createMenuItem(
                            Material.ARROW,
                            "§eNext Page (" + holder.getPage() + ")"
                    )
            );

        }

        /*
        |--------------------------------------------------------------------------
        | BACK
        |--------------------------------------------------------------------------
        */

        menu.setItem(
                5,
                RSMenuUtils.createMenuItem(

                        Material.OAK_DOOR,

                        "§eBack"

                )
        );

        player.openInventory(
                menu
        );

    }

}