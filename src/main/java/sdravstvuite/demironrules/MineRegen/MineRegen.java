package sdravstvuite.demironrules.MineRegen;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import sdravstvuite.demironrules.ConfigManager;

import java.util.List;
import java.util.Set;

import static sdravstvuite.demironrules.EventListeners.DeathEventListener.loadWorlds;

public class MineRegen {
    public static double getRInt(double min, double max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
    public static void MineRegen(String group, String selection_name){
        ConfigManager.reloadArea();
        List<Location> blocks = ConfigManager.getSelectionBlocks(group, selection_name);
        Material blocksType = ConfigManager.getAreaMaterial(group, selection_name);
        assert blocks != null;
        for(Location loc : blocks){
            if(loc.getWorld() != null) {
                if (loc.getBlock().getType() != blocksType) {
                    loadWorlds();
                    loc.getBlock().setType(blocksType);
                }
            }
        }
    }

    public static String MineRegenRandom(String group){
        ConfigManager.reloadArea();
        Set<String> answer = ConfigManager.getConfigArea().getConfigurationSection("groups." + group).getKeys(false);
        String[] areas = new String[answer.size()];
        answer.toArray(areas);
        int random = (int) getRInt(0, areas.length-1);
        MineRegen(group, areas[random]);
        return areas[random];
    }
}
