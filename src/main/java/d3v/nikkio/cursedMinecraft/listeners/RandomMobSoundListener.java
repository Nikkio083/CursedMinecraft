package d3v.nikkio.cursedMinecraft.listeners;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomMobSoundListener implements Listener {
    private final JavaPlugin plugin;
    private final double soundChance;

    public RandomMobSoundListener(JavaPlugin plugin, double soundChance) {
        this.plugin = plugin;
        this.soundChance = soundChance;
    }

    @EventHandler
    public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
        if(Math.random() < soundChance) {
            // Play a random mob sound at the player's location
            org.bukkit.entity.Player player = event.getPlayer();
            org.bukkit.Location location = player.getLocation();
            org.bukkit.Sound[] aggressiveMobs = {
                Sound.ENTITY_ZOMBIE_AMBIENT,
                Sound.ENTITY_SKELETON_AMBIENT,
                Sound.ENTITY_CREEPER_HURT,
                Sound.ENTITY_ENDERMAN_SCREAM,
                Sound.ENTITY_GHAST_SCREAM,
                Sound.ENTITY_WITCH_AMBIENT,
                Sound.ENTITY_CREEPER_PRIMED
            };
            int randomIndex = (int) (Math.random() * aggressiveMobs.length);
            org.bukkit.Sound randomSound = aggressiveMobs[randomIndex];
            player.playSound(location, randomSound, 1.0f, 1.0f);
        }
    }
}
