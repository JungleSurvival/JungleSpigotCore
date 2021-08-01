package pl.wolny.junglesurvivalcore.minecraft.whitelist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.common.pluginobjects.PlayerServerAccessEnum;
import pl.wolny.junglesurvivalcore.common.utils.VerificationUtils;

public class WhitelistJoinEvent implements Listener {
    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event){
        switch (VerificationUtils.canPlayerJoin(event.getUniqueId())){
            case NOT_WHITELISTED:
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, VerificationUtils.kickNotAtList(event.getUniqueId(), event.getName()));
                break;
            case MYSQL_ERROR:
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, VerificationUtils.kickDatabaseError());
                break;
        }
    }
}
