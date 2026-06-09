package com.rsscripting.rsauger.database;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;

public class DatabaseManager {

    private static JavaPlugin plugin;

    private static String databaseUrl;

    public static void initialize(
            JavaPlugin pluginInstance
    ) {

        plugin = pluginInstance;

        createDatabase();

        createTables();

        updateDatabase();

    }

    private static void createDatabase() {

        try {

            File databaseFile =
                    new File(
                            plugin.getDataFolder(),
                            "database.db"
                    );

            if (!plugin.getDataFolder().exists()) {

                if (!plugin.getDataFolder().mkdirs()) {

                    plugin.getLogger().severe(
                            "Failed to create plugin data folder."
                    );

                    return;

                }

            }

            databaseUrl =
                    "jdbc:sqlite:"
                            + databaseFile.getAbsolutePath();

            Connection connection =
                    DriverManager.getConnection(
                            databaseUrl
                    );

            connection.close();

            plugin.getLogger().info(
                    "SQLite database initialized."
            );

        }

        catch (Exception e) {

            plugin.getLogger().log(
                    Level.SEVERE,
                    "Failed to initialize database.",
                    e
            );

        }

    }

    private static void createTables() {

        try (

                Connection connection =
                        DriverManager.getConnection(
                                databaseUrl
                        )

        ) {

            connection.createStatement().execute(
                    """
                    CREATE TABLE IF NOT EXISTS machines (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        world TEXT NOT NULL,
                        x INTEGER NOT NULL,
                        y INTEGER NOT NULL,
                        z INTEGER NOT NULL,
                        owner_uuid TEXT,
                        owner_name TEXT,
                        machine_type TEXT,
                        paused INTEGER DEFAULT 0,
                        state TEXT DEFAULT 'ACTIVE',
                        filter_mode TEXT DEFAULT 'ALLOW_ALL',
                        created_at TEXT                                              
                    )
                    """
            );

            connection.createStatement().execute(
                    """
                    CREATE TABLE IF NOT EXISTS machine_filters (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        machine_id INTEGER NOT NULL,
                        page INTEGER NOT NULL,
                        slot INTEGER NOT NULL,
                        item_data TEXT NOT NULL
                    )
                    """
            );

            plugin.getLogger().info(
                    "Database tables initialized."
            );

        }

        catch (Exception e) {

            plugin.getLogger().log(
                    Level.SEVERE,
                    "Failed to create tables.",
                    e
            );

        }

    }

    private static void updateDatabase() {

        try (

                Connection connection =
                        DriverManager.getConnection(
                                databaseUrl
                        )

        ) {

            ensureColumnExists(
                    connection,
                    "machines",
                    "state",
                    "TEXT DEFAULT 'ACTIVE'"
            );

            ensureColumnExists(
                    connection,
                    "machines",
                    "error_reason",
                    "TEXT"
            );

        }

        catch (Exception e) {

            plugin.getLogger().log(
                    Level.SEVERE,
                    "Failed to update database.",
                    e
            );

        }

    }

    private static void ensureColumnExists(
            Connection connection,
            String table,
            String column,
            String definition
    ) {

        try (

                var columns =
                        connection
                                .getMetaData()
                                .getColumns(
                                        null,
                                        null,
                                        table,
                                        column
                                )

        ) {

            if (!columns.next()) {

                connection.createStatement().execute(
                        "ALTER TABLE "
                                + table
                                + " ADD COLUMN "
                                + column
                                + " "
                                + definition
                );

            }

        }

        catch (Exception e) {

            plugin.getLogger().log(
                    Level.SEVERE,
                    "Failed updating column "
                            + column,
                    e
            );

        }

    }

    public static Connection getConnection()
            throws Exception {

        return DriverManager.getConnection(
                databaseUrl
        );

    }

}