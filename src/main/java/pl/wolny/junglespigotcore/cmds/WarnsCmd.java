package pl.wolny.junglespigotcore.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.wolny.junglespigotcore.utils.warn.WarnManager;
import pl.wolny.junglespigotcore.utils.warn.WarnObject;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class WarnsCmd implements CommandExecutor {
    private void GenWarns(Player player, Player pl2){
        List<WarnObject> warns = WarnManager.getWarns(player.getName());
        Collections.reverse(warns);
        if(warns.size() == 0){
            pl2.sendMessage(ChatColor.translateAlternateColorCodes('+', "+aGracz +f" + player.getName() + " +anie ma warnów!"));
            return;
        }
        pl2.sendMessage(ChatColor.translateAlternateColorCodes('^', "^9>>> ^aOstrzezenia gracza " + player.getName() + "^a:"));
        int i = 1;
        for (WarnObject warn: warns) {
            pl2.sendMessage(ChatColor.translateAlternateColorCodes('^', "^9>>> ^aWarn o id " + i + "^a:"));
            pl2.sendMessage(ChatColor.translateAlternateColorCodes('^', "^aPowód:^f " + warn.getReason() + "^a."));
            pl2.sendMessage(ChatColor.translateAlternateColorCodes('^', "^aData:^f " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(warn.getDate()) + "^a."));
            i++;
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return false;
    }
}
