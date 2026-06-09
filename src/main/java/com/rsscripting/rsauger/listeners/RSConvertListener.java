package com.rsscripting.rsauger.listeners;

import com.rsscripting.rsauger.gui.RSConversionMenu;
import com.rsscripting.rsauger.gui.RSMainMenu;
import com.rsscripting.rsauger.managers.MachineManager;
import com.rsscripting.rsauger.utils.RSConstants;
import com.rsscripting.rsauger.gui.session.GuiSession;
import com.rsscripting.rsauger.gui.session.GuiSessionManager;

import com.rsscripting.rsauger.utils.RSMessageUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RSConvertListener
        implements Listener {

    @EventHandler
    public void onPlayerInteractBlock(
            PlayerInteractEvent event
    ) {

        Player player =
                event.getPlayer();

        Block block =
                event.getClickedBlock();

        if (block == null) {
            return;
        }

        /*
        |--------------------------------------------------------------------------
        | REQUIRE SNEAKING
        |--------------------------------------------------------------------------
        */

        if (!player.isSneaking()) {
            return;
        }

        /*
        |--------------------------------------------------------------------------
        | VALIDATE TARGET ENTITY
        |--------------------------------------------------------------------------
        */

        if (block.getType() !=
                RSConstants.REQUIRED_TARGET_BLOCK) {
            return;
        }

        /*
        |--------------------------------------------------------------------------
        | VALIDATE HELD ITEM
        |--------------------------------------------------------------------------
        */

        ItemStack heldItem =
                player.getInventory()
                        .getItemInMainHand();

        if (heldItem.getType() !=
                RSConstants.REQUIRED_HELD_ITEM) {
            return;
        }

        /*
        |--------------------------------------------------------------------------
        | CANCEL VANILLA ACTION
        |--------------------------------------------------------------------------
        */

        event.setCancelled(true);

        /*
        |--------------------------------------------------------------------------
        | STORE SELECTED ENTITY
        |--------------------------------------------------------------------------
        */

        GuiSession session =
                GuiSessionManager.getSession(
                        player
                );

        session.setSelectedBlock(
                block
        );

        /*
|--------------------------------------------------------------------------
| ALREADY CONVERTED
|--------------------------------------------------------------------------
*/

        if (MachineManager.machineExists(
                block
        )) {

    /*
    |--------------------------------------------------------------------------
    | OWNER CHECK
    |--------------------------------------------------------------------------
    */

            if (!MachineManager.isOwner(
                    block,
                    player
            )

                    &&

                    !player.hasPermission(
                            RSConstants.BYPASS_PERMISSION
                    )

            ) {

                RSMessageUtils.error(
                        player,
                        "You are not the owner."
                );

                return;

            }

    /*
    |--------------------------------------------------------------------------
    | OPEN MAIN MENU
    |--------------------------------------------------------------------------
    */

            RSMainMenu.open(
                    player,
                    block
            );

            return;

        }

        /*
        |--------------------------------------------------------------------------
        | OPEN CONVERSION MENU
        |--------------------------------------------------------------------------
        */

        RSConversionMenu.open(player);

    }

    /*
    |--------------------------------------------------------------------------
    | GET SELECTED ENTITY
    |--------------------------------------------------------------------------
    */

    public Block getSelectedBlock(
            Player player
    ) {

        GuiSession session =
                GuiSessionManager.getSession(
                        player
                );

        return session.getSelectedBlock();

    }

    /*
    |--------------------------------------------------------------------------
    | Convert Back
    |--------------------------------------------------------------------------
    */

    public void convertBack(
            Player player
    ) {

        GuiSession session =
                GuiSessionManager.getSession(
                        player
                );

        Block block =
                session.getSelectedBlock();

        if (block == null) {

            player.closeInventory();

            return;

        }

        MachineManager.deleteMachine(
                block
        );

        RSMessageUtils.success(
                player,
                "Machine removed."
        );

        player.closeInventory();

    }

}