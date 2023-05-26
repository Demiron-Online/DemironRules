package sdravstvuite.demironrules.EventListeners;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getPlayer().isDead()) e.getPlayer().spigot().respawn();
        String lives = ConfigManager.getData(e.getPlayer(), "count_lives");
        if (!lives.equalsIgnoreCase("null")) {
            if (Integer.parseInt(lives) == 1) {
                ConfigManager.changeLife(e.getPlayer(), "gender", "null");
                ConfigManager.changeLife(e.getPlayer(), "race", "null");
                ConfigManager.changeLife(e.getPlayer(), "name", "null");
                ConfigManager.changeLife(e.getPlayer(), "count_lives", "-1");
                e.getPlayer().kick(Component.text().content("Это была твоя последняя жизнь...").build());

            } else if (Integer.parseInt(lives) == 0) {
                e.getPlayer().kick(Component.text().content("Даже так ты как-то умудрился...").build());
            } else {
                Location loc = new Location(Bukkit.getWorld("world_the_end"), 0, 100, 0);
                e.getPlayer().teleport(loc);
                task = new BukkitRunnable() {
                    int time = 15; //or any other number you want to start countdown from
                    Location backLoc;
                    public void run() {
                        if (!e.getPlayer().isOnline()) {
                            return;
                        }
                        if (this.time == 0) {
                            if (e.getPlayer().getBedSpawnLocation() != null) {
                                backLoc = e.getPlayer().getBedSpawnLocation();
                            } else {
                                backLoc = new Location(Bukkit.getWorld("world"), 0, 90, 0);
                                e.getPlayer().sendMessage("Сука " + backLoc);
                                e.getPlayer().sendMessage("" + loc);
                                e.getPlayer().sendMessage("" + Bukkit.getWorlds());
                            }
                            e.getPlayer().teleport(backLoc);
                            task.cancel();
                            return;
                        }
                        e.getPlayer().sendMessage(this.time + " second(s) remains!");
                        time--;
                    }
                }.runTaskTimer(DemironRules.getInstance(), 0L, 20L);
            }
        }
    }
}
