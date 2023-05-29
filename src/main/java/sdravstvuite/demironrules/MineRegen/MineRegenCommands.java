package sdravstvuite.demironrules.MineRegen;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.internal.annotation.Selection;
import com.sk89q.worldedit.bukkit.*;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.SessionOwner;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import com.sk89q.worldedit.bukkit.*;
import sdravstvuite.demironrules.ConfigManager;
import java.util.Set;

import static sdravstvuite.demironrules.ConfigManager.*;

public class MineRegenCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(sender instanceof Player){
            Player p = (Player) sender;

            if(s.equalsIgnoreCase("mineregencreate")){
                if (strings.length <= 2) {
                    p.sendMessage(ChatColor.RED+"Используй команду в виде /mineregencreate " + ChatColor.WHITE + "<название_группы> <название_рудника> <блок_рудника>!");
                }else{
                    WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
                    BukkitPlayer player = worldEdit.wrapPlayer(p);
                    Region region = worldEdit.getWorldEdit().getSessionManager().get(player).getSelection(worldEdit.getSession(p).getSelectionWorld());
                    if (region != null){
                        if (Material.getMaterial(strings[2]) != null) {
                            createNewArea(strings[0], strings[1], region, Material.getMaterial(strings[2]));
                            p.sendMessage(ChatColor.GREEN+"Рудник " + strings[0] + " с " + strings[1] + " был добавлен в группу " + strings[2]);
                            p.sendMessage(ChatColor.GOLD+"Если вы ошиблись при создании, используйте /mineregendeletegroup " + ChatColor.WHITE + "<название_группы>");
                            p.sendMessage(ChatColor.GOLD+"Или /mineregendelete " + ChatColor.WHITE + "<название_группы> <название_рудника>");
                        }else {
                            p.sendMessage(ChatColor.RED+"Материала " + strings[2] + " не существует");
                        }
                    }else{
                        p.sendMessage(ChatColor.RED+"Для начала выделите регион топориком.");
                    }
                }
            }
            if(s.equalsIgnoreCase("mineregendelete")) {
                if (strings.length <= 1) {
                    p.sendMessage(ChatColor.RED+"Используй команду в виде /mineregendelete " + ChatColor.WHITE + "<название_группы> <название_рудника>");
                }else{
                    if (getGroup(strings[0]) != null){
                        if (getArea(strings[0], strings[1]) != null){
                            deleteByNameArea(strings[0], strings[1]);
                        }else {
                            p.sendMessage(ChatColor.RED+"Рудника под названием " + ChatColor.WHITE + strings[1] + ChatColor.RED + " в группе " + ChatColor.WHITE + strings[0] + ChatColor.RED + " не существует.");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED+"Группы под названием " + ChatColor.WHITE + strings[0] + ChatColor.RED + " не существует.");
                    }
                }
            }
            if(s.equalsIgnoreCase("mineregendeletegroup")) {
                if (strings.length == 0) {
                    p.sendMessage(ChatColor.RED+"Используй команду в виде /mineregendeletegroup " + ChatColor.WHITE + "<название_группы>");
                }else{
                    if (getGroup(strings[0]) != null){
                            deleteByNameArea(strings[0], strings[1]);
                    } else {
                        p.sendMessage(ChatColor.RED+"Группы под названием " + ChatColor.WHITE + strings[0] + ChatColor.RED + " не существует.");
                    }
                }
            }
            if(s.equalsIgnoreCase("mineregeninsta")) {
                if (strings.length <= 1) {
                    p.sendMessage(ChatColor.RED+"Используй команду в виде /mineregeninsta " + ChatColor.WHITE + "<название_группы> <название_рудника>");
                }else{
                    if (getGroup(strings[0]) != null){
                        if (getArea(strings[0], strings[1]) != null){
                            MineRegen.MineRegen(strings[0], strings[1]);
                        }else {
                            p.sendMessage(ChatColor.RED+"Рудника под названием " + ChatColor.WHITE + strings[1] + ChatColor.RED + " в группе " + ChatColor.WHITE + strings[0] + ChatColor.RED + " не существует.");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED+"Группы под названием " + ChatColor.WHITE + strings[0] + ChatColor.RED + " не существует.");
                    }
                }
            }
            if(s.equalsIgnoreCase("mineregengrouprandom")) {
                if (strings.length == 0) {
                    p.sendMessage(ChatColor.RED+"Используй команду в виде /mineregengrouprandom " + ChatColor.WHITE + "<название_группы>");
                }else{
                    if (getGroup(strings[0]) != null){
                        p.sendMessage(ConfigManager.getConfigArea().getConfigurationSection("groups." + strings[0]).toString());
                        p.sendMessage(MineRegen.MineRegenRandom(strings[0]));
                    } else {
                        p.sendMessage(ChatColor.RED+"Группы под названием " + ChatColor.WHITE + strings[0] + ChatColor.RED + " не существует.");
                    }
                }
            }
        }
        return false;
    }
}
