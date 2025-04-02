package net.enderbyteprograms.antiabuse;

import net.enderbyteprograms.antiabuse.commands.AACCommand;
import net.enderbyteprograms.antiabuse.commands.AACTabCompleter;
import net.enderbyteprograms.antiabuse.listeners.ChatListener;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiAbuseMain extends JavaPlugin {
    @Override
    public void onEnable() {
        Static.PluginRoot = this;
        this.getServer().getPluginManager().registerEvents(new ChatListener(),this);
        this.getCommand("aac").setExecutor(new AACCommand());
        this.getCommand("aac").setTabCompleter(new AACTabCompleter());
        this.saveDefaultConfig();
        Static.Configuration = getConfig();
        getLogger().info("Anti-abuse commands is ready");
    }
    @Override
    public void onDisable() {
        getLogger().info("Goodbye!");
    }
}
