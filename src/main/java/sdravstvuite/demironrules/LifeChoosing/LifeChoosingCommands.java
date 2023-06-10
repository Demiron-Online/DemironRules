package sdravstvuite.demironrules.LifeChoosing;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import sdravstvuite.demironrules.ConfigManager;
import sdravstvuite.demironrules.DemironRules;

public class LifeChoosingCommands implements CommandExecutor {
    ConversationFactory factory = new ConversationFactory(DemironRules.getInstance());

    TextComponent racesMessage = Component.text().content("Я Элладец").clickEvent(ClickEvent.runCommand("/changeracealad")).color(TextColor.fromHexString("#0384fc"))
            .append(Component.text().content("  Я Варяг").clickEvent(ClickEvent.runCommand("/changeracevaryag")).color(TextColor.fromHexString("#fcba03")))
            .append(Component.text().content("  Я Вильдиец\n").clickEvent(ClickEvent.runCommand("/changeracevildice")).color(TextColor.fromHexString("#ffcb3b")))
            .append(Component.text().content("     Я Акриец").clickEvent(ClickEvent.runCommand("/changeraceacrice")).color(TextColor.fromHexString("#eb352f")))
            .append(Component.text().content("     Я Готландец").clickEvent(ClickEvent.runCommand("/changeracegothland")).color(TextColor.fromHexString("#ed694c")))
            .build();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(s.equalsIgnoreCase("changegenderwoman")){
                String takedData = ConfigManager.getData(p, "count_lives");
                if(Integer.parseInt(takedData) == 0){
                    ConfigManager.changeLife((Player) sender, "gender", "woman");
                    sender.sendMessage("Ты теперь женщина");
                    p.sendMessage(racesMessage);
                }
                 else{
                    sender.sendMessage("Нельзя изменить пол при жизни.");
                }
                return true;
            }
            if(s.equalsIgnoreCase("changegenderman")){
                String takedData = ConfigManager.getData(p, "count_lives");
                if(Integer.parseInt(takedData) == 0){
                    ConfigManager.changeLife((Player) sender, "gender", "man");
                    sender.sendMessage("Ты теперь мужчина");
                    p.sendMessage(racesMessage);
                } else{
                sender.sendMessage("Нельзя изменить пол при жизни.");
                }
                return true;
            }
            if(s.equalsIgnoreCase("changeracealad")){
                if(Integer.parseInt(ConfigManager.getData(p, "count_lives")) == 0){
                    ConfigManager.changeLife((Player) sender, "race", "alad");
                    Conversation conv = factory.withFirstPrompt(new LifeConverstation()).withLocalEcho(false).buildConversation((Conversable) sender);
                    if(!p.isConversing()) {
                        conv.begin();
                    }
                    sender.sendMessage("Ты теперь Элладец");
                }else{
                    sender.sendMessage("Нельзя изменить расу при жизни.");
                }
            }
            if(s.equalsIgnoreCase("changeracevaryag")){
                if(Integer.parseInt(ConfigManager.getData(p, "count_lives")) == 0){
                    ConfigManager.changeLife((Player) sender, "race", "varyag");
                    Conversation conv = factory.withFirstPrompt(new LifeConverstation()).withLocalEcho(false).buildConversation((Conversable) sender);
                    if(!p.isConversing()) {
                        conv.begin();
                    }
                    sender.sendMessage("Ты теперь Варяг");
                }else{
                    sender.sendMessage("Нельзя изменить расу при жизни.");
                }
            }
            if(s.equalsIgnoreCase("changeracevildice")){
                if(Integer.parseInt(ConfigManager.getData(p, "count_lives")) == 0){
                    ConfigManager.changeLife((Player) sender, "race", "vildice");
                    Conversation conv = factory.withFirstPrompt(new LifeConverstation()).withLocalEcho(false).buildConversation((Conversable) sender);
                    if(!p.isConversing()) {
                        conv.begin();
                    }
                    sender.sendMessage("Ты теперь Вильдиец");
                }else{
                    sender.sendMessage("Нельзя изменить расу при жизни.");
                }
            }
            if(s.equalsIgnoreCase("changeraceacrice")){
                if(Integer.parseInt(ConfigManager.getData(p, "count_lives")) == 0){
                    ConfigManager.changeLife((Player) sender, "race", "acrice");
                    Conversation conv = factory.withFirstPrompt(new LifeConverstation()).withLocalEcho(false).buildConversation((Conversable) sender);
                    if(!p.isConversing()) {
                        conv.begin();
                    }
                    sender.sendMessage("Ты теперь Акриец");
                }else{
                    sender.sendMessage("Нельзя изменить расу при жизни.");
                }
            }
            if(s.equalsIgnoreCase("changeracegothland")){
                if(Integer.parseInt(ConfigManager.getData(p, "count_lives")) == 0){
                    ConfigManager.changeLife((Player) sender, "race", "gothland");
                    Conversation conv = factory.withFirstPrompt(new LifeConverstation()).withLocalEcho(false).buildConversation((Conversable) sender);
                    if(!p.isConversing()) {
                        conv.begin();
                    }
                    sender.sendMessage("Ты теперь Готландец");
                }else{
                    sender.sendMessage("Нельзя изменить расу при жизни.");
                }
            }
            if(s.equalsIgnoreCase("reloadlife")){
                if (!sender.isOp()) {
                    sender.sendMessage(ChatColor.RED + "Вы не администратор.");
                    return true;
                }
                ConfigManager.checkConfig();
                sender.sendMessage(ChatColor.GOLD + "Конфиг жизней людей был обновлен");
            }
        }
        return false;
    }
}
// Элладцы
// Варяги
// Вильдийцы
// Акрийцы
// Готландцы
// я русский
