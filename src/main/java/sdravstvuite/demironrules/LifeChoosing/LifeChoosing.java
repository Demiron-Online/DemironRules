package sdravstvuite.demironrules.LifeChoosing;

import com.nickuc.openlogin.bukkit.api.events.AsyncAuthenticateEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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
    public void onPlayerJoin(AsyncAuthenticateEvent e) {
        Player p = e.getPlayer();
        if (isObjectString(ConfigManager.getData(p, "count_lives"))) {
            if (ConfigManager.getData(p, "count_lives").equalsIgnoreCase("null")) {
                TextComponent message = Component.text().content("Я мужчина").clickEvent(ClickEvent.runCommand("/changegenderman")).color(TextColor.fromHexString("#0384fc"))
                        .append(Component.text().content("  Я девушка").clickEvent(ClickEvent.runCommand("/changegenderwoman")).color(TextColor.fromHexString("#03e8fc"))).build();
                p.sendMessage(message);
                return;
            }
        }else if (isObjectInteger(ConfigManager.getData(p, "count_lives"))) {
            if (Integer.parseInt(ConfigManager.getData(p, "count_lives")) == 0) {
                TextComponent message = Component.text().content("Я мужчина").clickEvent(ClickEvent.runCommand("/changegenderman")).color(TextColor.fromHexString("#0384fc"))
                        .append(Component.text().content("  Я девушка").clickEvent(ClickEvent.runCommand("/changegenderwoman")).color(TextColor.fromHexString("#03e8fc"))).build();
                p.sendMessage(message);
            }
        }
    }
}

