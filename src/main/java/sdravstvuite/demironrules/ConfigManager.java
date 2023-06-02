package sdravstvuite.demironrules;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.internal.annotation.Selection;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.selector.CuboidRegionSelector;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.stringtemplate.v4.ST;

import javax.security.auth.login.Configuration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigManager {
    private static File cfg;
    private static File cfgArea;
    private static FileConfiguration config;
    private static FileConfiguration configArea;
    public static void checkConfig() {
        cfg = new File(Bukkit.getServer().getPluginManager().getPlugin("DemironRules").getDataFolder(), "player_lives.yml");
        cfgArea = new File(Bukkit.getServer().getPluginManager().getPlugin("DemironRules").getDataFolder(), "mines.yml");
        if (!cfg.exists()) {
            try {
                cfg.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (!cfgArea.exists()){
            try {
                cfgArea.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(cfg);
        configArea = YamlConfiguration.loadConfiguration(cfgArea);
    }

    public static void save() {
        try {
            config.save(cfg);
            configArea.save(cfgArea);
        } catch (IOException e) {
            System.out.println("DemironR Data Base: Не могу сохранить файл... " + e);
        }
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(cfg);
    }
    public static void reloadArea() {
        configArea = YamlConfiguration.loadConfiguration(cfgArea);
    }



    public static FileConfiguration get() {
        return config;
    }
    public static FileConfiguration getConfigArea() {
        return configArea;
    }

//------------------------------------------------------------------------------------------------------------------//

    public static String getData(Player player, String type_data) {
        reload();
        String p = player.getDisplayName();
        Object answer = config.get("players." + p + "." + type_data);
        if (answer != null && answer != "null"){
            if (DemironRules.isObjectInteger(answer)){
                return Integer.toString((Integer) answer);
            }
            else {
                return (String) answer;
            }
        }else{
            return "null";
        }
    }

    public static void changeLife(Player player, String type_data, Object new_data) {
        reload();
        String p = player.getDisplayName();
        Object get = config.get("players." + p + "." + type_data);
            if (type_data.equalsIgnoreCase("count_lives") && get != null && get != "null") {
                config.set("players." + p + "." + type_data, (Integer) get + (Integer) new_data);
            }else{
                config.set("players." + p + "." + type_data, new_data);
            }
        save();
    }

//------------------------------------------------------------------------------------------------------------------//

    public static void createNewArea(String group, String selection_name, Region region, Material material) {
        reloadArea();
        World world = Bukkit.getWorld(region.getWorld().getName());
        Location max = BukkitAdapter.adapt(world, region.getMaximumPoint());
        Location min = BukkitAdapter.adapt(world, region.getMinimumPoint());

        List<String> locations = new ArrayList<>();
        for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    Location checkLoc = new Location(world, x, y, z);
                    if (checkLoc.getBlock().getType() == material) {
                        locations.add(checkLoc.getWorld().getName() + ":" + checkLoc.getBlockX() + ":" + checkLoc.getBlockY() + ":" + checkLoc.getBlockZ());
                    }
                }
            }
        }
        configArea.set("groups." + group + "." + selection_name + ".material", material.toString());
        configArea.set("groups." + group + "." + selection_name + ".blocks", locations);
        save();
        }

    public static List<Location> getSelectionBlocks(String group, String selection_name) {
        reloadArea();
        Object answer = configArea.get("groups." + group + "." + selection_name);
        if (answer != null){
            List<Location> locations = new ArrayList<>();
            List<String> locationsString = configArea.getStringList("groups." + group + "." + selection_name + ".blocks");
            for (String s : locationsString) {
                if (s == null || s.trim().equals("")) continue;
                String[] scuted = s.split(":");
                if(scuted.length == 4){
                    final World w = Bukkit.getServer().getWorld(scuted[0]);
                    final int x = Integer.parseInt(scuted[1]);
                    final int y = Integer.parseInt(scuted[2]);
                    final int z = Integer.parseInt(scuted[3]);
                    locations.add(new Location(w, x, y, z));
                }
            }
            return locations;
        }
        return null;
    }

    public static Material getAreaMaterial(String group, String selection_name) {
        reloadArea();
        return Material.getMaterial(configArea.getString("groups." + group + "." + selection_name + ".material"));
    }

    public static String getArea(String group, String selection_name) {
        reloadArea();
        ConfigurationSection answer = configArea.getConfigurationSection("groups." + group + "." + selection_name);
        if (answer != null) return answer.toString();
        return null;
    }

    public static String getGroup(String group) {
        reloadArea();
        ConfigurationSection answer = configArea.getConfigurationSection("groups." + group);
        if (answer != null) return answer.toString();
        return null;
    }

    public static void deleteByGroupArea(String group) {
        reloadArea();
        configArea.set("groups." + group, null);
        save();
    }

    public static void deleteByNameArea(String group, String selection_name) {
        reloadArea();
        configArea.set("groups." + group + "." + selection_name, null);
        save();
    }
}