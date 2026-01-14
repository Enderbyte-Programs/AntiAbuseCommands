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
            commandSender.sendMessage("AntiAbuseCommands Mark 12 Patch 0 - (c) 2025-2026 Enderbyte Programs");
        } else {
            if (strings[0].equals("reload")) {
                if (!commandSender.hasPermission("aac.admin")) {
                    commandSender.sendMessage(ChatColor.RED + "Insufficient Permission" + ChatColor.RESET);
                    return false;
                }
                commandSender.sendMessage("Reloading config...");
                Static.PluginRoot.reloadConfig();
                Static.Configuration = Static.PluginRoot.getConfig();
                Static.printTraceback = Static.Configuration.getBoolean("settings.print-traceback");
                Static.noteIntercept = Static.Configuration.getBoolean("settings.note-intercept");
                Static.noteAlert = Static.Configuration.getBoolean("settings.note-alert");
                commandSender.sendMessage("Reloaded successfully");
            }
        }

        return true;
    }
}
