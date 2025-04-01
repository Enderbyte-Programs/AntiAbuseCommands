package net.enderbyteprograms.antiabuse.listeners;

import net.enderbyteprograms.antiabuse.Static;
import net.enderbyteprograms.antiabuse.modules.Gamemode;
import net.enderbyteprograms.antiabuse.modules.Give;
import net.enderbyteprograms.antiabuse.modules.Module;
import net.enderbyteprograms.antiabuse.modules.Transfer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(PlayerCommandPreprocessEvent event) {
        String commandtext = event.getMessage();
        String commandhead = commandtext.split(" ")[0].replace("/","");

        HashMap<String,Module> ConfigMappings = new HashMap<>();
        ConfigMappings.put("give", new Give());
        ConfigMappings.put("gamemode", new Gamemode());
        ConfigMappings.put("transfer",new Transfer());

        for (String cname : ConfigMappings.keySet()) {
            if (Static.Configuration.getBoolean(cname + ".enabled")) {
                //Has been enabled
                Module module = ConfigMappings.get(cname);
                if (Arrays.asList(module.GetAliases()).contains(commandhead)) {
                    //Detected that this module should run
                    boolean isvalid = module.IsValid(commandtext, event.getPlayer());
                    if (!isvalid) {
                        Static.PluginRoot.getLogger().warning("ATTN - " + event.getPlayer().getDisplayName() + " tried to abuse commands! (" + commandtext + ")");
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }

    }
}
