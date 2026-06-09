package com.rsscripting.rsauger.managers;

import com.rsscripting.rsauger.database.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectOutputStream;

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

            e.printStackTrace();

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

            e.printStackTrace();

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

            e.printStackTrace();

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

            e.printStackTrace();

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

            e.printStackTrace();

            return false;

        }

    }

    public static void setRadius(
            Block block,
            int radius
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                UPDATE machines
                                SET radius = ?
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                """
                        )

        ) {

            statement.setInt(
                    1,
                    radius
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

            e.printStackTrace();

        }

    }

    public static int getRadius(
            Block block
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT radius
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
                        "radius"
                );

            }

        }

        catch (Exception e) {

            e.printStackTrace();

        }

        return 5;

    }

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

            e.printStackTrace();

        }

    }

    public static boolean isPaused(
            Block block
    ) {

        return getMachineState(
                block
        ) == MachineState.PAUSED;

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

            e.printStackTrace();

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

            e.printStackTrace();

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

            e.printStackTrace();

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

            e.printStackTrace();

        }

    }

    public static String serializeItemStack(
            ItemStack itemStack
    ) {

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            BukkitObjectOutputStream dataOutput =
                    new BukkitObjectOutputStream(
                            outputStream
                    );

            dataOutput.writeObject(
                    itemStack
            );

            dataOutput.close();

            return Base64.getEncoder()
                    .encodeToString(
                            outputStream.toByteArray()
                    );

        }

        catch (IOException e) {

            e.printStackTrace();

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

            e.printStackTrace();

        }

    }

    public static ItemStack deserializeItemStack(
            String serializedItem
    ) {

        try {

            byte[] data =
                    Base64.getDecoder()
                            .decode(
                                    serializedItem
                            );

            BukkitObjectInputStream inputStream =
                    new BukkitObjectInputStream(
                            new ByteArrayInputStream(
                                    data
                            )
                    );

            ItemStack itemStack =
                    (ItemStack) inputStream.readObject();

            inputStream.close();

            return itemStack;

        }

        catch (Exception e) {

            e.printStackTrace();

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

            e.printStackTrace();

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

            e.printStackTrace();

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

            e.printStackTrace();

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

            e.printStackTrace();

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

            e.printStackTrace();

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

            e.printStackTrace();

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

            e.printStackTrace();

        }

        return MachineError.NONE;

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