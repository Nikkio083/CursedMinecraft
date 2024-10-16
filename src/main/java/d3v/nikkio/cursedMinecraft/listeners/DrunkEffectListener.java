package d3v.nikkio.cursedMinecraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DrunkEffectListener implements Listener {

    private final JavaPlugin plugin;
    private final int drunkEffectDuration;
    private final Set<UUID> drunkPlayers = new HashSet<>();

    public DrunkEffectListener(JavaPlugin plugin, int drunkEffectDuration) {
        this.plugin = plugin;
        this.drunkEffectDuration = drunkEffectDuration;
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        applyDrunkEffect(player);
    }

    private void applyDrunkEffect(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, drunkEffectDuration, 1)); // Nausea effect
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, drunkEffectDuration, 1)); // Slowness effect
        drunkPlayers.add(player.getUniqueId());

        new BukkitRunnable() {
            @Override
            public void run() {
                drunkPlayers.remove(player.getUniqueId());
            }
        }.runTaskLater(plugin, drunkEffectDuration);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (drunkPlayers.contains(player.getUniqueId())) {
            event.setTo(event.getFrom().setDirection(event.getFrom().getDirection().multiply(-1)));
        }
    }
}