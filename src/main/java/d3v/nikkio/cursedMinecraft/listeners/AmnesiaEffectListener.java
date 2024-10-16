package d3v.nikkio.cursedMinecraft.listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AmnesiaEffectListener implements Listener, CommandExecutor {

    private final JavaPlugin plugin;
    private final int amnesiaEffectDuration;
    private final int shuffleInterval;

    public AmnesiaEffectListener(JavaPlugin plugin, int amnesiaEffectDuration, int shuffleInterval) {
        this.plugin = plugin;
        this.amnesiaEffectDuration = amnesiaEffectDuration;
        this.shuffleInterval = shuffleInterval;
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getItemMeta().getDisplayName().equals("Potion of Amnesia")) {
            Player player = event.getPlayer();
            applyAmnesiaEffect(player);
        }
    }

    public void applyAmnesiaEffect(Player player) {
        startInventoryShuffleTask(player);
    }

    private void startInventoryShuffleTask(Player player) {
        new BukkitRunnable() {
            int elapsed = 0;

            @Override
            public void run() {
                if (elapsed >= amnesiaEffectDuration) {
                    cancel();
                    return;
                }
                if (player.isOnline()) {
                    shuffleInventory(player);
                } else {
                    cancel();
                }
                elapsed += shuffleInterval;
            }
        }.runTaskTimer(plugin, 0L, 20L * shuffleInterval);
    }

    private void shuffleInventory(Player player) {
        List<ItemStack> items = IntStream.range(0, player.getInventory().getSize())
                .mapToObj(player.getInventory()::getItem)
                .collect(Collectors.toList());
        Collections.shuffle(items);
        for (int i = 0; i < items.size(); i++) {
            player.getInventory().setItem(i, items.get(i));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("amnesia")) {
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
            applyAmnesiaEffect(targetPlayer);
            return true;
        }
        return false;
    }
}