package sdravstvuite.demironrules.EventListeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sdravstvuite.demironrules.DemironRules;

// ДООООООООООООООООООООООООООООООООООООООООООООООООПИИИИИИИИИИИИИИИИИИИИИИИИИИИИИИИИИИИИИИСАААААААААААААААААААААААААТЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬЬ!!!!!!!!!!!!!!!!!!!!!!
public class FishingEventListener implements Listener { // Ловля сундука вместо рыбы с каким-то шансом

    DemironRules plugin;
    public FishingEventListener(DemironRules plugin) {
        this.plugin = plugin;
    }
    public static double getRInt(double min, double max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
    @EventHandler
    public void onFishing(PlayerFishEvent e) {
        if(e.getCaught() instanceof Item){
            int n = (int) getRInt(1, 10);
            if (n > 8) {
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 1);
                ItemStack openCase = new ItemStack(Material.CHEST);
                openCase.getItemMeta().setDisplayName(ChatColor.BLUE + "Мокрое Сокровище");
                ((Item) e.getCaught()).setItemStack(openCase);
            }
        }
    }
}
