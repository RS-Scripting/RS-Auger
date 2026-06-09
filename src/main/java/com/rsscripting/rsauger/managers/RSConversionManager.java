package com.rsscripting.rsauger.managers;

import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;

import java.util.Map;

public class RSConversionManager {

    public static boolean containsFilterItem(
            Block block,
            ItemStack item
    ) {

        Map<Integer, ItemStack> filterItems =
                MachineManager.getAllFilterItems(
                        block
                );

        for (ItemStack filterItem
                : filterItems.values()) {

            if (filterItem == null) {
                continue;
            }

            if (filterItem.isSimilar(
                    item
            )) {

                return true;

            }

        }

        return false;

    }

}