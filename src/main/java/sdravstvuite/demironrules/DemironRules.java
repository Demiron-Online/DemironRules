package sdravstvuite.demironrules;


import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import sdravstvuite.demironrules.EventListeners.*;

import static sdravstvuite.demironrules.TownyPlugins.TownBlockTypes.registerCustomPlot;

public final class DemironRules extends JavaPlugin implements Listener {
    public static ItemStack AxeDrop;
    private static DemironRules instance;
    public void onLoad() {
        instance = this;
        registerCustomPlot();
    }

    public static DemironRules getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        DemironRules plugin;
        getServer().getPluginManager().registerEvents(new AxeEventListener(this), this);
        getServer().getPluginManager().registerEvents(new WaterInDesertEventListener(this), this);
        getServer().getPluginManager().registerEvents(new LavaEventListener(this), this);
        getServer().getPluginManager().registerEvents(new FishingEventListener(this), this);
        getServer().getPluginManager().registerEvents(new DeathEventListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}