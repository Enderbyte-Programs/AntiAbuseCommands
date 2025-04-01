package net.enderbyteprograms.antiabuse.modules;

import net.enderbyteprograms.antiabuse.Static;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Teleport implements Module{
    @Override
    public String[] GetAliases() {
        return new String[] {"tp","teleport","minecraft:tp","minecraft:telepor"};
    }

    private boolean isint(String s) {
        return false;
    }

    private boolean isuuid(String s) {
        return false;
    }

    @Override
    public boolean IsValid(String text, Player executor) {
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
        }
        if (len_of_args == 3) {
            // /tp <player> <player>
            type = TeleportTypes.PlayerToPlayer;
            if (split[1].equals(executor.getDisplayName()) || split[2].equals(executor.getDisplayName())) {
                type = TeleportTypes.SelfToPlayer;
                //Is doing self, alternate syntax
            }
        }
        if (len_of_args == 4) {
            // /tp <x> <y> <z>
            type = TeleportTypes.SelfToCoordinates;

        }
        if (len_of_args == 5) {
            // /tp <player> <x> <y> <z>
            type = TeleportTypes.PlayerToCoordinates;
            if (split[1].equals(executor.getDisplayName())) {
                type = TeleportTypes.SelfToCoordinates;
                //Is doing self, alternate syntax
            }
        }

        //Exemption?
        if (executor.hasPermission("aac.teleport.exempt")) {
            return true;
        }

        //Check for involvesselfg
        if (Static.Configuration.getBoolean("teleport.involve-self-only")) {
            if (type == TeleportTypes.PlayerToPlayer || type == TeleportTypes.PlayerToCoordinates) {
                executor.sendMessage(ChatColor.RED + "You may not teleport other people");
                return false;
            }
        }

        return true;
    }
}
