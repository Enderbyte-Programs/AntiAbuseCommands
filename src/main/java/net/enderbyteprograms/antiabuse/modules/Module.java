package net.enderbyteprograms.antiabuse.modules;

public interface Module {
    public String[] GetAliases();
    public boolean IsValid(String text,org.bukkit.entity.Player executor);
}
