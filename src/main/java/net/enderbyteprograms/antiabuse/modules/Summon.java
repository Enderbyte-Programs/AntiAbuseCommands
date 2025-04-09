package net.enderbyteprograms.antiabuse.modules;

import net.enderbyteprograms.antiabuse.Static;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Summon implements Module{
    @Override
    public String[] GetAliases() {
        return new String[] {"summon","minecraft:summon"};
    }

    @Override
    public boolean IsValid(String text, Player executor) {
        String fullitem = text.split(" ")[1];

        if (executor.hasPermission("aac.summon.exempt")) {
            return true;
            //is OK sergei - he has exemption
        }

        String itemhead = fullitem.split("\\[")[0].split("\\{")[0];
        if (itemhead.contains(":")) {
            itemhead = itemhead.split(":")[1];
        }

        for (String forbiddenitemname : Static.Configuration.getStringList("summon.forbidden")) {
            if (itemhead.equals(forbiddenitemname)) {
                executor.sendMessage(ChatColor.RED + "That has been forbidden" + ChatColor.RESET);
                return false;
            }

            if (Static.Configuration.getBoolean("summon.strict-find")) {
                if (text.contains(forbiddenitemname)) {
                    executor.sendMessage(ChatColor.RED + "Nice try, but that has been forbidden" + ChatColor.RESET);
                    return false;
                }
            }
        }

        return true;
    }
}
