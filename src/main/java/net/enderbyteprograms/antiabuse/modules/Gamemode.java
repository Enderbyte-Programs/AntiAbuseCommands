package net.enderbyteprograms.antiabuse.modules;

import net.enderbyteprograms.antiabuse.Static;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

public class Gamemode implements Module{
    @Override
    public String[] GetAliases() {
        return new String[] {"gamemode","minecraft:gamemode"};
    }

    @Override
    public boolean IsValid(String text, Player executor) {
        String[] sp = text.split(" ");

        if (executor.hasPermission("aac.gamemode.exempt")) {
            return true;//Exempted
        }

        //Discover target if applicable. Automatically allow if no target, as it means it is to the self
        String target = "@s";
        if (sp.length > 2) {
            target = sp[2];
        } else {
            return true;
        }

        //Check for the forbidden @s
        if (target.contains("@e") || target.contains("@a") || target.contains("@r")) {
            executor.sendMessage(ChatColor.RED + "Mass commands are not permitted" + ChatColor.RESET);
            return false;
        }

        //Check for UUID
        try {
            UUID.fromString(target);
            executor.sendMessage(ChatColor.RED + "You may not use UUIDs" + ChatColor.RESET);
            return false;
        } catch (Exception e) {
            //OK
        }

        //Check for involve self only
        if (Static.Configuration.getBoolean("gamemode.involve-self-only") && !target.equals(executor.getDisplayName())) {
            executor.sendMessage(ChatColor.RED + "You may only set your own gamemode" + ChatColor.RESET);
            return false;
        }

        //Check for same world only
        if (Static.Configuration.getBoolean("gamemode.same-world-only")) {
            if (!executor.getWorld().getName().equals(Bukkit.getPlayer(target).getWorld().getName())) {
                executor.sendMessage(ChatColor.RED + "You may not change gamemodes across worlds." + ChatColor.RESET);
                return false;
            }
        }

        //Check if world is OK
        if (!Static.Configuration.getList("gamemode.worlds").contains(Bukkit.getPlayer(target).getWorld().getName())) {
            executor.sendMessage(ChatColor.RED + "You may not change a player's gamemode in that world." + ChatColor.RESET);
            return false;
        }

        //OK by now
        return true;
    }
}
