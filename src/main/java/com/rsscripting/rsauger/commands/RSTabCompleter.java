package com.rsscripting.rsauger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RSTabCompleter
        implements TabCompleter {

    @Override
    public List<String> onTabComplete(

            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args


    ) {

        List<String> completions =
                new ArrayList<>();

        /*
        |--------------------------------------------------------------------------
        | FIRST ARGUMENT
        |--------------------------------------------------------------------------
        */

        if (args.length == 1) {

            completions.add("reload");

            completions.add("version");

        }

        return completions;

    }

}