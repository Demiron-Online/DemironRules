package sdravstvuite.demironrules.EventListeners;

import com.nickuc.openlogin.bukkit.api.events.AsyncAuthenticateEvent;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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

    String world = "Demiron";
    String world_the_end = "Demiron_the_end";
    Integer time_in_limb = 300;
    public void loadWorlds() {
        new WorldCreator(world).createWorld();
        new WorldCreator(world_the_end).createWorld();
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
                Location loc = new Location(Bukkit.getWorld(world_the_end), 0, 100, 0);
                e.getPlayer().teleport(loc);
                ConfigManager.changeLife(e.getPlayer(), "count_lives", -1);
                startEndTimer(e.getPlayer(), time_in_limb);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamageInEnd(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if (p.getWorld() == Bukkit.getWorld(world_the_end)) {
            e.setCancelled(true);
            if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                Location backLoc = new Location(Bukkit.getWorld(world_the_end), 0, 100, 0);
                p.teleport(backLoc);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(AsyncAuthenticateEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE) return;
        if (p.getWorld() == Bukkit.getWorld(world_the_end)) {
            startEndTimer(e.getPlayer(), time_in_limb);
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
                    Location loc = new Location(Bukkit.getWorld(world_the_end), 0, 100, 0);
                    p.teleport(loc);
                }
                if (timer_time == 0) {
                    if (p.getBedSpawnLocation() != null) {
                        backLoc = p.getBedSpawnLocation();
                    } else {
                        backLoc = new Location(Bukkit.getWorld(world), -52.5, 68, 34.5);
                    }
                    loadWorlds();
                    p.teleport(backLoc);
                    task.cancel();
                    return;
                }
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("До возрождения: " + timer_time + " секунд"));
                timer_time--;
            }
        }.runTaskTimer(DemironRules.getInstance(), 0L, 20L);
    }
}