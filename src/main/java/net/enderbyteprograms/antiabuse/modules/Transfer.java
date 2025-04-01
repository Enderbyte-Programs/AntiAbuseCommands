package net.enderbyteprograms.antiabuse.modules;

import net.enderbyteprograms.antiabuse.Static;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Transfer implements Module{
    @Override
    public String[] GetAliases() {
        return new String[] {"transfer","minecraft:transfer"};
    }

    @Override
    public boolean IsValid(String text, Player executor) {
        if (executor.hasPermission("aac.transfer.exempt")) {
            return true;
        }

        //If block-other-players is false, then everything is OK
        if (!Static.Configuration.getBoolean("transfer.block-other-players")) {
            return true;
        }

        //Now check for players. If there are any players other than @s, @p, or the username, it will be blocked
        //But we need to include the port

        String[] split = text.split(" ");
        if (split.length == 2) {
            //Is /transfer <server>, which is OK
            return true;
        }

        if (split.length == 3) {
            //Try to parse a port number.
            //If we can, it is OK
            try {
                Integer.parseInt(split[2]);
                return true;
            } catch (Exception e) {
                //Trying to do another player
                String target = split[2];
                if (target.equals(executor.getDisplayName()) || target.equals("@s") || target.equals("@p")) {
                    return true;
                }
            }
        }

        //What if there is port and players?
        if (split.length == 4) {
            String target = split[3];
            if (target.equals(executor.getDisplayName()) || target.equals("@s") || target.equals("@p")) {
                return true;
            }
        }

        executor.sendMessage(ChatColor.RED + "You may not transfer other players." + ChatColor.RESET);
        return false;
    }
}
