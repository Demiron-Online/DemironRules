package sdravstvuite.demironrules.MineRegen;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import sdravstvuite.demironrules.ConfigManager;
import sdravstvuite.demironrules.DemironRules;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static sdravstvuite.demironrules.EventListeners.DeathEventListener.loadWorlds;

public class MineRegenTimer {
    public static void MineRegenStartTimer() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DemironRules.getInstance(), () -> {
            for (String s : ConfigManager.getConfigArea().getConfigurationSection("groups").getKeys(false)){
                if (s == null) continue;
                Bukkit.getConsoleSender().sendMessage(MineRegen.MineRegenRandom(s) + " был восстановлен");
            }
        }, 0, 60 * 60 * 20); // 60 секунд * 60 минут * кол-во тиков в секунду = 1 час
    }
}
