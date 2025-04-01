package net.enderbyteprograms.antiabuse.listeners;

import net.enderbyteprograms.antiabuse.Static;
import net.enderbyteprograms.antiabuse.modules.*;
import net.enderbyteprograms.antiabuse.modules.Module;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ChatListener implements Listener {

    private boolean recursivestartswith(String[] valstocheck,String searcher) {
        for (String v : valstocheck) {
            if (searcher.startsWith(v)) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onChat(PlayerCommandPreprocessEvent event) {
        String commandtext = event.getMessage();
        String commandhead = commandtext.replace("/","");

        HashMap<String,Module> ConfigMappings = new HashMap<>();
        ConfigMappings.put("give", new Give());
        ConfigMappings.put("gamemode", new Gamemode());
        ConfigMappings.put("transfer",new Transfer());
        ConfigMappings.put("multiverse",new Multiverse());

        for (String cname : ConfigMappings.keySet()) {
            if (Static.Configuration.getBoolean(cname + ".enabled")) {
                //Has been enabled
                Module module = ConfigMappings.get(cname);
                if (recursivestartswith(module.GetAliases(),commandhead)) {
                    //Detected that this module should run
                    try {
                        boolean isvalid = module.IsValid(commandtext, event.getPlayer());
                        if (!isvalid) {
                            Static.PluginRoot.getLogger().warning("ATTN - " + event.getPlayer().getDisplayName() + " tried to abuse commands! (" + commandtext + ")");
                            event.setCancelled(true);
                            return;
                        }
                    } catch (Exception e) {
                        Static.PluginRoot.getLogger().warning("Processor crashed - "+e.getMessage());
                    }
                }
            }
        }

    }
}
