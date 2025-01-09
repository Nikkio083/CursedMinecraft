package d3v.nikkio.cursedMinecraft.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ExpodingChickenListener {
    private final JavaPlugin plugin;
    private final double ExplodeChance;

    public ExpodingChickenListener(JavaPlugin plugin, double explodeChance) {
        this.plugin = plugin;
        this.ExplodeChance = explodeChance;
    }
    @EventHandler
    public void onPlayerDamageChicken(EntityDamageByEntityEvent event) {
        if (Math.random() < ExplodeChance) {
            if(event.getDamager().equals(EntityType.PLAYER)){
                Entity entity = event.getEntity();
                if(entity.getType().equals(EntityType.CHICKEN)){
                    entity.getWorld().createExplosion(entity.getLocation(), 4.0F, false, false);
                }
            }
        }
    }
}
