package net.enderbyteprograms.antiabuse.modules;

import org.bukkit.entity.Player;

public class Multiverse implements Module{
    @Override
    public String[] GetAliases() {
        return new String[] {"mvtp","multiverse:mvtp","multiversetp","mvteleport","multiverseteleport","multiverse:multiversetp","multiverse:mvteleport","multiverse:multiverseteleport"};
    }

    @Override
    public boolean IsValid(String text, Player executor) {

        

        return true;
    }
}
