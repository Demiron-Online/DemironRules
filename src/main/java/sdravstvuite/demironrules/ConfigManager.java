package sdravstvuite.demironrules;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigManager {
    private static File cfg;
    private static FileConfiguration config;
    public static void checkConfig() {
        cfg = new File(Bukkit.getServer().getPluginManager().getPlugin("DemironRules").getDataFolder(), "player_lives.yml");
        if (!cfg.exists()) {
            try {
                cfg.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(cfg);
    }

    public static void save() {
        try {
            config.save(cfg);
        } catch (IOException e) {
            System.out.println("DemironR Data Base: Не могу сохранить файл... " + e);
        }
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(cfg);
    }
    public static FileConfiguration get() {
        return config;
    }

    public static String getData(Player player, String type_data) {
        reload();
        String p = player.getDisplayName();
        Object answer = config.get("players." + p + "." + type_data);
        if (answer != null && answer != "null"){
            return (String) answer;
        }else{
            return "null";
        }
    }


    public static void changeLife(Player player, String type_data, String new_data) {
        reload();
        String p = player.getDisplayName();
        Object get = config.get("players." + p + "." + type_data);
        if (get != null){
            if(type_data.equalsIgnoreCase("count_lives")) {
                config.set("players." + p + "." + type_data, Integer.parseInt((String) get) + Integer.parseInt(new_data));
            }else{
                config.set("players." + p + "." + type_data, new_data);
            }
        }else{
            HashMap<String, String> sTypes = new HashMap<String, String>(){{
                put("gender", "null");
                put("race", "null");
                put("name", "null");
                put("count_lives", "0");}};
            sTypes.replace(type_data, new_data);
            config.set("players." + p + "." + "gender", sTypes.get("gender"));
            config.set("players." + p + "." + "race", sTypes.get("race"));
            config.set("players." + p + "." + "name", sTypes.get("name"));
            config.set("players." + p + "." + "count_lives", sTypes.get("count_lives"));
        }
        save();
    }



}
