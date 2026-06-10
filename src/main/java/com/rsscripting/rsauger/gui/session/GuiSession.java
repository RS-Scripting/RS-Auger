package com.rsscripting.rsauger.gui.session;

import org.bukkit.block.Block;

public class GuiSession {

    private Block selectedBlock;

    public GuiSession() {

    }

    public Block getSelectedBlock() {
        return selectedBlock;
    }

    public void setSelectedBlock(Block selectedBlock) {
        this.selectedBlock = selectedBlock;
    }

}