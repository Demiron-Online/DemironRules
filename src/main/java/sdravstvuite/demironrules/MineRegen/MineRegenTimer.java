package sdravstvuite.demironrules.MineRegen;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import sdravstvuite.demironrules.ConfigManager;
import sdravstvuite.demironrules.DemironRules;

import java.util.List;

public class MineRegenTimer {
    public static void MineRegenStartTimer() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DemironRules.getInstance(), new Runnable() {
            @Override
            public void run() {
                List<String> groups = ConfigManager.getConfigArea().getStringList("group");
                for (String s : groups){
                    MineRegen.MineRegenRandom(s);
                }
            }
        }, 0, 4320000);
    }
}
