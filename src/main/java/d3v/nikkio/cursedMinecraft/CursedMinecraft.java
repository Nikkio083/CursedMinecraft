package d3v.nikkio.cursedMinecraft;

import d3v.nikkio.cursedMinecraft.commands.RandomTeleportCommand;
import d3v.nikkio.cursedMinecraft.listeners.AmnesiaEffectListener;
import d3v.nikkio.cursedMinecraft.listeners.AnimalRainListener;
import d3v.nikkio.cursedMinecraft.listeners.BedExplodeListener;
import d3v.nikkio.cursedMinecraft.listeners.DrunkEffectListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CursedMinecraft extends JavaPlugin {

    public boolean enableFallDamage = false;
    public int drunkEffectDuration = 300;
    public double bedExplodeChance = 0.1;
    public int amnesiaEffectDuration = 600; // 10 minutes
    public int shuffleInterval = 300; // 5 minutes

    @Override
    public void onEnable() {
        AmnesiaEffectListener amnesiaEffectListener = new AmnesiaEffectListener(this, amnesiaEffectDuration, shuffleInterval);
        Objects.requireNonNull(this.getCommand("amnesia")).setExecutor(amnesiaEffectListener);
        Objects.requireNonNull(this.getCommand("rtp")).setExecutor(new RandomTeleportCommand(this));
        getServer().getPluginManager().registerEvents(new AnimalRainListener(this, enableFallDamage), this);
        getServer().getPluginManager().registerEvents(new DrunkEffectListener(this, drunkEffectDuration), this);
        getServer().getPluginManager().registerEvents(new BedExplodeListener(this, bedExplodeChance), this);
        getServer().getPluginManager().registerEvents(amnesiaEffectListener, this);
    }
}