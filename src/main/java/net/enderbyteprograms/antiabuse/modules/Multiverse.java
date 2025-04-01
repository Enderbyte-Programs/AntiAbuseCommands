package net.enderbyteprograms.antiabuse.modules;

import net.enderbyteprograms.antiabuse.Static;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Multiverse implements Module{
    @Override
    public String[] GetAliases() {
        return new String[] {"mvtp","multiverse:mvtp","multiversetp","mvteleport","multiverseteleport","multiverse:multiversetp","multiverse:mvteleport","multiverse:multiverseteleport"};
    }

    @Override
    public boolean IsValid(String text, Player executor) {

        String target = text.split(" ")[1];//Players should never be sending player info anyway

        //Exempt?
        if (executor.hasPermission("aac.multiverse.exempt")) {
            return true;
        }

        //Not in approved list?
        if (!Static.Configuration.getStringList("multiverse.block-worlds").contains(target)) {
            executor.sendMessage(ChatColor.RED + "You are not allowed to teleport there." + ChatColor.RED);
            return false;
        }

        return true;
    }
}
