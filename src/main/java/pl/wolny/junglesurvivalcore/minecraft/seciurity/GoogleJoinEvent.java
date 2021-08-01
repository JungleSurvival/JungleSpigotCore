package pl.wolny.junglesurvivalcore.minecraft.seciurity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.common.utils.GeneralUtils;

public class GoogleJoinEvent implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(JungleSurvivalCore.core.getCache().getAdminList().contains(event.getPlayer().getUniqueId())){
            event.getPlayer().setOp(false);
            event.getPlayer().sendMessage(GeneralUtils.fixColorComponent("&aProsze wpisać /2fa kod aby otrzymać permisje administratora!"));
        }
    }
}
