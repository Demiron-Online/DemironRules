package sdravstvuite.demironrules.EventListeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import sdravstvuite.demironrules.DemironRules;

import static sdravstvuite.demironrules.DemironRules.AxeDrop;

// Плагин на бросание топора правой кнопкой
public class AxeEventListener implements Listener {
    DemironRules plugin;
    public AxeEventListener(DemironRules plugin) {
        this.plugin = plugin;
    }

//    @EventHandler
//    public void AxeOnRightClick(PlayerInteractEvent e) {
//        Player p = e.getPlayer();
//        Action a = e.getAction();
//        if (e.getItem() == null) return;
//        if ((a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) && e.getItem().getType() == Material.IRON_AXE) {
//            ItemStack axe = e.getItem();
//            assert axe != null;
//            if (axe.getType().equals(Material.IRON_AXE)) {
//                DemironRules.AxeDrop = axe;
//                Location loc = p.getEyeLocation().add(p.getLocation().getDirection());
//                Snowball snowball = p.getWorld().spawn(loc, Snowball.class);
//                snowball.setItem(axe);
//                snowball.setVelocity(p.getLocation().getDirection().multiply(0.7));
//                axe.setAmount(axe.getAmount() - 1);
//            }
//        }
//    }
//
//    @EventHandler
//    public void AxeOnSnowballHit(ProjectileHitEvent e) {
//        if (!(e.getHitEntity() instanceof LivingEntity))
//            return;
//        LivingEntity ent = (LivingEntity) e.getHitEntity();
//        Projectile proj = e.getEntity();
//        if (proj instanceof Snowball) {
//            System.out.println("work");
//            ItemStack item = new ItemStack(Material.IRON_AXE);
//            ItemMeta meta = item.getItemMeta();
//            Damageable damage = (Damageable) meta;
//            if (item.getType().equals(Material.IRON_AXE)) {
//                ent.damage(8);
//                damage.setDamage(damage.getDamage() + 70);
//                item.setItemMeta(damage);
//                e.getHitEntity().getLocation().getWorld().dropItemNaturally(e.getHitEntity().getLocation(), item);
//            }
//        }
//    }
}
