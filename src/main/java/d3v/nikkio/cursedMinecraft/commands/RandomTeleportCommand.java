package d3v.nikkio.cursedMinecraft.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class RandomTeleportCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public RandomTeleportCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player targetPlayer;
        if (args.length > 0) {
            targetPlayer = plugin.getServer().getPlayer(args[0]);
            if (targetPlayer == null) {
                sender.sendMessage("Player not found.");
                return false;
            }
        } else if (sender instanceof Player) {
            targetPlayer = (Player) sender;
        } else {
            sender.sendMessage("You must specify a player.");
            return false;
        }
        teleportPlayer(targetPlayer);
        return true;
    }

    public void teleportPlayer(Player player) {
        Random random = new Random();
        int x = random.nextInt(10000) - 5000;
        int z = random.nextInt(10000) - 5000;
        int y = random.nextInt(10000) - 5000;
        Location randomLocation = new Location(player.getWorld(), x, y, z);
        player.teleport(randomLocation);
        player.sendMessage("You have been randomly teleported!");
    }
}