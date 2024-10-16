package d3v.nikkio.cursedMinecraft.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BedExplodeListener implements Listener {

    private final JavaPlugin plugin;
    private final double bedExplodeChance;

    public BedExplodeListener(JavaPlugin plugin, double bedExplodeChance) {
        this.plugin = plugin;
        this.bedExplodeChance = bedExplodeChance;
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            Player player = event.getPlayer();
            if (Math.random() < bedExplodeChance) { // Use the provided chance
                Location bedLocation = event.getBed().getLocation();
                bedLocation.getWorld().createExplosion(bedLocation, 4.0F, false, false);
            }
        }
    }
}