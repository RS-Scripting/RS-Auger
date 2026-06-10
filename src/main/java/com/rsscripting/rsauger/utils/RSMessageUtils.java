package com.rsscripting.rsauger.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class RSMessageUtils {

    /*
    |--------------------------------------------------------------------------
    | PREFIX
    |--------------------------------------------------------------------------
    */

    private static final Component PREFIX =

            Component.text("[")
                    .color(NamedTextColor.GRAY)

                    .append(
                            Component.text(
                                    "RS-Auger"
                            ).color(
                                    NamedTextColor.RED
                            )
                    )

                    .append(
                            Component.text("] ")
                                    .color(
                                            NamedTextColor.GRAY
                                    )
                    );

    /*
    |--------------------------------------------------------------------------
    | SUCCESS
    |--------------------------------------------------------------------------
    */

    public static void success(
            Player player,
            String message
    ) {

        player.sendMessage(

                PREFIX.append(

                        Component.text(
                                message
                        ).color(
                                NamedTextColor.GREEN
                        )

                )

        );

    }

    /*
    |--------------------------------------------------------------------------
    | ERROR
    |--------------------------------------------------------------------------
    */

    public static void error(
            Player player,
            String message
    ) {

        player.sendMessage(

                PREFIX.append(

                        Component.text(
                                message
                        ).color(
                                NamedTextColor.RED
                        )

                )

        );

    }

    /*
    |--------------------------------------------------------------------------
    | INFO
    |--------------------------------------------------------------------------
    */

    public static void info(
            Player player,
            String message
    ) {

        player.sendMessage(

                PREFIX.append(

                        Component.text(
                                message
                        ).color(
                                NamedTextColor.AQUA
                        )

                )

        );

    }

}