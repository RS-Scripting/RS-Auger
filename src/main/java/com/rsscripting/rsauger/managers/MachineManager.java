package com.rsscripting.rsauger.managers;

import com.rsscripting.rsauger.database.DatabaseManager;
import com.rsscripting.rsauger.machine.AugerEndpoints;
import com.rsscripting.rsauger.RSAuger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.World;


import com.rsscripting.rsauger.machine.MachineState;
import com.rsscripting.rsauger.machine.MachineError;

public class MachineManager {

    public static boolean machineExists(
            Block block
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT id
                                FROM machines
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                LIMIT 1
                                """
                        )

        ) {

            statement.setString(
                    1,
                    block.getWorld().getName()
            );

            statement.setInt(
                    2,
                    block.getX()
            );

            statement.setInt(
                    3,
                    block.getY()
            );

            statement.setInt(
                    4,
                    block.getZ()
            );

            ResultSet result =
                    statement.executeQuery();

            return result.next();

        }

        catch (Exception e) {

            logException(
                    e
            );

            return false;

        }

    }

    public static void insertMachine(
            Block block
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                INSERT INTO machines (
                                    world,
                                    x,
                                    y,
                                    z,
                                    created_at
                                )
                                VALUES (
                                    ?, ?, ?, ?, datetime('now')
                                )
                                """
                        )

        ) {

            statement.setString(
                    1,
                    block.getWorld().getName()
            );

            statement.setInt(
                    2,
                    block.getX()
            );

            statement.setInt(
                    3,
                    block.getY()
            );

            statement.setInt(
                    4,
                    block.getZ()
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

    }

    public static void setEndpoints(
            Block machine,
            Block source,
            Block destination
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                UPDATE machines
                                SET source_world = ?,
                                    source_x = ?,
                                    source_y = ?,
                                    source_z = ?,
                                    destination_world = ?,
                                    destination_x = ?,
                                    destination_y = ?,
                                    destination_z = ?
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                """
                        )

        ) {

            statement.setString(
                    1,
                    source.getWorld().getName()
            );

            statement.setInt(
                    2,
                    source.getX()
            );

            statement.setInt(
                    3,
                    source.getY()
            );

            statement.setInt(
                    4,
                    source.getZ()
            );

            statement.setString(
                    5,
                    destination.getWorld().getName()
            );

            statement.setInt(
                    6,
                    destination.getX()
            );

            statement.setInt(
                    7,
                    destination.getY()
            );

            statement.setInt(
                    8,
                    destination.getZ()
            );

            statement.setString(
                    9,
                    machine.getWorld().getName()
            );

            statement.setInt(
                    10,
                    machine.getX()
            );

            statement.setInt(
                    11,
                    machine.getY()
            );

            statement.setInt(
                    12,
                    machine.getZ()
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

    }

    public static void deleteMachine(
            Block block
    ) {

        int machineId =
                getMachineId(
                        block
                );

        try (

                Connection connection =
                        DatabaseManager.getConnection()

        ) {

            if (machineId != -1) {

                try (

                        PreparedStatement filterStatement =
                                connection.prepareStatement(
                                        """
                                        DELETE FROM machine_filters
                                        WHERE machine_id = ?
                                        """
                                )

                ) {

                    filterStatement.setInt(
                            1,
                            machineId
                    );

                    filterStatement.executeUpdate();

                }

            }

            try (

                    PreparedStatement machineStatement =
                            connection.prepareStatement(
                                    """
                                    DELETE FROM machines
                                    WHERE world = ?
                                    AND x = ?
                                    AND y = ?
                                    AND z = ?
                                    """
                            )

            ) {

                machineStatement.setString(
                        1,
                        block.getWorld().getName()
                );

                machineStatement.setInt(
                        2,
                        block.getX()
                );

                machineStatement.setInt(
                        3,
                        block.getY()
                );

                machineStatement.setInt(
                        4,
                        block.getZ()
                );

                machineStatement.executeUpdate();

            }

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

    }

    public static void setOwner(
            Block block,
            Player player
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                UPDATE machines
                                SET owner_uuid = ?,
                                    owner_name = ?
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                """
                        )

        ) {

            statement.setString(
                    1,
                    player.getUniqueId().toString()
            );

            statement.setString(
                    2,
                    player.getName()
            );

            statement.setString(
                    3,
                    block.getWorld().getName()
            );

            statement.setInt(
                    4,
                    block.getX()
            );

            statement.setInt(
                    5,
                    block.getY()
            );

            statement.setInt(
                    6,
                    block.getZ()
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

    }

    public static boolean isOwner(
            Block block,
            Player player
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT owner_uuid
                                FROM machines
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                LIMIT 1
                                """
                        )

        ) {

            statement.setString(
                    1,
                    block.getWorld().getName()
            );

            statement.setInt(
                    2,
                    block.getX()
            );

            statement.setInt(
                    3,
                    block.getY()
            );

            statement.setInt(
                    4,
                    block.getZ()
            );

            ResultSet result =
                    statement.executeQuery();

            if (!result.next()) {

                return false;

            }

            String ownerUuid =
                    result.getString(
                            "owner_uuid"
                    );

            if (ownerUuid == null) {

                return false;

            }

            return UUID.fromString(
                    ownerUuid
            ).equals(
                    player.getUniqueId()
            );

        }

        catch (Exception e) {

            logException(
                    e
            );

            return false;

        }

    }

    /*
    |  Set End points
     */

    public static void setEndpoints(
            Block machine,
            AugerEndpoints endpoints
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                UPDATE machines
                                SET source_world = ?,
                                    source_x = ?,
                                    source_y = ?,
                                    source_z = ?,
                                    destination_world = ?,
                                    destination_x = ?,
                                    destination_y = ?,
                                    destination_z = ?
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                """
                        )

        ) {

            Block source =
                    endpoints.getSource();

            Block destination =
                    endpoints.getDestination();

            statement.setString(
                    1,
                    source.getWorld().getName()
            );

            statement.setInt(
                    2,
                    source.getX()
            );

            statement.setInt(
                    3,
                    source.getY()
            );

            statement.setInt(
                    4,
                    source.getZ()
            );

            statement.setString(
                    5,
                    destination.getWorld().getName()
            );

            statement.setInt(
                    6,
                    destination.getX()
            );

            statement.setInt(
                    7,
                    destination.getY()
            );

            statement.setInt(
                    8,
                    destination.getZ()
            );

            statement.setString(
                    9,
                    machine.getWorld().getName()
            );

            statement.setInt(
                    10,
                    machine.getX()
            );

            statement.setInt(
                    11,
                    machine.getY()
            );

            statement.setInt(
                    12,
                    machine.getZ()
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

    }

    /*
    |  Get Endpoints from database
    */

    public static AugerEndpoints getEndpoints(
            Block machine
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT
                                    source_world,
                                    source_x,
                                    source_y,
                                    source_z,
                                    destination_world,
                                    destination_x,
                                    destination_y,
                                    destination_z
                                FROM machines
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                LIMIT 1
                                """
                        )

        ) {

            statement.setString(
                    1,
                    machine.getWorld().getName()
            );

            statement.setInt(
                    2,
                    machine.getX()
            );

            statement.setInt(
                    3,
                    machine.getY()
            );

            statement.setInt(
                    4,
                    machine.getZ()
            );

            ResultSet result =
                    statement.executeQuery();

            if (!result.next()) {

                return null;

            }

            String sourceWorld =
                    result.getString(
                            "source_world"
                    );

            String destinationWorld =
                    result.getString(
                            "destination_world"
                    );

            if (sourceWorld == null
                    || destinationWorld == null) {

                return null;

            }

            World sourceBukkitWorld =
                    Bukkit.getWorld(
                            sourceWorld
                    );

            World destinationBukkitWorld =
                    Bukkit.getWorld(
                            destinationWorld
                    );

            if (sourceBukkitWorld == null
                    || destinationBukkitWorld == null) {

                return null;

            }

            Block source =
                    sourceBukkitWorld.getBlockAt(
                            result.getInt(
                                    "source_x"
                            ),
                            result.getInt(
                                    "source_y"
                            ),
                            result.getInt(
                                    "source_z"
                            )
                    );

            Block destination =
                    destinationBukkitWorld.getBlockAt(
                            result.getInt(
                                    "destination_x"
                            ),
                            result.getInt(
                                    "destination_y"
                            ),
                            result.getInt(
                                    "destination_z"
                            )
                    );

            return new AugerEndpoints(
                    source,
                    destination
            );

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

        return null;

    }

    /*
    |  Set machine to "Paused"
    */

    public static void setPaused(
            Block block,
            boolean paused
    ) {

        if (paused) {

            setMachineState(
                    block,
                    MachineState.PAUSED
            );

        }

        else {

            setMachineState(
                    block,
                    MachineState.ACTIVE
            );

        }

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                UPDATE machines
                                SET paused = ?
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                """
                        )

        ) {

            statement.setBoolean(
                    1,
                    paused
            );

            statement.setString(
                    2,
                    block.getWorld().getName()
            );

            statement.setInt(
                    3,
                    block.getX()
            );

            statement.setInt(
                    4,
                    block.getY()
            );

            statement.setInt(
                    5,
                    block.getZ()
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

    }

    public static boolean isPaused(
            Block block
    ) {

        return getMachineState(
                block
        ) != MachineState.ACTIVE;

    }

    public static void setFilterMode(
            Block block,
            String filterMode
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                UPDATE machines
                                SET filter_mode = ?
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                """
                        )

        ) {

            statement.setString(
                    1,
                    filterMode
            );

            statement.setString(
                    2,
                    block.getWorld().getName()
            );

            statement.setInt(
                    3,
                    block.getX()
            );

            statement.setInt(
                    4,
                    block.getY()
            );

            statement.setInt(
                    5,
                    block.getZ()
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

    }

    public static String getFilterMode(
            Block block
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT filter_mode
                                FROM machines
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                LIMIT 1
                                """
                        )

        ) {

            statement.setString(
                    1,
                    block.getWorld().getName()
            );

            statement.setInt(
                    2,
                    block.getX()
            );

            statement.setInt(
                    3,
                    block.getY()
            );

            statement.setInt(
                    4,
                    block.getZ()
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                return result.getString(
                        "filter_mode"
                );

            }

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

        return "ALLOW_ALL";

    }

    public static int getMachineId(
            Block block
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT id
                                FROM machines
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                LIMIT 1
                                """
                        )

        ) {

            statement.setString(
                    1,
                    block.getWorld().getName()
            );

            statement.setInt(
                    2,
                    block.getX()
            );

            statement.setInt(
                    3,
                    block.getY()
            );

            statement.setInt(
                    4,
                    block.getZ()
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                return result.getInt(
                        "id"
                );

            }

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

        return -1;

    }

    public static void removeFilterItem(
            Block block,
            int page,
            int slot
    ) {

        int machineId =
                getMachineId(
                        block
                );

        if (machineId == -1) {

            return;

        }

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                DELETE FROM machine_filters
                                WHERE machine_id = ?
                                AND page = ?
                                AND slot = ?
                                """
                        )

        ) {

            statement.setInt(
                    1,
                    machineId
            );

            statement.setInt(
                    2,
                    page
            );

            statement.setInt(
                    3,
                    slot
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

    }

    public static String serializeItemStack(
            ItemStack itemStack
    ) {

        try {

            return Base64.getEncoder()
                    .encodeToString(
                            itemStack.serializeAsBytes()
                    );

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

        return null;

    }

    public static void saveFilterItem(
            Block block,
            int page,
            int slot,
            ItemStack itemStack
    ) {

        int machineId =
                getMachineId(
                        block
                );

        if (machineId == -1) {

            return;

        }

        removeFilterItem(
                block,
                page,
                slot
        );

        String serializedItem =
                serializeItemStack(
                        itemStack
                );

        if (serializedItem == null) {

            return;

        }

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                INSERT INTO machine_filters
                                (
                                    machine_id,
                                    page,
                                    slot,
                                    item_data
                                )
                                VALUES
                                (
                                    ?, ?, ?, ?
                                )
                                """
                        )

        ) {

            statement.setInt(
                    1,
                    machineId
            );

            statement.setInt(
                    2,
                    page
            );

            statement.setInt(
                    3,
                    slot
            );

            statement.setString(
                    4,
                    serializedItem
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

    }

    public static ItemStack deserializeItemStack(
            String serializedItem
    ) {

        try {

            return ItemStack.deserializeBytes(
                    Base64.getDecoder()
                            .decode(
                                    serializedItem
                            )
            );

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

        return null;

    }

    public static Map<Integer, ItemStack> getFilterItems(
            Block block,
            int page
    ) {

        Map<Integer, ItemStack> items =
                new HashMap<>();

        int machineId =
                getMachineId(
                        block
                );

        if (machineId == -1) {

            return items;

        }

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT slot, item_data
                                FROM machine_filters
                                WHERE machine_id = ?
                                AND page = ?
                                """
                        )

        ) {

            statement.setInt(
                    1,
                    machineId
            );

            statement.setInt(
                    2,
                    page
            );

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                items.put(

                        result.getInt(
                                "slot"
                        ),

                        deserializeItemStack(
                                result.getString(
                                        "item_data"
                                )
                        )

                );

            }

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

        return items;

    }

    public static Map<Integer, ItemStack> getAllFilterItems(
            Block block
    ) {

        Map<Integer, ItemStack> items =
                new HashMap<>();

        int machineId =
                getMachineId(
                        block
                );

        if (machineId == -1) {

            return items;

        }

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT page,
                                       slot,
                                       item_data
                                FROM machine_filters
                                WHERE machine_id = ?
                                """
                        )

        ) {

            statement.setInt(
                    1,
                    machineId
            );

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                int page =
                        result.getInt(
                                "page"
                        );

                int slot =
                        result.getInt(
                                "slot"
                        );

                items.put(

                        (page * 1000) + slot,

                        deserializeItemStack(
                                result.getString(
                                        "item_data"
                                )
                        )

                );

            }

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

        return items;

    }

    public static int getHighestFilterPage(
            Block block
    ) {

        int machineId =
                getMachineId(
                        block
                );

        if (machineId == -1) {

            return 1;

        }

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT MAX(page)
                                FROM machine_filters
                                WHERE machine_id = ?
                                """
                        )

        ) {

            statement.setInt(
                    1,
                    machineId
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                int page =
                        result.getInt(
                                1
                        );

                return Math.max(
                        page,
                        1
                );

            }

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

        return 1;

    }

    public static void setMachineState(
            Block block,
            MachineState state
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                UPDATE machines
                                SET state = ?
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                """
                        )

        ) {

            statement.setString(
                    1,
                    state.name()
            );

            statement.setString(
                    2,
                    block.getWorld().getName()
            );

            statement.setInt(
                    3,
                    block.getX()
            );

            statement.setInt(
                    4,
                    block.getY()
            );

            statement.setInt(
                    5,
                    block.getZ()
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

    }

    public static MachineState getMachineState(
            Block block
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT state
                                FROM machines
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                LIMIT 1
                                """
                        )

        ) {

            statement.setString(
                    1,
                    block.getWorld().getName()
            );

            statement.setInt(
                    2,
                    block.getX()
            );

            statement.setInt(
                    3,
                    block.getY()
            );

            statement.setInt(
                    4,
                    block.getZ()
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                return MachineState.valueOf(
                        result.getString(
                                "state"
                        )
                );

            }

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

        return MachineState.ACTIVE;

    }

    public static void setMachineError(
            Block block,
            MachineError error
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                UPDATE machines
                                SET error_reason = ?
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                """
                        )

        ) {

            statement.setString(
                    1,
                    error.name()
            );

            statement.setString(
                    2,
                    block.getWorld().getName()
            );

            statement.setInt(
                    3,
                    block.getX()
            );

            statement.setInt(
                    4,
                    block.getY()
            );

            statement.setInt(
                    5,
                    block.getZ()
            );

            statement.executeUpdate();

            if (error != MachineError.NONE) {

                setMachineState(
                        block,
                        MachineState.ERROR
                );

            }

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

    }

    public static MachineError getMachineError(
            Block block
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT error_reason
                                FROM machines
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                LIMIT 1
                                """
                        )

        ) {

            statement.setString(
                    1,
                    block.getWorld().getName()
            );

            statement.setInt(
                    2,
                    block.getX()
            );

            statement.setInt(
                    3,
                    block.getY()
            );

            statement.setInt(
                    4,
                    block.getZ()
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                String error =
                        result.getString(
                                "error_reason"
                        );

                if (error == null
                        || error.isEmpty()) {

                    return MachineError.NONE;

                }

                return MachineError.valueOf(
                        error
                );

            }

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

        return MachineError.NONE;

    }

    public static java.util.List<Block> getAllMachines() {

        java.util.List<Block> machines =
                new java.util.ArrayList<>();

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT
                                    world,
                                    x,
                                    y,
                                    z
                                FROM machines
                                """
                        )

        ) {

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                World world =
                        Bukkit.getWorld(
                                result.getString(
                                        "world"
                                )
                        );

                if (world == null) {

                    continue;

                }

                machines.add(
                        world.getBlockAt(
                                result.getInt(
                                        "x"
                                ),
                                result.getInt(
                                        "y"
                                ),
                                result.getInt(
                                        "z"
                                )
                        )
                );

            }

        }

        catch (Exception e) {

            logException(
                    e
            );

        }

        return machines;

    }

    private static void logException(
            Exception e
    ) {

        RSAuger.getInstance()
                .getLogger()
                .severe(
                        e.getMessage()
                );

        e.printStackTrace();

    }

    public static void clearMachineError(
            Block block
    ) {

        setMachineError(
                block,
                MachineError.NONE
        );

        if (getMachineState(
                block
        ) == MachineState.ERROR) {

            setMachineState(
                    block,
                    MachineState.ACTIVE
            );

        }

    }


}