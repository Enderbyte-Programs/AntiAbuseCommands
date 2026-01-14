package net.enderbyteprograms.antiabuse.modules;

import net.enderbyteprograms.antiabuse.Static;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class WorldEdit implements Module{

    @Override
    public String[] GetAliases() {
        return new String[] {"/","worldedit:/"};
    }

    @Override
    public boolean IsValid(String text, Player executor) {
        if (executor.hasPermission("aac.worldedit.exempt")) {
            return true;
        }
        List<String> bannedWords = Static.Configuration.getStringList("worldedit.banned-words");
        boolean res = true;

        for (String bannedWord:bannedWords) {
            if (text.toLowerCase().contains(bannedWord.toLowerCase())) {
                executor.sendMessage(ChatColor.RED+"This operation has been disallowed."+ChatColor.RESET);
                res = false;
            }
        }

        return res;

    }
}
