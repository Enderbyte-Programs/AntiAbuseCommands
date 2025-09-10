package net.enderbyteprograms.antiabuse.modules;

import net.enderbyteprograms.antiabuse.Static;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Enchant implements Module{
    @Override
    public String[] GetAliases() {
        return new String[]{"enchant","minecraft:enchant"};
    }

    @Override
    public boolean IsValid(String text, Player executor) {
        String[] splitargs = text.split(" ");
        if (executor.hasPermission("aac.enchant.exempt")) {
            return true;
        }
        String target = splitargs[1];// Will be an @ figure
        if (target.contains("@e") || target.contains("@a") || target.contains("@r")) {
            executor.sendMessage(ChatColor.RED + "You may not execute mass commands." + ChatColor.RESET);
            return false;
        }

        if (target.equals("@s")) {
            return true;
        }

        //Check for UUIDs
        try {
            UUID.fromString(target);
            executor.sendMessage(ChatColor.RED + "Please target a player only.");
            return false;
        } catch (Exception e) {
            //Is OK, it shouldn't be a UUID
        }

        //Get world
        String targetsworld = Bukkit.getPlayer(target).getWorld().getName();
        if (Static.Configuration.getStringList("enchant.allowed-worlds").contains(targetsworld)) {
            return true;
        } else {
            executor.sendMessage(ChatColor.RED + "The target is not in an allowed world." + ChatColor.RESET);
            return false;
        }
    }
}
