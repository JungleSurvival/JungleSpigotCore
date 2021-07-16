package pl.wolny.junglesurvivalcore.minecraft.whitelist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.common.utils.VerificationUtils;

public class WhitelistJoinEvent implements Listener {
    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event){
        if(!VerificationUtils.canPlayerJoin(event.getUniqueId())){
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, VerificationUtils.kickNotAtList(event.getUniqueId(), event.getName()));
        }
    }
}
