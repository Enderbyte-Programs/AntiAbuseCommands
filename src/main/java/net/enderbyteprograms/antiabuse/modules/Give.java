package net.enderbyteprograms.antiabuse.modules;

import net.enderbyteprograms.antiabuse.Static;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class Give implements Module{
    @Override
    public String[] GetAliases() {
        return new String[]{"give"};
    }

    @Override
    public boolean IsValid(String text, Player executor) {
        String towho = text.split(" ")[1];
        //Check if exempt
        if (executor.hasPermission("aac.give.exempt")) {
            return true;
        }

        //@s or own name?
        if (Static.Configuration.getBoolean("give.to-self-only")) {
            if (!Objects.equals(towho, "@s") && !Objects.equals(towho, executor.getName()))     {
                executor.sendMessage(ChatColor.RED + "You may only give to yourself" + ChatColor.RESET);
                return false;
            }
        }

        //Check for @s
        if (towho.contains("@")) {
            if (towho.contains("@e") || towho.contains("@a") || towho.contains("@r")) {
                executor.sendMessage(ChatColor.RED + "You may not execute mass commands" + ChatColor.RESET);
                return false;
            } //@p and @s are OK
            else {
                return true;
            }
        }

        //Check for UUIDs - not allowed
        try {
            UUID u = UUID.fromString(towho);
            executor.sendMessage(ChatColor.RED + "Please do not use UUIDs." + ChatColor.RESET);
            return false;
        } catch  (Exception e){
            //We want this!
        }

        //Find world
        String exworld = executor.getWorld().getName();
        String tgworld = Bukkit.getPlayer(towho).getWorld().getName();


        //Find same world
        if (Static.Configuration.getBoolean("give.same-world.only") && !exworld.equals(tgworld)) {
            executor.sendMessage(ChatColor.RED + "You may not give across worlds." + ChatColor.RESET);
            return false;
        }

        //What if target is not in an approved world?
        if (!Static.Configuration.getList("give.worlds").contains(tgworld)) {
            executor.sendMessage(ChatColor.RED + "You may not give to that world." + ChatColor.RESET);
            return false;
        }

        //Should be OK
        return true;
    }
}
