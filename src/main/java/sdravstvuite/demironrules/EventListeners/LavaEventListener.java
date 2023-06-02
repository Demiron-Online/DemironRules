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
import org.bukkit.event.player.PlayerMoveEvent;
import sdravstvuite.demironrules.DemironRules;

import java.util.List;

public class LavaEventListener implements Listener {
    DemironRules plugin;
    public LavaEventListener(DemironRules plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void LavaOnBlockPlace(BlockPlaceEvent e) { //Поставка блока в лаву
        if (e.getBlockReplacedState().getType() == Material.LAVA){
            e.getBlock().setType(Material.LAVA);
            e.getBlock().getLocation().getWorld().playSound(e.getBlock().getLocation().add(0,1,0), Sound.BLOCK_FIRE_EXTINGUISH, 2, 1);
            e.getBlock().getLocation().getWorld().playEffect(e.getBlock().getLocation().add(0,1,0), Effect.EXTINGUISH, null);
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
    @EventHandler
    public void onPlayerMovingCloseToLava(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        Location loc = p.getLocation();
        loc = loc.add(3, -3, 3);
        for (int h = 0; h < 7; h++) {
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (loc.getBlock().getType() == Material.LAVA) {
                        p.setFireTicks(80);
                    }
                    loc = loc.add(0, 0, -1);
                }
                loc = loc.add(-1, 0, 7);
            }
            loc = loc.add(7, 1, 7);
        }
    }
}
