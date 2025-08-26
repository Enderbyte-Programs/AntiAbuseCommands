package net.enderbyteprograms.antiabuse.listeners;

import net.enderbyteprograms.antiabuse.Static;
import net.enderbyteprograms.antiabuse.modules.*;
import net.enderbyteprograms.antiabuse.modules.Module;
import net.enderbyteprograms.antiabuse.modules.bwb.Setlore;
import net.enderbyteprograms.antiabuse.modules.bwb.Settitle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ChatListener implements Listener {

    private boolean recursivestartswith(List<String> valstocheck, String searcher) {
        for (String v : valstocheck) {
            if (searcher.startsWith(v)) {
                return true;
            }
        }
        return false;
    }

    private boolean recursivestartswith(String[] valstocheck, String searcher) {
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
        //Static.PluginRoot.getLogger().info(commandtext);
        String commandhead = commandtext.replace("/","");

        HashMap<String,Module> ConfigMappings = new HashMap<>();
        ConfigMappings.put("give", new Give());
        ConfigMappings.put("gamemode", new Gamemode());
        ConfigMappings.put("transfer",new Transfer());
        ConfigMappings.put("multiverse",new Multiverse());
        ConfigMappings.put("tpa",new Tpa());//MUST come before teleport due to command naming restrictions!
        ConfigMappings.put("teleport",new Teleport());
        ConfigMappings.put("summon", new Summon());
        ConfigMappings.put("settitle",new Settitle());
        ConfigMappings.put("setlore",new Setlore());
        ConfigMappings.put("parkour",new Parkour());

        for (String cname : ConfigMappings.keySet()) {
            if (Static.Configuration.getBoolean(cname + ".enabled")) {
                //Has been enabled
                Module module = ConfigMappings.get(cname);
                if (recursivestartswith(module.GetAliases(),commandhead)) {
                    //Detected that this module should run

                    if (recursivestartswith(Static.Configuration.getStringList("whitelist-commands"),commandhead)) {
                        continue;
                    }

                    try {
                        Static.PluginRoot.getLogger().info(String.format("Intercepted command %s by %s",commandhead.split(" ")[0],event.getPlayer().getDisplayName()));
                        boolean isvalid = module.IsValid(commandtext, event.getPlayer());
                        if (!isvalid) {
                            Static.PluginRoot.getLogger().warning("ATTN - " + event.getPlayer().getDisplayName() + " tried to abuse commands! (" + commandtext + ")");
                            event.setCancelled(true);
                            return;
                        }
                    } catch (Exception e) {
                        StringWriter sw = new StringWriter();
                        e.printStackTrace(new PrintWriter(sw));//Come on java, there is a better way
                        Static.PluginRoot.getLogger().warning("Processor crashed - "+sw.toString());
                    }


                }
            }
        }

    }
}
