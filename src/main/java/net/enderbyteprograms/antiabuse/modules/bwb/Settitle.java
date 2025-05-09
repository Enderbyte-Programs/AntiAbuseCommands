package net.enderbyteprograms.antiabuse.modules.bwb;

import net.enderbyteprograms.antiabuse.Static;
import net.enderbyteprograms.antiabuse.modules.Module;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Settitle implements Module {
    @Override
    public String[] GetAliases() {
        return new String[] {"settitle","bookswithoutborders:settitle"};
    }

    @Override
    public boolean IsValid(String text, Player executor) {
        //Exempt?

        if (executor.hasPermission("aac.settitle.exempt")) {
            return true;
        }

        //Get inventory, and see if hand is an OK item
        PlayerInventory inv = executor.getInventory();
        ItemStack current = inv.getItemInMainHand();
        String cname = current.getType().name();
        for (String allowedname : Static.Configuration.getStringList("settitle.allowed")) {
            //Static.PluginRoot.getLogger().info(String.format("Rejected titleset - item was %s and allowed was %s",cname,allowedname.toLowerCase()));
            if (allowedname.toLowerCase().equals(cname.toLowerCase())) {
                return true;
            }
        }


        executor.sendMessage(ChatColor.RED + "You are not allowed to use settitle on this item" + ChatColor.RESET);

        return false;

    }
}
