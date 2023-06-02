package sdravstvuite.demironrules.LifeChoosing;

import com.nickuc.openlogin.bukkit.api.events.AsyncAuthenticateEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerMoveEvent;
import sdravstvuite.demironrules.ConfigManager;
import sdravstvuite.demironrules.DemironRules;

import static sdravstvuite.demironrules.DemironRules.isObjectInteger;
import static sdravstvuite.demironrules.DemironRules.isObjectString;

public class LifeChoosing implements Listener {
    DemironRules plugin;
    public LifeChoosing(DemironRules plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerNotRegAndMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if(Integer.parseInt(ConfigManager.getData(p, "count_lives")) == 0) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin(AsyncAuthenticateEvent e) {
        Player p = e.getPlayer();
        if (ConfigManager.getData(p, "count_lives").equalsIgnoreCase("null")
                || Integer.parseInt(ConfigManager.getData(p, "count_lives")) == 0) {
            ConfigManager.reload();
            ConfigManager.get().set("players." + p.getDisplayName() + "." + "gender", "null");
            ConfigManager.get().set("players." + p.getDisplayName() + "." + "race", "null");
            ConfigManager.get().set("players." + p.getDisplayName() + "." + "name", "null");
            ConfigManager.get().set("players." + p.getDisplayName() + "." + "count_lives", 0);
            ConfigManager.save();
            TextComponent message = Component.text().content("Я мужчина\n").clickEvent(ClickEvent.runCommand("/changegenderman")).hoverEvent(HoverEvent.showText(Component.text().content("Я мужчина").build())).color(TextColor.fromHexString("#0384fc"))
                    .append(Component.text().content("Я девушка").clickEvent(ClickEvent.runCommand("/changegenderwoman")).hoverEvent(HoverEvent.showText(Component.text().content("Я женщина").build())).color(TextColor.fromHexString("#03e8fc"))).build();
            p.sendMessage(message);
            return;
        }
    }
}