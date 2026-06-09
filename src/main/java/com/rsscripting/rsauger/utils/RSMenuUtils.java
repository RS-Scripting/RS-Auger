package com.rsscripting.rsauger.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.kyori.adventure.text.Component;

import java.util.List;

public class RSMenuUtils {

    /*
    |--------------------------------------------------------------------------
    | CREATE FILLER PANE
    |--------------------------------------------------------------------------
    */

    public static ItemStack createFillerPane() {

        ItemStack item =
                new ItemStack(
                        RSConstants.MENU_FILLER_MATERIAL
                );

        ItemMeta meta =
                item.getItemMeta();

        if (meta != null) {

            meta.displayName(
                    Component.empty()
            );

            item.setItemMeta(meta);

        }

        return item;

    }

    /*
    |--------------------------------------------------------------------------
    | CREATE MENU ITEM
    |--------------------------------------------------------------------------
    */

    public static ItemStack createMenuItem(

            Material material,

            String name

    ) {

        ItemStack item =
                new ItemStack(material);

        ItemMeta meta =
                item.getItemMeta();

        if (meta != null) {

            meta.displayName(
                    Component.text(
                            name
                    )
            );

            item.setItemMeta(meta);

        }

        return item;

    }

    /*
    |--------------------------------------------------------------------------
    | CREATE MENU ITEM WITH LORE
    |--------------------------------------------------------------------------
    */

    public static ItemStack createMenuItem(

            Material material,

            String name,

            List<String> lore

    ) {

        ItemStack item =
                new ItemStack(material);

        ItemMeta meta =
                item.getItemMeta();

        if (meta != null) {

            meta.displayName(
                    Component.text(
                            name
                    )
            );

            meta.lore(
                    lore.stream()
                            .map(Component::text)
                            .toList()
            );

            item.setItemMeta(meta);

        }

        return item;

    }

}