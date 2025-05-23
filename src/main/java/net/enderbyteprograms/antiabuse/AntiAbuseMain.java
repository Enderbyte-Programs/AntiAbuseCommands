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

        Patch();//Make sure config is updated to latest version.

        this.reloadConfig();
        Static.Configuration = getConfig();

        getLogger().info("Anti-abuse commands is ready");
    }
    @Override
    public void onDisable() {
        getLogger().info("Goodbye!");
    }

    public void Patch() {
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

        if (!this.getConfig().contains("settitle",true)) {
            //Update to mark 6
            getLogger().info("Updating config to mark 6");
            this.getConfig().set("settitle.enabled",true);
            this.getConfig().set("settitle.allowed",Arrays.asList(new String[] {"written_book","writable_book"}));
            this.getConfig().set("setlore.enabled",true);
            this.getConfig().set("setlore.allowed",Arrays.asList(new String[] {"written_book","writable_book"}));

            saveConfig();
        }

        if (!this.getConfig().contains("give.strict",true)) {
            //Update to mark 7
            getLogger().info("Updating config to mark 7");
            this.getConfig().set("give.strict",false);

            saveConfig();
        }

        if (!this.getConfig().contains("parkour",true)) {
            //Update to mark 8
            getLogger().info("Updating config to mark 8");
            this.getConfig().set("parkour.enabled",true);
            this.getConfig().set("parkour.blocked-regexes",new String[]{"setcourse \\S+ command"});

            saveConfig();
        }
    }
}
