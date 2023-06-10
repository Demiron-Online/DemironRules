package sdravstvuite.demironrules;


import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import sdravstvuite.demironrules.EventListeners.*;
import sdravstvuite.demironrules.LifeChoosing.LifeChoosing;
import sdravstvuite.demironrules.LifeChoosing.LifeChoosingCommands;
import sdravstvuite.demironrules.LifeChoosing.LifeScoreboard;
import sdravstvuite.demironrules.MineRegen.MineRegen;
import sdravstvuite.demironrules.MineRegen.MineRegenCommands;
import sdravstvuite.demironrules.MineRegen.MineRegenTimer;
import sdravstvuite.demironrules.ScoreBoards.FastBoard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static sdravstvuite.demironrules.LifeChoosing.LifeScoreboard.updateBoard;


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
    public static Map<UUID, FastBoard> boards = new HashMap<>();
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
        getServer().getPluginManager().registerEvents(new LifeScoreboard(this), this);

        getCommand("reloadlife").setExecutor(new LifeChoosingCommands());

        getCommand("changegenderwoman").setExecutor(new LifeChoosingCommands());
        getCommand("changegenderman").setExecutor(new LifeChoosingCommands());

        getCommand("changeracealad").setExecutor(new LifeChoosingCommands());
        getCommand("changeracevaryag").setExecutor(new LifeChoosingCommands());
        getCommand("changeracevildice").setExecutor(new LifeChoosingCommands());
        getCommand("changeraceacrice").setExecutor(new LifeChoosingCommands());
        getCommand("changeracegothland").setExecutor(new LifeChoosingCommands());;

        getCommand("mineregencreate").setExecutor(new MineRegenCommands());
        getCommand("mineregendelete").setExecutor(new MineRegenCommands());
        getCommand("mineregendeletegroup").setExecutor(new MineRegenCommands());
        getCommand("mineregeninsta").setExecutor(new MineRegenCommands());
        getCommand("mineregengrouprandom").setExecutor(new MineRegenCommands());

        MineRegenTimer.MineRegenStartTimer();

        getServer().getPluginManager().registerEvents(this, this);

        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : boards.values()) {
                updateBoard(board);
            }
        }, 0, 200);
    }
    @EventHandler
    public void HideNicknames(PlayerJoinEvent e){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard score = manager.getNewScoreboard();
        Team t = score.getTeam("nhide");
        if(t == null) {
            t = score.registerNewTeam("nhide");
            t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
            t.setOption(Team.Option.DEATH_MESSAGE_VISIBILITY, Team.OptionStatus.NEVER);
        }
        t.addEntry(e.getPlayer().getName());
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ConfigManager.save();
    }


}