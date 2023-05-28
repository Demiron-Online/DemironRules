package sdravstvuite.demironrules;


import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import sdravstvuite.demironrules.EventListeners.*;
import sdravstvuite.demironrules.LifeChoosing.LifeChoosing;
import sdravstvuite.demironrules.LifeChoosing.LifeChoosingCommands;
import sdravstvuite.demironrules.MineRegen.MineRegen;
import sdravstvuite.demironrules.MineRegen.MineRegenCommands;
import sdravstvuite.demironrules.MineRegen.MineRegenTimer;


public final class DemironRules extends JavaPlugin implements Listener {
    public static ItemStack AxeDrop;
    private ConfigManager configManager;
    private static DemironRules instance;

    public void onLoad() {
        instance = this;
    }

    public static DemironRules getInstance() {
        return instance;
    }

    public static boolean isObjectInteger(Object o) {
        return o instanceof Integer;
    }
    public static boolean isObjectString(Object o) {
        return o instanceof String;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        DemironRules plugin;
        ConfigManager.checkConfig();
        ConfigManager.save();
        getServer().getPluginManager().registerEvents(new AxeEventListener(this), this);
        getServer().getPluginManager().registerEvents(new WaterInDesertEventListener(this), this);
        getServer().getPluginManager().registerEvents(new LavaEventListener(this), this);
        getServer().getPluginManager().registerEvents(new FishingEventListener(this), this);
        getServer().getPluginManager().registerEvents(new DeathEventListener(this), this);
        getServer().getPluginManager().registerEvents(new LifeChoosing(this), this);

        getCommand("ChangeGenderWoman").setExecutor(new LifeChoosingCommands());
        getCommand("ChangeGenderMan").setExecutor(new LifeChoosingCommands());
        getCommand("changeracealad").setExecutor(new LifeChoosingCommands());
        getCommand("changeracevaryag").setExecutor(new LifeChoosingCommands());
        getCommand("changeracevildice").setExecutor(new LifeChoosingCommands());
        getCommand("changeraceacrice").setExecutor(new LifeChoosingCommands());
        getCommand("changeracealad").setExecutor(new LifeChoosingCommands());;
        getCommand("mineregencreate").setExecutor(new MineRegenCommands());
        getCommand("mineregendelete").setExecutor(new MineRegenCommands());
        getCommand("mineregendeletegroup").setExecutor(new MineRegenCommands());
        getCommand("mineregeninsta").setExecutor(new MineRegenCommands());
        getCommand("mineregengrouprandom").setExecutor(new MineRegenCommands());

        MineRegenTimer.MineRegenStartTimer();
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ConfigManager.save();
    }


}