package com.rsscripting.rsauger.commands;

import com.rsscripting.rsauger.config.ConfigManager;
import com.rsscripting.rsauger.utils.RSConstants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RSPluginCommand
        implements CommandExecutor {



    @Override
    public boolean onCommand(

            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args

    ) {

        /*
        |--------------------------------------------------------------------------
        | BASE COMMAND
        |--------------------------------------------------------------------------
        */

        if (args.length == 0) {

            sender.sendMessage(
                    RSConstants.PREFIX
                            + "Running "
                            + RSConstants.PLUGIN_NAME
            );

            return true;

        }

        /*
        |--------------------------------------------------------------------------
        | RELOAD COMMAND
        |--------------------------------------------------------------------------
        */

        if (args[0].equalsIgnoreCase(
                "reload"
        )) {

            if (!sender.hasPermission(
                    RSConstants.ADMIN_PERMISSION
            )) {

                sender.sendMessage(
                        RSConstants.PREFIX
                                + "No permission."
                );

                return true;

            }

            ConfigManager.reload();

            sender.sendMessage(
                    RSConstants.PREFIX
                            + "Configuration reloaded."
            );

            return true;

        }

        /*
        |--------------------------------------------------------------------------
        | VERSION COMMAND
        |--------------------------------------------------------------------------
        */

        if (args[0].equalsIgnoreCase(
                "version"
        )) {

            sender.sendMessage(
                    RSConstants.PREFIX
                            + RSConstants.PLUGIN_NAME
                            + " v"
                            + RSConstants.VERSION
            );

            return true;

        }

        return true;

    }

}