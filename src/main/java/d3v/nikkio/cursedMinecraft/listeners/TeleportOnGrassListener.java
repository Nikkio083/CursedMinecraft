package d3v.nikkio.cursedMinecraft.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TeleportOnGrassListener implements Listener {
    private final JavaPlugin plugin;
    private final double teleportChance;

    public TeleportOnGrassListener(JavaPlugin plugin, double teleportChance) {
        this.plugin = plugin;
        this.teleportChance = teleportChance;
    }

    @EventHandler
    public void onPlayerStepOnGrass(org.bukkit.event.player.PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Block block = location.getBlock();
        if((block.getType() == Material.SHORT_GRASS) || block.getType() == Material.TALL_GRASS){
            if(Math.random() < teleportChance){
                double x = location.getX() + (Math.random() * 100 - 50);
                double z = location.getZ() + (Math.random() * 100 - 50);
                double y = location.getWorld().getHighestBlockYAt((int)x, (int)z);
                Location newLocation = new Location(player.getWorld(), x, y, z);
                player.teleport(newLocation);
            }
        }
    }
}
