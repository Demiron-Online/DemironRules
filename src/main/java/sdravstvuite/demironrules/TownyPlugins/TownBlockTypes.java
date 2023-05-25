package sdravstvuite.demironrules.TownyPlugins;
import com.palmergames.bukkit.towny.event.TownBlockTypeRegisterEvent;
import com.palmergames.bukkit.towny.event.TownInvitePlayerEvent;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.TownBlockData;
import com.palmergames.bukkit.towny.object.TownBlockType;
import com.palmergames.bukkit.towny.object.TownBlockTypeHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import sdravstvuite.demironrules.DemironRules;

public class TownBlockTypes implements Listener {
    DemironRules plugin;
    public TownBlockTypes(DemironRules plugin) {
        this.plugin = plugin;
    }
    public static void registerCustomPlot() {
        if (TownBlockTypeHandler.exists("Machine") && TownBlockTypeHandler.exists("Mint")) return;
        TownBlockType customPlotMachine = new TownBlockType("Machine", new TownBlockData() {
            @Override
            public String getMapKey() { return "M"; } // A single character to be shown on the /towny map and /towny map hud
            @Override
            public double getCost() { return 100.0; } // A cost that will be paid to set the plot type.
        });

        TownBlockType customPlotMint = new TownBlockType("Mint", new TownBlockData() {
            @Override
            public String getMapKey() { return "o"; } // A single character to be shown on the /towny map and /towny map hud
            @Override
            public double getCost() { return 101.1; } // A cost that will be paid to set the plot type.
        });

        try {
            TownBlockTypeHandler.registerType(customPlotMachine);
        } catch (TownyException e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
        try {
            TownBlockTypeHandler.registerType(customPlotMint);
        } catch (TownyException e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }

    @EventHandler
    public void onTownyLoadTownBlockTypes(TownBlockTypeRegisterEvent event) {
        registerCustomPlot();
    }

    @EventHandler
    public void confirmation(TownInvitePlayerEvent e) {
        Bukkit.getServer().getConsoleSender().sendMessage("Suka blyati " + e.getInvite().getReceiver().getPlayer() + "   " + e.getInvite().getDirectSender());
        if (e.getInvite().getReceiver().getPlayer() == null) return;
        e.getInvite().getReceiver().getPlayer().sendMessage("Вы клянетесь, сука?");
    }
}