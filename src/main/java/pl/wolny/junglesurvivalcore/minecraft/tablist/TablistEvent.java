package pl.wolny.junglesurvivalcore.minecraft.tablist;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.wolny.junglesurvivalcore.common.utils.GeneralUtils;

public class TablistEvent implements Listener {
    @EventHandler
    public void JoinEventTablist(PlayerJoinEvent event){
        Bukkit.getServer().sendPlayerListHeaderAndFooter(GeneralUtils.fixColorComponent("&2Jungle&aSurvival"), GeneralUtils.fixColorComponent("&aGracze online: &2" + Bukkit.getServer().getOnlinePlayers().size()));
    }
    @EventHandler
    public void LeftEvent(PlayerQuitEvent event){
        Bukkit.getServer().sendPlayerListHeaderAndFooter(GeneralUtils.fixColorComponent("&2Jungle&aSurvival"), GeneralUtils.fixColorComponent("&aGracze online: &2" + Bukkit.getServer().getOnlinePlayers().size()));
    }
}
