package d3v.nikkio.cursedMinecraft;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public final class CursedMinecraft extends JavaPlugin implements Listener {

    public boolean enableFallDamage = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        //Lista idee:
        /*
        * - an RTP that does not care where it puts you -- DONE
        * - Inventory roulette: Every five/ten minutes your inventory gets shuffled
        * - Drunk Controls: Every time you drink or eat something you get drunk for 15 seconds so the contols are inverted
        * - There is a chance that when you go to sleep the bed is going to explode
        * - Teleporting chests, when you try to open one it teleports to a random location in a 50*50*50 block area
        * - IT'S RAINING CATS AND DOGSSSS!!!!!!!!! -- DONE
        * */
        getLogger().info("CursedMinecraft is enabled, good luck!");
        Objects.requireNonNull(this.getCommand("rtp")).setExecutor(new RandomTeleportCommand());
        new BukkitRunnable() {
            @Override
            public void run() {
                for(World world : Bukkit.getWorlds()) {
                    if(world.isThundering()) {
                        Bukkit.getPluginManager();
                        Location location = getRandomLocation(world);
                        Location randomLocation = getRandomLocation(world);
                        SpawnTHEDOGGOS(randomLocation);
                    }
                }

            }
        }.runTaskTimer(this, 0L, 20L * 10);
    }
    private Location getRandomLocation(World world) {
        double x = (Math.random()*1000)-500;
        double z = (Math.random()*1000)-500;
        double y = world.getHighestBlockYAt((int)x, (int)z);
        return new Location(world, x, y+20, z);
    }
    private void SpawnTHEDOGGOS(Location location) {
        if(Math.random() > 0.5){
            Cat cat = (Cat) location.getWorld().spawnEntity(location, EntityType.CAT);
            if(!enableFallDamage) {
                cat.setInvulnerable(true);
            }
        }else{
            Wolf dog = (Wolf) location.getWorld().spawnEntity(location, EntityType.WOLF);
            if(!enableFallDamage) {
                dog.setInvulnerable(true);
            }
        }
    }
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(!enableFallDamage && (event.getEntity() instanceof Cat || event.getEntity() instanceof Wolf)) {
            if(event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
        }
    }


    public static class RandomTeleportCommand implements CommandExecutor {
        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            if(command.getName().equalsIgnoreCase("rtp")) {
                Player target;
                if(!(sender instanceof Player)) {
                    if(args.length == 0) {
                        sender.sendMessage(ChatColor.RED + "Usage: /rtp <player>");
                        return true;
                    }else {
                        target = Bukkit.getPlayer(args[0]);
                    }
                }
                else {
                    if(args.length == 0){
                        target = (Player) sender;
                    }else{
                        target = Bukkit.getPlayer(args[0]);
                    }
                }
                assert target != null;
                int initialX = target.getLocation().getBlockX();
                int initialY = target.getLocation().getBlockY();
                int initialZ = target.getLocation().getBlockZ();

                int random = (int)(Math.random() * 1000 + 1);
                int finalX;
                int finalY;
                int finalZ;

                int randomRandom = (int)(Math.random() * 3 + 1);
                switch (randomRandom) {
                    case 0:
                        finalX = initialX + random;
                        finalZ = initialZ + random;
                        break;
                    case 1:
                        finalX = initialX - random;
                        finalZ = initialZ - random;
                        break;
                    case 2:
                        finalX = initialX + random;
                        finalZ = initialZ - random;
                        break;
                    default:
                        finalX = initialX - random;
                        finalZ = initialZ + random;
                        break;
                }
                finalY = initialY;
                sender.sendMessage(ChatColor.GREEN + "Searching for a 'safe' location...");
                Block block, block2, block3, block4;
                block = target.getWorld().getBlockAt(finalX, finalY, finalZ);
                block2 = target.getWorld().getBlockAt(finalX, finalY + 1, finalZ);
                block3 = target.getWorld().getBlockAt(finalX, finalY + 2, finalZ);
                block4 = target.getWorld().getBlockAt(finalX, finalY + 3, finalZ);
                block.setType(Material.COARSE_DIRT);
                block2.setType(Material.AIR);
                block3.setType(Material.AIR);
                block4.setType(Material.AIR);

                sender.sendMessage(ChatColor.AQUA + "A location was found, good luck!");
                target.teleport(new Location(target.getWorld(), finalX, finalY, finalZ));
                return true;
            }
            return false;
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("CursedMinecraft is disabled!");
    }
}
