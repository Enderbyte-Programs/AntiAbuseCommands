package net.enderbyteprograms.antiabuse.modules;

import net.enderbyteprograms.antiabuse.Helper;
import net.enderbyteprograms.antiabuse.Static;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class Parkour implements Module{
    @Override
    public String[] GetAliases() {
        return new String[] {"pa","parkour","parkour:parkour","parkour:pa","pkr","parkour:pkr"};
    }

    @Override
    public boolean IsValid(String text, Player executor) {
        String commandtail = text.split(" ",2)[1];
        List<String> bannedregexes = Static.Configuration.getStringList("parkour.blocked-regexes");
        //Check for exempt

        if (executor.hasPermission("aac.parkour.exempt")) {
            return true;
        }

        if (Helper.DoesStringMatchAnyRegex(commandtail,bannedregexes)) {
            executor.sendMessage(ChatColor.RED + "This feature has been blocked by AntiAbuseCommands." + ChatColor.RESET);
            return false;
        }
        return true;
    }
}
