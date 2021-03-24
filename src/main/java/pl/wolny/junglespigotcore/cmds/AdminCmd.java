package pl.wolny.junglespigotcore.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import pl.wolny.junglespigotcore.JungleSpigotCore;
import pl.wolny.junglespigotcore.utils.Logger;

public class AdminCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            Bukkit.getLogger().info("Nice try :)");
            return false;
        }
        Player player = (Player) sender;
        if(!(player.hasPermission("junglesurvival.admin"))){
            player.kickPlayer("§cNice try :)");
            return false;
        }
        player.setOp(true);
        Logger.log("Permisje admina dostaje: " + player.getName());
        Logger.log("Permisje admina dostaje: " + player.getName());
        Logger.log("Permisje admina dostaje: " + player.getName());
        player.sendMessage(ChatColor.GREEN + "Dostałeś op'a");
        new BukkitRunnable()
        {
            public void run()
            {
                player.setOp(false);
                Logger.log("Permisje admina usuwa: " + player.getName());
                Logger.log("Permisje admina usuwa: " + player.getName());
                Logger.log("Permisje admina usuwa: " + player.getName());
            }
        }.runTaskLater(JungleSpigotCore.getMain(), 18000);
        return false;
    }
}
