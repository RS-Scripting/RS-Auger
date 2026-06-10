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
    | RELOAD
    |--------------------------------------------------------------------------
    */

    public static void reload() {

        RSAuger.getInstance()
                .reloadConfig();

    }

}