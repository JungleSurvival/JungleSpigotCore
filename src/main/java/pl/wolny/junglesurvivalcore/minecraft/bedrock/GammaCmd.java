package pl.wolny.junglesurvivalcore.minecraft.bedrock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.geysermc.floodgate.api.FloodgateApi;
import org.jetbrains.annotations.NotNull;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.common.utils.GeneralUtils;

public class GammaCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(GeneralUtils.fixColor("&cTa komenda może być wykonana tylko z poziomu gracza!"));
            return false;
        }
        Player player = (Player) sender;
        if(!FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())){
            sender.sendMessage(GeneralUtils.fixColor("&cTa komenda może być wykonana tylko z poziomu gracza bedrock!"));
            return false;
        }
        if(JungleSurvivalCore.core.getCache().getBedrockGamma().contains(player.getUniqueId())){
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            JungleSurvivalCore.core.getCache().getBedrockGamma().remove(player.getUniqueId());
            sender.sendMessage(GeneralUtils.fixColor("&aPomyślnie usunięto effekt Night Vision."));
            return true;
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
        JungleSurvivalCore.core.getCache().getBedrockGamma().add(player.getUniqueId());
        sender.sendMessage(GeneralUtils.fixColor("&aPomyślnie dodano effekt Night Vision."));
        return true;
    }
}
