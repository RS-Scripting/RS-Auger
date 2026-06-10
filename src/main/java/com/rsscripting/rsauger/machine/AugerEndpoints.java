package com.rsscripting.rsauger.machine;

import org.bukkit.block.Block;

public class AugerEndpoints {

    private final Block source;
    private final Block destination;

    public AugerEndpoints(
            Block source,
            Block destination
    ) {

        this.source = source;
        this.destination = destination;

    }

    public Block getSource() {

        return source;

    }

    public Block getDestination() {

        return destination;

    }

}