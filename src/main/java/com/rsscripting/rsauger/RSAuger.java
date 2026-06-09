package com.rsscripting.rsauger;

import com.rsscripting.rsauger.commands.RSPluginCommand;
import com.rsscripting.rsauger.commands.RSTabCompleter;
import com.rsscripting.rsauger.config.ConfigManager;
import com.rsscripting.rsauger.database.DatabaseManager;
import com.rsscripting.rsauger.listeners.*;
import com.rsscripting.rsauger.utils.GitHubUpdateChecker;
import com.rsscripting.rsauger.utils.RSConstants;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class RSAuger
        extends JavaPlugin {

    private static RSAuger instance;

    @Override
    public void onEnable() {

        saveDefaultConfig();


        instance = this;

        /*
        |--------------------------------------------------------------------------
        | CONFIG
        |--------------------------------------------------------------------------
        */

        ConfigManager.setup();

         /*
        |--------------------------------------------------------------------------
        | Database
        |--------------------------------------------------------------------------
        */

        DatabaseManager.initialize(
                this
        );

        /*
        |--------------------------------------------------------------------------
        | LISTENERS
        |--------------------------------------------------------------------------
        */

        /*  Convert */
        RSConvertListener
                conversionListener =
                new RSConvertListener();

        getServer().getPluginManager().registerEvents(

                conversionListener,

                this

        );

        /*  Main Menu */
        getServer().getPluginManager().registerEvents(

                new RSMenuListener(
                        conversionListener
                ),

                this

        );

        /*  Admin */
        RSAdminListener adminListener =
                new RSAdminListener(
                        conversionListener
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        adminListener,
                        this
                );

        /*  Filter */
        RSFilterListener filterListener =
                new RSFilterListener(
                        conversionListener
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        filterListener,
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new RSBlockBreakListener(),
                        this
                );

        /*
        |--------------------------------------------------------------------------
        | COMMANDS
        |--------------------------------------------------------------------------
        */

        PluginCommand command =
                getCommand(
                        RSConstants.COMMAND_ALIAS
                );

        if (command != null) {

            command.setExecutor(
                    new RSPluginCommand()
            );

            command.setTabCompleter(
                    new RSTabCompleter()
            );

        }

        /*
        |--------------------------------------------------------------------------
        | UPDATE CHECKER
        |--------------------------------------------------------------------------
        */

        GitHubUpdateChecker.checkForUpdates();

        /*
        |--------------------------------------------------------------------------
        | ENABLE MESSAGE
        |--------------------------------------------------------------------------
        */

        getLogger().info(
                RSConstants.PLUGIN_NAME
                        + " Enabled"
        );

    }

    @Override
    public void onDisable() {

        getLogger().info(
                RSConstants.PLUGIN_NAME
                        + " Disabled"
        );

    }

    /*
    |--------------------------------------------------------------------------
    | GET INSTANCE
    |--------------------------------------------------------------------------
    */

    public static RSAuger getInstance() {

        return instance;

    }

}