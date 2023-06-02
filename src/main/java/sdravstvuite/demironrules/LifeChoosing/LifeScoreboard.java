package sdravstvuite.demironrules.LifeChoosing;

import com.nickuc.openlogin.bukkit.api.events.AsyncAuthenticateEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.ScoreboardManager;
import sdravstvuite.demironrules.ConfigManager;
import sdravstvuite.demironrules.DemironRules;
import sdravstvuite.demironrules.ScoreBoards.FastBoard;

import java.util.HashMap;

import static sdravstvuite.demironrules.DemironRules.boards;

public class LifeScoreboard implements Listener {
    DemironRules plugin;
    public LifeScoreboard(DemironRules plugin) {
        this.plugin = plugin;
    }
    public static final HashMap<String, String> NameGenders = new HashMap<String, String>(){{
        put("man", "Мужчина");
        put("woman", "Женщина");}};
    public static final HashMap<String, String> NameRaces = new HashMap<String, String>(){{
        put("alad", "Элладец");
        put("varyag", "Варяг");
        put("vildice", "Вильдиец");
        put("acrice", "Акриец");
        put("gothland", "Готландец");}};

    @EventHandler
    public void onJoin(AsyncAuthenticateEvent e) {
        Player player = e.getPlayer();
        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "DEMIRON");
        boards.put(player.getUniqueId(), board);

    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        FastBoard board = boards.remove(p.getUniqueId());
        if (board != null) {
            board.delete();
        }
    }

    public static void updateBoard(FastBoard board) {
        board.updateLines(
                "",
                "Имя: " + ConfigManager.getData(board.getPlayer(), "name"),
                "",
                "Пол: " + NameGenders.get(ConfigManager.getData(board.getPlayer(), "gender")),
                "",
                "Раса: " + NameRaces.get(ConfigManager.getData(board.getPlayer(), "race")),
                "",
                "Жизней: " + ConfigManager.getData(board.getPlayer(), "count_lives")
        );
    }
}
