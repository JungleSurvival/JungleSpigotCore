package pl.wolny.junglesurvivalcore.minecraft.seciurity;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.common.utils.GeneralUtils;
import pl.wolny.junglesurvivalcore.common.utils.SeciurityUtils;

public class GoogleAuthAddAdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof ConsoleCommandSender)){
            sender.sendMessage(GeneralUtils.fixColor("&cTę komendę możesz wykonać tylko z poziomu CLI!"));
            return false;
        }
        if(args.length < 1){
            sender.sendMessage(GeneralUtils.fixColor("&cNie poprawnie użycie komendy!"));
            return false;
        }
        Player admin = Bukkit.getPlayer(args[0]);
        if(admin == null){
            sender.sendMessage(GeneralUtils.fixColor("&cNie możesz mianować admina który jest offline!"));
            return false;
        }
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        SeciurityUtils.setSecret(admin.getUniqueId(), key.getKey());
        sender.sendMessage("Kod dla admina: " + GoogleAuthenticatorQRGenerator.getOtpAuthURL("JungleSurvival", admin.getName(), key));
        admin.kick(Component.text("Zostałeś wyrzucony z serwera!"));
        JungleSurvivalCore.core.getCache().getAdminList().add(admin.getUniqueId());
        return true;
    }
}
