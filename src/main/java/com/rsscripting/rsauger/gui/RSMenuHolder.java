package com.rsscripting.rsauger.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class RSMenuHolder
        implements InventoryHolder {

    private final String menuId;

    private int page = 1;

    public RSMenuHolder(
            String menuId
    ) {

        this.menuId = menuId;

    }

    public String getMenuId() {

        return menuId;

    }

    public int getPage() {

        return page;

    }

    public void setPage(
            int page
    ) {

        if (page < 1) {

            page = 1;

        }

        this.page = page;

    }

    @Override
    public @NotNull Inventory getInventory() {

        throw new UnsupportedOperationException(
                "RSMenuHolder does not store an inventory."
        );

    }

}