package sdravstvuite.demironrules.EventListeners;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import sdravstvuite.demironrules.DemironRules;

public class LavaEventListener implements Listener {
    DemironRules plugin;
    public LavaEventListener(DemironRules plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void LavaOnBlockPlace(BlockPlaceEvent e) { //Поставка блока в лаву
        if (e.getBlock().getLocation().getBlock().getType() == Material.LAVA){
            e.getBlock().setType(Material.LAVA);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 2, 1);
            e.getBlock().getLocation().getWorld().playEffect(e.getBlock().getLocation(), Effect.EXTINGUISH, 2004);
        }
    }

    @EventHandler
    public void LavaEntityDamageEvent(EntityDamageEvent event) { //Наступание в лаву
        if (!(event.getEntity() instanceof Damageable)) return;
        if(event.getCause() == EntityDamageEvent.DamageCause.LAVA) event.setDamage(100);
    }
    @EventHandler
    public void LavaBlockFormEvent(BlockFormEvent event) { //Наступание в лаву
        if (event.getBlock().getType() == Material.LAVA){
            Location locUp = event.getBlock().getLocation().add(0,1,0);
            Location locDown = event.getBlock().getLocation().add(0,-1,0);
            Block northBlock = event.getBlock().getRelative(BlockFace.NORTH);
            Block southBlock = event.getBlock().getRelative(BlockFace.SOUTH);
            Block westBlock = event.getBlock().getRelative(BlockFace.WEST);
            Block eastBlock = event.getBlock().getRelative(BlockFace.EAST);
            if (locDown.getBlock().getType() == Material.WATER) {locDown.getBlock().setType(Material.COBBLESTONE); event.setCancelled(true);}
            if (locUp.getBlock().getType() == Material.WATER) {locUp.getBlock().setType(Material.COBBLESTONE); event.setCancelled(true);}
            if (northBlock.getType() == Material.WATER) {northBlock.setType(Material.COBBLESTONE); event.setCancelled(true);}
            if (southBlock.getType() == Material.WATER) {southBlock.setType(Material.COBBLESTONE); event.setCancelled(true);}
            if (westBlock.getType() == Material.WATER) {westBlock.setType(Material.COBBLESTONE); event.setCancelled(true);}
            if (eastBlock.getType() == Material.WATER) {eastBlock.setType(Material.COBBLESTONE); event.setCancelled(true);}

        }
    }
}
