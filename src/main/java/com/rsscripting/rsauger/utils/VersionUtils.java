package com.rsscripting.rsauger.utils;

import com.rsscripting.rsauger.RSAuger;

public class VersionUtils {

    /*
    |--------------------------------------------------------------------------
    | GET INSTALLED VERSION
    |--------------------------------------------------------------------------
    */

    public static String getVersion() {

        return RSAuger.getInstance()
                .getDescription()
                .getVersion();
    }

}