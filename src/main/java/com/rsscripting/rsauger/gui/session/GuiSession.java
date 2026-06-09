package com.rsscripting.rsauger.gui.session;

import org.bukkit.block.Block;

import java.util.UUID;

public class GuiSession {

    private final UUID playerUuid;

    private Block selectedBlock;

    public GuiSession(UUID playerUuid) {
        this.playerUuid = playerUuid;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public Block getSelectedBlock() {
        return selectedBlock;
    }

    public void setSelectedBlock(Block selectedBlock) {
        this.selectedBlock = selectedBlock;
    }

}