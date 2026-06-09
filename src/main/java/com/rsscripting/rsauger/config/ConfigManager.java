package com.rsscripting.rsauger.config;

import com.rsscripting.rsauger.RSAuger;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static FileConfiguration config;

    /*
    |--------------------------------------------------------------------------
    | SETUP
    |--------------------------------------------------------------------------
    */

    public static void setup() {

        RSAuger.getInstance()
                .saveDefaultConfig();

        config =
                RSAuger.getInstance()
                        .getConfig();

    }

    /*
    |--------------------------------------------------------------------------
    | RELOAD
    |--------------------------------------------------------------------------
    */

    public static void reload() {

        RSAuger.getInstance()
                .reloadConfig();

        config =
                RSAuger.getInstance()
                        .getConfig();

    }

    /*
    |--------------------------------------------------------------------------
    | GET CONFIG
    |--------------------------------------------------------------------------
    */

    public static FileConfiguration get() {

        return config;

    }

}