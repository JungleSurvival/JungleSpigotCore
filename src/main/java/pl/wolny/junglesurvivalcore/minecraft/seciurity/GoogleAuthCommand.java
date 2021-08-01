package pl.wolny.junglesurvivalcore.minecraft.seciurity;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.common.utils.GeneralUtils;
import pl.wolny.junglesurvivalcore.common.utils.SeciurityUtils;

import java.util.Calendar;
import java.util.Date;

public class GoogleAuthCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(GeneralUtils.fixColor("&cNie możesz wykonać tej komendy z poziomu CLI!"));
            return false;
        }
        if(!JungleSurvivalCore.core.getCache().getAdminList().contains(((Player) sender).getUniqueId())){
            sender.sendMessage(GeneralUtils.fixColor("&cNie posiadasz odpowiednich permisji aby wykonać tę komendę!"));
            return false;
        }
        if(args.length < 1){
            sender.sendMessage(GeneralUtils.fixColor("&cNie poprawne użycie komendy!"));
            return false;
        }
        try{
            int code = Integer.parseInt(args[0]);
            System.out.println(SeciurityUtils.playerInputCode(((Player) sender).getUniqueId(), code));
            System.out.println(SeciurityUtils.getSecret(((Player) sender).getUniqueId()));
            if(SeciurityUtils.playerInputCode(((Player) sender).getUniqueId(), code)){
                sender.setOp(true);
                sender.sendMessage(GeneralUtils.fixColor("&aPomyślnie nadano przywileje administratora!"));
                return true;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            Calendar.getInstance().add(Calendar.MINUTE, 5);
            ((Player) sender).banPlayer("INVALID 2FA CODE. PLEASE JOIN THE SERVER IN 5 MINUTES!", calendar.getTime());
            return false;
        }catch (NumberFormatException e){
            sender.sendMessage(GeneralUtils.fixColor("&cNie poprawne użycie komendy!"));
            return false;
        }
    }
}
