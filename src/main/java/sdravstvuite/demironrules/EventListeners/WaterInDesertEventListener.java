package sdravstvuite.demironrules.EventListeners;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import sdravstvuite.demironrules.DemironRules;

import static org.bukkit.Bukkit.getWorld;

public class WaterInDesertEventListener implements Listener {
    DemironRules plugin;
    public WaterInDesertEventListener(DemironRules plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void OnWaterBlockPlace(BlockPlaceEvent e){
        if(e.getBlock().getType() != Material.WATER) return;
        if(e.getBlock().getBiome() != Biome.DESERT) return;
        e.getBlock().setType(Material.AIR);
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 2, 1);
        e.getBlock().getLocation().getWorld().playEffect(e.getBlock().getLocation(), Effect.EXTINGUISH, 2004);
    }
}
