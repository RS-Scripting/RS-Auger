package com.rsscripting.rsauger.gui.session;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiSessionManager {

    private static final Map<UUID, GuiSession> sessions =
            new HashMap<>();

    public static GuiSession getSession(
            Player player
    ) {

        return sessions.computeIfAbsent(
                player.getUniqueId(),
                uuid -> new GuiSession()
        );

    }

}