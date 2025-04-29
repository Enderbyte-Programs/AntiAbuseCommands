package net.enderbyteprograms.antiabuse.commands;

import net.enderbyteprograms.antiabuse.Static;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AACCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage("AntiAbuseCommands Mark 7 Patch 1 - (c) 2025 Enderbyte Programs");
        } else {
            if (strings[0].equals("reload")) {
                if (!commandSender.hasPermission("aac.admin")) {
                    commandSender.sendMessage(ChatColor.RED + "Insufficient Permission" + ChatColor.RESET);
                    return false;
                }
                commandSender.sendMessage("Reloading config...");
                Static.PluginRoot.reloadConfig();
                Static.Configuration = Static.PluginRoot.getConfig();
                commandSender.sendMessage("Reloaded successfully");
            }
        }

        return true;
    }
}
