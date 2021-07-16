package pl.wolny.junglesurvivalcore.minecraft.warnsystem;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.wolny.junglesurvivalcore.common.utils.GeneralUtils;
import pl.wolny.junglesurvivalcore.common.utils.WarnUtils;

public class WarnsCmdMinecraft implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof ConsoleCommandSender && args.length == 0){
            sender.sendMessage(GeneralUtils.fixColor("&cZłe użcyie komendy!"));
            return false;
        }
        if(sender instanceof Player && args.length == 0){
            WarnUtils.genMcWarnMsg(((Player) sender).getUniqueId(), sender, sender.getName());
            return true;
        }
        if(Bukkit.getPlayer(args[0]) == null){
            sender.sendMessage(GeneralUtils.fixColor("&cPodany gracz jest oflnie!"));
            return false;
        }
        WarnUtils.genMcWarnMsg(Bukkit.getPlayer(args[0]).getUniqueId(), sender, args[0]);
        return true;
    }
}
