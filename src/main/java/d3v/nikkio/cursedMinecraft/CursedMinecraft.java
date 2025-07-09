package d3v.nikkio.cursedMinecraft;

import d3v.nikkio.cursedMinecraft.commands.RandomTeleportCommand;
import d3v.nikkio.cursedMinecraft.listeners.AmnesiaEffectListener;
import d3v.nikkio.cursedMinecraft.listeners.AnimalRainListener;
import d3v.nikkio.cursedMinecraft.listeners.BedExplodeListener;
import d3v.nikkio.cursedMinecraft.listeners.DrunkEffectListener;
import d3v.nikkio.cursedMinecraft.listeners.WalkingBedsListener;
import d3v.nikkio.cursedMinecraft.listeners.ExpodingChickenListener;
import d3v.nikkio.cursedMinecraft.listeners.RandomMobSoundListener;
import d3v.nikkio.cursedMinecraft.listeners.TeleportOnGrassListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CursedMinecraft extends JavaPlugin {

    public boolean enableFallDamage = false;
    public int drunkEffectDuration = 300;
    public double ExplodeChance = 0.1;
    public int amnesiaEffectDuration = 600; // 10 minutes
    public int shuffleInterval = 300; // 5 minutes
    public double mobSoundChance = 0.05;
    public double grassTPChance = 0.1; // 10% chance to teleport when stepping on grass

    @Override
    public void onEnable() {
        AmnesiaEffectListener amnesiaEffectListener = new AmnesiaEffectListener(this, amnesiaEffectDuration, shuffleInterval);
        Objects.requireNonNull(this.getCommand("amnesia")).setExecutor(amnesiaEffectListener);
        Objects.requireNonNull(this.getCommand("rtp")).setExecutor(new RandomTeleportCommand(this));
        getServer().getPluginManager().registerEvents(new AnimalRainListener(this, enableFallDamage), this);
        getServer().getPluginManager().registerEvents(new DrunkEffectListener(this, drunkEffectDuration), this);
        getServer().getPluginManager().registerEvents(new RandomMobSoundListener(this, mobSoundChance), this);
        getServer().getPluginManager().registerEvents(new TeleportOnGrassListener(this, grassTPChance), this);
        getServer().getPluginManager().registerEvents(new BedExplodeListener(this, ExplodeChance), this);
        getServer().getPluginManager().registerEvents(new WalkingBedsListener(this, ExplodeChance), this);
        getServer().getPluginManager().registerEvents(new ExpodingChickenListener(this, ExplodeChance), this);
        getServer().getPluginManager().registerEvents(amnesiaEffectListener, this);
    }
}
