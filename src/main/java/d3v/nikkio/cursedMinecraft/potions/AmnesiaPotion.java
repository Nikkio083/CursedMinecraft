package d3v.nikkio.cursedMinecraft.potions;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AmnesiaPotion {

    public static ItemStack createAmnesiaPotion(JavaPlugin plugin) {
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("Potion of Amnesia");
            meta.addCustomEffect(new PotionEffect(PotionEffectType.NAUSEA, 6000, 1), true); // 5 minutes of nausea
            meta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 6000, 1), true); // 5 minutes of blindness
            potion.setItemMeta(meta);
        }
        return potion;
    }
}