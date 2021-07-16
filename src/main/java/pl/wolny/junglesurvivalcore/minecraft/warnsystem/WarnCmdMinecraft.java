package pl.wolny.junglesurvivalcore.minecraft.warnsystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.wolny.junglesurvivalcore.common.utils.GeneralUtils;
import pl.wolny.junglesurvivalcore.common.utils.WarnUtils;

public class WarnCmdMinecraft implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("junglesrv.admin")){
            sender.sendMessage(GeneralUtils.fixColor("&cNie posiadasz uprawnień do ostrzegania graczy :c"));
            return false;
        }
        if(!(args.length > 1)){
            sender.sendMessage(GeneralUtils.fixColor("&cZłe użcyie komendy!"));
            return false;
        }
        if(Bukkit.getPlayer(args[0]) == null){
            sender.sendMessage(GeneralUtils.fixColor("&cPodany gracz jest oflnie!"));
            return false;
        }
        Player toWarn = Bukkit.getPlayer(args[0]);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String reason = sb.toString().trim();
        WarnUtils.warnPlayer(toWarn.getUniqueId(), reason, sender.getName(), true);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('^', "^aOstrzegasz gracza: ^f" + toWarn.getName() + " ^aZa:^f " + reason + "^a."));
        toWarn.sendMessage(ChatColor.translateAlternateColorCodes('^', "^aZostałeś ostrzeżony za: ^f" + reason + "^a."));
        return false;
    }
}
