package pl.wolny.junglespigotcore.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.wolny.junglespigotcore.utils.warn.WarnManager;

public class WarnCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            Bukkit.getLogger().info("Nice try :)");
            return false;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("junglesurvival.warn")){
            player.sendMessage(ChatColor.translateAlternateColorCodes('^', "^cNie możesz tego zrobić!"));
            return false;
        }
        if(args.length < 1){
            player.sendMessage(ChatColor.translateAlternateColorCodes('^', "^cZłe użycie!"));
            return false;
        }
        Player toWarn = Bukkit.getPlayer(args[0]);
        if(toWarn == null){
            player.sendMessage(ChatColor.translateAlternateColorCodes('^', "^cTen gracz jest offline!"));
            return false;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String reason = sb.toString().trim();
        WarnManager.addWarn(toWarn, reason);
        return false;
    }
}
