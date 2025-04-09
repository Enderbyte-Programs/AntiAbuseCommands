package net.enderbyteprograms.antiabuse;

import net.enderbyteprograms.antiabuse.commands.AACCommand;
import net.enderbyteprograms.antiabuse.commands.AACTabCompleter;
import net.enderbyteprograms.antiabuse.listeners.ChatListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class AntiAbuseMain extends JavaPlugin {
    @Override
    public void onEnable() {
        Static.PluginRoot = this;
        this.getServer().getPluginManager().registerEvents(new ChatListener(),this);
        this.getCommand("aac").setExecutor(new AACCommand());
        this.getCommand("aac").setTabCompleter(new AACTabCompleter());
        this.saveDefaultConfig();

        if (!this.getConfig().contains("whitelist-commands",true)) {
            getLogger().info("Updating config to mark 3");
            this.getConfig().set("whitelist-commands",Arrays.asList(new String[] {"placeholder"}));
            saveConfig();
        }

        if (!this.getConfig().contains("give.forbidden",true)) {
            //Update to mark 4
            getLogger().info("Updating config to mark 4");
            this.getConfig().set("give.forbidden", Arrays.asList(new String[] {"placeholder"}));
            this.getConfig().set("summon.enabled",true);
            this.getConfig().set("summon.forbidden", Arrays.asList(new String[] {"placeholder"}));

            saveConfig();
        }

        if (!this.getConfig().contains("summon.strict-find",true)) {
            //Update to mark 5
            getLogger().info("Updating config to mark 5");
            this.getConfig().set("summon.strict-find",true);

            saveConfig();
        }

        this.reloadConfig();
        Static.Configuration = getConfig();

        getLogger().info("Anti-abuse commands is ready");
    }
    @Override
    public void onDisable() {
        getLogger().info("Goodbye!");
    }
}
