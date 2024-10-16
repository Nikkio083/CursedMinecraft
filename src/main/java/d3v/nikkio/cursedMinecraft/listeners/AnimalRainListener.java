package d3v.nikkio.cursedMinecraft.listeners;

import d3v.nikkio.cursedMinecraft.CursedMinecraft;
import org.bukkit.*;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AnimalRainListener implements Listener {

    private final boolean enableFallDamage;
    private final JavaPlugin plugin;

    public AnimalRainListener(CursedMinecraft plugin, boolean enableFallDamage) {
        this.plugin = plugin;
        this.enableFallDamage = enableFallDamage;
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState() && event.getWorld().isThundering()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (event.getWorld().isThundering()) {
                        Location randomLocation = getRandomLocation(event.getWorld());
                        spawnTheDoggos(randomLocation);
                    } else {
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L * 10);
        }
    }

    private Location getRandomLocation(World world) {
        double x = (Math.random() * 1000) - 500;
        double z = (Math.random() * 1000) - 500;
        double y = world.getHighestBlockYAt((int) x, (int) z);
        return new Location(world, x, y + 20, z);
    }

    private void spawnTheDoggos(Location location) {
        if (Math.random() > 0.5) {
            Cat cat = (Cat) location.getWorld().spawnEntity(location, EntityType.CAT);
            if (!enableFallDamage) {
                cat.setInvulnerable(true);
            }
        } else {
            Wolf dog = (Wolf) location.getWorld().spawnEntity(location, EntityType.WOLF);
            if (!enableFallDamage) {
                dog.setInvulnerable(true);
            }
        }
    }
}