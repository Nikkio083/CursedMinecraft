package d3v.nikkio.cursedMinecraft.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class WalkingBedsListener implements Listener {

    private final JavaPlugin plugin;
    private final double walkChance;

    public WalkingBedsListener(JavaPlugin plugin, double walkChance) {
        this.plugin = plugin;
        this.walkChance = walkChance;
    }
    @EventHandler
    public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();
        World world = player.getWorld();

        double radius = 20; //block radius x bed search

        for(int x = (int)(playerLocation.getX() -radius); x <= (int) (playerLocation.getX() + radius); x++) {
            for(int y = (int)(playerLocation.getY() -radius); y <= (int)(playerLocation.getY() +radius); y++) {
                for(int z = (int)(playerLocation.getZ() -radius); z <= (int)(playerLocation.getZ() +radius); z++) {
                    Block block = world.getBlockAt(x, y, z);
                    Material blockType = block.getType();
                    if(block.getType().toString().contains("BED")) {
                        Location blockLocation = block.getLocation();
                        if(!isInSight(player, blockLocation)) {
                            block.setType(Material.AIR); // Remove the bed when no one's looking
                            Location newLocation = getRandomLocationInRadius(blockLocation, (int) radius);
                            Block newBlock = newLocation.getBlock();
                            newBlock.setType(blockType);
                            // Make the new bed glowing

                            ArmorStand armorStand = world.spawn(newLocation.add(0, 0.5, 0), ArmorStand.class, as -> {
                                as.setVisible(false);
                                as.setGravity(false);
                                as.setCustomName("I'm moving!");
                                as.setCustomNameVisible(true);
                                as.setInvulnerable(true);
                                as.setMarker(true);
                                as.setGlowing(true);
                                as.setHelmet(new ItemStack(blockType));
                            });
                            armorStand.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20 * 60, 1, false, false, false));
                        }
                    }
                }
            }
        }
    }


    private boolean isInSight(Player player, Location target) {
        Vector toTarget = target.toVector().subtract(player.getEyeLocation().toVector()).normalize();
        Vector direction = player.getEyeLocation().getDirection().normalize();
        double dot = direction.dot(toTarget);
        return dot > 0.95; // circa 18° di campo visivo
    }
    private Location getRandomLocationInRadius(Location center, int radius) {
        double x = center.getX() + (Math.random() * 2 - 1) * radius;
        double y = center.getY() + (Math.random() * 2 - 1) * radius;
        double z = center.getZ() + (Math.random() * 2 - 1) * radius;
        Location location = new Location(center.getWorld(), x, y, z);
        location.setY(center.getWorld().getHighestBlockYAt(location) + 1);
        return location;
    }
}
