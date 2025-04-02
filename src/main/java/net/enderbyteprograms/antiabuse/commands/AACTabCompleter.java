package net.enderbyteprograms.antiabuse.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class AACTabCompleter implements TabCompleter {

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return List.of("reload");
        } else {
            return new ArrayList<String>();
        }
    }
}
