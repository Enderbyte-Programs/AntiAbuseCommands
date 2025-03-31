package net.enderbyteprograms.antiabuse.modules;

import org.bukkit.entity.Player;

public class Teleport implements Module{
    @Override
    public String[] GetAliases() {
        return new String[0];
    }

    @Override
    public boolean IsValid(String text, Player executor) {
        return false;
    }
}
