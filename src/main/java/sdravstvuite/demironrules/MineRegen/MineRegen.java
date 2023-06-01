package sdravstvuite.demironrules.MineRegen;

import org.bukkit.Location;
import org.bukkit.Material;
import sdravstvuite.demironrules.ConfigManager;

import java.util.List;
import java.util.Set;

public class MineRegen {
    public static double getRInt(double min, double max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
    public static void MineRegen(String group, String selection_name){
        ConfigManager.reload();
        List<Location> blocks = ConfigManager.getSelectionBlocks(group, selection_name);
        Material blocksType = ConfigManager.getAreaMaterial(group, selection_name);
        assert blocks != null;
        for(Location loc : blocks){
            if(loc.getBlock().getType() != blocksType){
                loc.getBlock().setType(blocksType);
            }
        }
    }
    public static String MineRegenRandom(String group){

        ConfigManager.reload();
        Set<String> answer = ConfigManager.getConfigArea().getConfigurationSection("group." + group).getKeys(true);
        String[] areas = new String[answer.size()];
        answer.toArray(areas);
        int random = (int) getRInt(1, areas.length);
        MineRegen(group, areas[random]);
        return areas[random];
    }

}
