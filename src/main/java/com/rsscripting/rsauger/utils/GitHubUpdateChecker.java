package com.rsscripting.rsauger.utils;

import com.rsscripting.rsauger.RSAuger;
import com.rsscripting.rsauger.utils.RSConstants;
import com.rsscripting.rsauger.utils.VersionUtils;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

public class GitHubUpdateChecker {

    /*
    |--------------------------------------------------------------------------
    | UPDATE STATUS
    |--------------------------------------------------------------------------
    */

    private static String latestVersion = null;

    private static String updateStatus =
            "Checking...";

    /*
    |--------------------------------------------------------------------------
    | CHECK FOR UPDATES
    |--------------------------------------------------------------------------
    */

    public static void checkForUpdates() {

        Bukkit.getScheduler().runTaskAsynchronously(

                RSAuger.getInstance(),

                () -> {

                    try {

                        String apiURL =
                                "https://raw.githubusercontent.com/"
                                        + RSConstants.GITHUB_USER
                                        + "/"
                                        + RSConstants.GITHUB_REPOSITORY
                                        + "/master/pom.xml";

                        HttpURLConnection connection =
                                (HttpURLConnection)
                                        URI.create(apiURL)
                                                .toURL()
                                                .openConnection();

                        connection.setRequestMethod("GET");

                        BufferedReader reader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                connection.getInputStream()
                                        )
                                );

                        StringBuilder response =
                                new StringBuilder();

                        String line;

                        while ((line = reader.readLine()) != null) {

                            response.append(line);

                        }

                        reader.close();

                        String pom =
                                response.toString();

                        latestVersion =
                                pom.split("<version>")[1]
                                        .split("</version>")[0]
                                        .trim();

                        String currentVersion =
                                VersionUtils.getVersion();

                        /*
                        |--------------------------------------------------------------------------
                        | UPDATE AVAILABLE
                        |--------------------------------------------------------------------------
                        */

                        if (latestVersion == null) {

                            updateStatus =
                                    "Unable To Check";

                        }
                        else if (
                                compareVersions(
                                        latestVersion,
                                        currentVersion
                                ) > 0
                        ) {

                            updateStatus =
                                    "Update Available";

                            Bukkit.getConsoleSender().sendMessage(

                                    RSConstants.PREFIX
                                            + "Update available: "
                                            + latestVersion
                                            + " (Current: "
                                            + currentVersion
                                            + ")"

                            );

                        }

                        /*
                        |--------------------------------------------------------------------------
                        | UP TO DATE
                        |--------------------------------------------------------------------------
                        */

                        else {

                            updateStatus =
                                    "Up To Date";

                            Bukkit.getConsoleSender().sendMessage(

                                    RSConstants.PREFIX
                                            + "RS-Auger is up to date."

                            );

                        }

                    }

                    catch (Exception exception) {

                        latestVersion = null;

                        updateStatus =
                                "Unable To Check";

                        Bukkit.getConsoleSender().sendMessage(

                                RSConstants.PREFIX
                                        + "Unable to check for updates."

                        );

                    }

                }

        );

    }

    /*
    |--------------------------------------------------------------------------
    | GET LATEST VERSION
    |--------------------------------------------------------------------------
    */

    public static String getLatestVersion() {

        return latestVersion;

    }

    /*
    |--------------------------------------------------------------------------
    | GET UPDATE STATUS
    |--------------------------------------------------------------------------
    */

    public static String getUpdateStatus() {

        return updateStatus;

    }

    /*
    |--------------------------------------------------------------------------
    | VERSION COMPARISON
    |--------------------------------------------------------------------------
    */

    private static int compareVersions(
            String version1,
            String version2
    ) {

        String[] v1 =
                version1.split("\\.");

        String[] v2 =
                version2.split("\\.");

        int length =
                Math.max(
                        v1.length,
                        v2.length
                );

        for (int i = 0; i < length; i++) {

            int num1 =
                    i < v1.length
                            ? Integer.parseInt(v1[i])
                            : 0;

            int num2 =
                    i < v2.length
                            ? Integer.parseInt(v2[i])
                            : 0;

            if (num1 < num2) {

                return -1;

            }

            if (num1 > num2) {

                return 1;

            }

        }

        return 0;

    }

}
