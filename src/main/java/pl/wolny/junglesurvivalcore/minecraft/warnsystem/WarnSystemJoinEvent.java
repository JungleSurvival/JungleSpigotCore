package pl.wolny.junglesurvivalcore.minecraft.warnsystem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.common.pluginobjects.WarnList;
import pl.wolny.junglesurvivalcore.common.utils.WarnUtils;

public class WarnSystemJoinEvent implements Listener {
    @EventHandler
    public void JoinEvent(PlayerJoinEvent event){
        if(!JungleSurvivalCore.core.getCache().getWarnListFromUUID().containsKey(event.getPlayer().getUniqueId())){
            WarnUtils.loadWarns(event.getPlayer().getUniqueId(), event.getPlayer().getName());
        }
    }
}
