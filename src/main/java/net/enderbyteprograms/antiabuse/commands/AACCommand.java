package net.enderbyteprograms.antiabuse.commands;

import net.enderbyteprograms.antiabuse.Static;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AACCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage("AntiAbuseCommands Mark 3 Patch 6 - (c) 2025 Enderbyte Programs");
        } else {
            if (strings[0].equals("reload")) {
                commandSender.sendMessage("Reloading config...");
                Static.PluginRoot.reloadConfig();
                Static.Configuration = Static.PluginRoot.getConfig();
                commandSender.sendMessage("Reloaded successfully");
            }
        }

        return true;
    }
}
