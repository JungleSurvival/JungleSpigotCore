package pl.wolny.junglesurvivalcore.minecraft.bedrock;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.common.utils.GeneralUtils;

public class GammaEvent implements Listener {
    @EventHandler
    public void LeftEvent(PlayerQuitEvent event){
        if(JungleSurvivalCore.core.getCache().getBedrockGamma().contains(event.getPlayer().getUniqueId())){
            event.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
            JungleSurvivalCore.core.getCache().getBedrockGamma().remove(event.getPlayer().getUniqueId());
        }
    }
}
