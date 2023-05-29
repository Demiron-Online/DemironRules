package sdravstvuite.demironrules.EventListeners;

import com.nickuc.openlogin.bukkit.api.events.AsyncAuthenticateEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import sdravstvuite.demironrules.ConfigManager;
import sdravstvuite.demironrules.DemironRules;

public class DeathEventListener implements Listener {
    DemironRules plugin;

    public DeathEventListener(DemironRules plugin) {
        this.plugin = plugin;
    }

    private BukkitTask task;

    public void loadWorlds() {
        new WorldCreator("world").createWorld();
        new WorldCreator("world_the_end").createWorld();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getPlayer().isDead()) e.getPlayer().spigot().respawn();
        String lives = ConfigManager.getData(e.getPlayer(), "count_lives");
        if (!lives.equalsIgnoreCase("null")) {
            if (Integer.parseInt(lives) == 1) {
                ConfigManager.changeLife(e.getPlayer(), "gender", "null");
                ConfigManager.changeLife(e.getPlayer(), "race", "null");
                ConfigManager.changeLife(e.getPlayer(), "name", "null");
                ConfigManager.changeLife(e.getPlayer(), "count_lives", -1);
                e.getPlayer().kick(Component.text().content("Это была твоя последняя жизнь...").build());
            } else if (Integer.parseInt(lives) == 0) {
                e.getPlayer().kick(Component.text().content("Даже так ты как-то умудрился...").build());
            } else {
                loadWorlds();
                Location loc = new Location(Bukkit.getWorld("world_the_end"), 0, 100, 0);
                e.getPlayer().teleport(loc);
                ConfigManager.changeLife(e.getPlayer(), "count_lives", -1);
                startEndTimer(e.getPlayer(), 15);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamageInEnd(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if (p.getWorld() == Bukkit.getWorld("world_the_end")) {
            e.setCancelled(true);
            if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                Location backLoc = new Location(Bukkit.getWorld("world"), 0, 90, 0);
                p.teleport(backLoc);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(AsyncAuthenticateEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld() == Bukkit.getWorld("world_the_end")) {
            startEndTimer(e.getPlayer(), 15);
        }
    }

    public void startEndTimer(Player p, Integer time) {
        task = new BukkitRunnable() {
            Location backLoc;
            Integer timer_time = time;
            public void run() {
                if (!p.isOnline()) {
                    return;
                }
                if (p.isDead()) {
                    loadWorlds();
                    p.spigot().respawn();
                    Location loc = new Location(Bukkit.getWorld("world_the_end"), 0, 100, 0);
                    p.teleport(loc);
                }
                if (timer_time == 0) {
                    if (p.getBedSpawnLocation() != null) {
                        backLoc = p.getBedSpawnLocation();
                    } else {
                        backLoc = new Location(Bukkit.getWorld("world"), 0, 90, 0);
                    }
                    loadWorlds();
                    p.teleport(backLoc);
                    task.cancel();
                    return;
                }
                p.sendMessage(timer_time + " second(s) remains!");
                timer_time--;
            }
        }.runTaskTimer(DemironRules.getInstance(), 0L, 20L);
    }
}