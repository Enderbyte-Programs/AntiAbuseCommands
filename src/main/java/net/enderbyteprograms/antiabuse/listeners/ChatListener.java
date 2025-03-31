package net.enderbyteprograms.antiabuse.listeners;

import net.enderbyteprograms.antiabuse.Static;
import net.enderbyteprograms.antiabuse.modules.Gamemode;
import net.enderbyteprograms.antiabuse.modules.Give;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(PlayerCommandPreprocessEvent event) {
        String commandtext = event.getMessage();
        String commandhead = commandtext.split(" ")[0].replace("/","");

        if (Static.Configuration.getBoolean("give.enabled")) {
            Give givemodule = new Give();
            if (Arrays.asList(givemodule.GetAliases()).contains(commandhead)) {
                //Process
                if (!givemodule.IsValid(commandtext,event.getPlayer())) {
                    event.setCancelled(true);

                }
                return;
            }
        }

        if (Static.Configuration.getBoolean("gamemode.enabled")) {
            Gamemode module = new Gamemode();
            if (Arrays.asList(module.GetAliases()).contains(commandhead)) {
                if (!module.IsValid(commandtext,event.getPlayer())) {
                    event.setCancelled(true);
                }
                return;
            }
        }

    }
}
