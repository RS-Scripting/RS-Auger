package com.rsscripting.rsauger.listeners;

import com.rsscripting.rsauger.gui.RSFilterMenu;
import com.rsscripting.rsauger.gui.RSMainMenu;
import com.rsscripting.rsauger.gui.RSMenuHolder;
import com.rsscripting.rsauger.managers.MachineManager;
import com.rsscripting.rsauger.managers.RSConversionManager;
import com.rsscripting.rsauger.utils.RSKeys;
import com.rsscripting.rsauger.utils.RSMessageUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class RSFilterListener
        implements Listener {





    private final RSConvertListener
            conversionListener;

    public RSFilterListener(
            RSConvertListener conversionListener
    ) {

        this.conversionListener =
                conversionListener;

    }

    @EventHandler
    public void onInventoryClick(
            InventoryClickEvent event
    ) {

        if (!(event.getWhoClicked()
                instanceof Player player)) {
            return;
        }

        if (!(event.getInventory()
                .getHolder()
                instanceof RSMenuHolder holder)) {
            return;
        }

        if (!holder.getMenuId()
                .equals(
                        "FILTER"
                )) {
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
        | TOP ROW
        |--------------------------------------------------------------------------
        */

        if (event.getRawSlot() < 9) {

            event.setCancelled(true);

            /*
            |--------------------------------------------------------------------------
            | FILTER MODE
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 3) {

                String currentMode =
                        MachineManager.getFilterMode(
                                block
                        );

                String nextMode;

                if (currentMode.equals(
                        RSKeys.FILTER_ALLOW_ALL
                )) {

                    nextMode =
                            RSKeys.FILTER_WHITELIST;

                }

                else if (currentMode.equals(
                        RSKeys.FILTER_WHITELIST
                )) {

                    nextMode =
                            RSKeys.FILTER_BLACKLIST;

                }

                else {

                    nextMode =
                            RSKeys.FILTER_ALLOW_ALL;

                }

                MachineManager.setFilterMode(
                        block,
                        nextMode
                );

                RSFilterMenu.open(
                        player,
                        block
                );

                return;

            }

             /*
            |--------------------------------------------------------------------------
            | PREVIOUS PAGE
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 0
                    && event.getCurrentItem() != null
                    && event.getCurrentItem().getType()
                    == Material.ARROW) {

                if (holder.getPage() > 1) {

                    holder.setPage(
                            holder.getPage() - 1
                    );

                    RSFilterMenu.open(
                            player,
                            block,
                            holder.getPage()
                    );

                }

                return;

            }

            /*
            |--------------------------------------------------------------------------
            | NEXT PAGE
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 8
                    && event.getCurrentItem() != null
                    && event.getCurrentItem().getType()
                    == Material.ARROW) {

                int highestPage =
                        MachineManager.getHighestFilterPage(
                                block
                        );

                if (holder.getPage()
                        <= highestPage) {

                    holder.setPage(
                            holder.getPage() + 1
                    );

                    RSFilterMenu.open(
                            player,
                            block,
                            holder.getPage()
                    );

                }

                return;

            }

            /*
            |--------------------------------------------------------------------------
            | BACK
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 5) {

                RSMainMenu.open(
                        player,
                        block
                );

                return;

            }

            return;

        }

        if (event.getRawSlot() >= event.getInventory().getSize()) {
            return;
        }

        /*
        |--------------------------------------------------------------------------
        | FILTER STORAGE
        |--------------------------------------------------------------------------
        */

        event.setCancelled(true);

        if (event.getCursor().getType()
                != Material.AIR) {

            ItemStack filterItem =
                    event.getCursor().clone();

            filterItem.setAmount(1);

            if (RSConversionManager.containsFilterItem(
                    block,
                    filterItem
            )) {

                RSMessageUtils.info(
                        player,
                        "That item is already in the filter."
                );

                return;

            }

            event.getInventory().setItem(
                    event.getRawSlot(),
                    filterItem
            );

            MachineManager.saveFilterItem(
                    block,
                    holder.getPage(),
                    event.getRawSlot(),
                    filterItem
            );

            RSFilterMenu.open(
                    player,
                    block,
                    holder.getPage()
            );

            return;

        }

        event.getInventory().setItem(
                event.getRawSlot(),
                null
        );

        MachineManager.removeFilterItem(
                block,
                holder.getPage(),
                event.getRawSlot()
        );

        RSFilterMenu.open(
                player,
                block,
                holder.getPage()
        );

    }

}