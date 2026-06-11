package com.rsscripting.rsauger.config;

import com.rsscripting.rsauger.RSAuger;

public class ConfigManager {


    /*
    |--------------------------------------------------------------------------
    | SETUP
    |--------------------------------------------------------------------------
    */

    public static void setup() {

        RSAuger.getInstance()
                .saveDefaultConfig();

    }

    /*
    |--------------------------------------------------------------------------
    | max-machine-length
    |--------------------------------------------------------------------------
    */

    public static int getMaxMachineLength() {
        int configured = RSAuger.getInstance()
                .getConfig()
                .getInt("max-machine-length", 16);

        if (configured < 1) {
            return 1;
        }

        return Math.min(configured, 16);
    }

    /*
    |--------------------------------------------------------------------------
    | RELOAD
    |--------------------------------------------------------------------------
    */

    public static void reload() {

        RSAuger.getInstance()
                .reloadConfig();

    }

}