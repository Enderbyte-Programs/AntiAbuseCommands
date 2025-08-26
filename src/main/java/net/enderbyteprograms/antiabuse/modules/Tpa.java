package net.enderbyteprograms.antiabuse.modules;

import net.enderbyteprograms.antiabuse.Static;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class Tpa implements Module{

    @Override
    public String[] GetAliases() {
        return new String[] {"tpa","tpahere","simpletpa:tpa","simpletpa:tpahere"};
    }

    @Override
    public boolean IsValid(String text, Player executor) {
        String[] splitargs = text.split(" ");
        //Will be /tpa ... or /tpahere ...
        if (splitargs.length == 0) {
            return true;//Must be error or /tpahereall, so passthrough OK
        }
        if (executor.hasPermission("aac.tpa.exempt")) {
            return true;//Is staff
        }

        String sourceworld = executor.getWorld().getName();
        Player destplayer = Bukkit.getPlayer(splitargs[1]);
        String destworld = destplayer.getWorld().getName();
        List<String> allowedworlds = Static.Configuration.getStringList("tpa.allowed-worlds");
        if (!(allowedworlds.contains(sourceworld) && allowedworlds.contains(destworld))) {
            executor.sendMessage(ChatColor.RED + "You may not teleport other players to or from this world." + ChatColor.RESET);
            return false;
        }

        //Should be OK
        return true;
    }
}
