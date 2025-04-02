package net.enderbyteprograms.antiabuse.modules;

import net.enderbyteprograms.antiabuse.Static;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Teleport implements Module{
    @Override
    public String[] GetAliases() {
        return new String[] {"tp","teleport","minecraft:tp","minecraft:teleport"};
    }

    private boolean isMinecraftNumber(String s) {
        try {
            Double.parseDouble(s.replace("~",""));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isuuid(String s) {
        try {
            UUID.fromString(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean IsValid(String text, Player executor) {

        //Some quick replaces
        text = text.replace("@s", executor.getDisplayName()).replace("@p",executor.getDisplayName());

        int len_of_args = text.split(" ").length;
        String[] split = text.split(" ");
        //We can find what type it is by arg length!

        String sourceplayer = executor.getDisplayName();
        String destcoords = null;
        String destplayer = null;
        TeleportTypes type = null;
        //Assumptions while we work out the data.

        if (len_of_args == 2) {
            // /tp <player>
            type = TeleportTypes.SelfToPlayer;
            destplayer = split[1];
        }
        if (len_of_args == 3) {
            // /tp <player> <player>
            type = TeleportTypes.PlayerToPlayer;
            if (split[1].equals(executor.getDisplayName()) || split[2].equals(executor.getDisplayName())) {
                type = TeleportTypes.SelfToPlayer;
                if (split[2].equals(executor.getDisplayName())) {
                    destplayer = split[1];
                }
                //Is doing self, alternate syntax
            }
        }
        if (len_of_args == 4) {
            // /tp <x> <y> <z>
            if (!isMinecraftNumber(split[1]) && !isMinecraftNumber(split[2]) && !isMinecraftNumber(split[3])) {
                executor.sendMessage(ChatColor.RED + "Malformed syntax!");
                return false;
            }
            type = TeleportTypes.SelfToCoordinates;

        }
        if (len_of_args == 5) {
            // /tp <player> <x> <y> <z>
            type = TeleportTypes.PlayerToCoordinates;
            if (split[1].equals(executor.getDisplayName())) {
                type = TeleportTypes.SelfToCoordinates;
                //Is doing self, alternate syntax
            }

            if (!isMinecraftNumber(split[2]) && !isMinecraftNumber(split[3]) && !isMinecraftNumber(split[4])) {
                executor.sendMessage(ChatColor.RED + "Malformed syntax!");
                return false;
            }
        }

        //Exemption?
        if (executor.hasPermission("aac.teleport.exempt")) {
            return true;
        }

        //Check for UUIDs
        if (isuuid(destplayer) || isuuid(sourceplayer)) {
            executor.sendMessage(ChatColor.RED + "You may not use UUIDs");
            return false;
        }

        //Check for involvesselfg
        if (Static.Configuration.getBoolean("teleport.involve-self-only")) {
            if (type == TeleportTypes.PlayerToPlayer || type == TeleportTypes.PlayerToCoordinates) {
                executor.sendMessage(ChatColor.RED + "You may not teleport other people");
                return false;
            }
        }

        //Anti mass command
        if (text.contains("@e") || text.contains("@a") || text.contains("@r")) {
            executor.sendMessage(ChatColor.RED + "You may not execute mass commands.");
            return false;
        }

        List<String> AllowedWorlds = Static.Configuration.getStringList("teleport.worlds");

        //Check for world validity
        if (!AllowedWorlds.contains(executor.getWorld().getName())) {
            executor.sendMessage(ChatColor.RED + "You may not teleport here.");
            return false;
        }


        if (destplayer != null) {
            //Check legality of destinattion player
            if (!AllowedWorlds.contains(Bukkit.getPlayer(destplayer).getWorld().getName())) {
                executor.sendMessage(ChatColor.RED + "You may not teleport here.");
                return false;
            }

            //Check same world only
            if (Static.Configuration.getBoolean("teleport.same-world-only")) {
                if (!Bukkit.getPlayer(sourceplayer).getWorld().getName().equals(Bukkit.getPlayer(destplayer).getWorld().getName())) {
                    executor.sendMessage(ChatColor.RED + "You may not teleport here.");
                    return false;
                }
            }
        }

        return true;
    }
}
