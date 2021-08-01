package pl.wolny.junglesurvivalcore.minecraft.bedrock;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.geysermc.cumulus.ModalForm;
import org.geysermc.cumulus.response.ModalFormResponse;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;

public class BedrockJoinEvent implements Listener {
    @EventHandler
    public void JoinEvent(PlayerJoinEvent event){
        if(!FloodgateApi.getInstance().isFloodgatePlayer(event.getPlayer().getUniqueId())){
            return;
        }
        ModalForm.Builder form  = ModalForm.builder()
                .title("Wsparcie graczy bedrock!")
                .content("Jesteś graczem bedrock!" + "\n" + "Oznacza to że możesz dostać takie dodatki jak keepinventory oraz wyłączone pvp."
                        + "\n" + "Czy chcesz otrzymać te dodatki?")
                .button1("Tak") // id = 0
                .button2("Nie") // id = 1
                .responseHandler((form1, responseData) -> {
                    ModalFormResponse response = form1.parseResponse(responseData);
                    if (!response.isCorrect()) {
                        event.getPlayer().sendMessage(ChatColor.GREEN + "Szanujemy twoją decyzję <3 Miłej gry!");
                        return;
                    }

                    // short version of getClickedButtonId == 0
                    if (response.getResult()) {
                        JungleSurvivalCore.core.getCache().getNonPvpBedrock().add(event.getPlayer().getUniqueId());
                        event.getPlayer().sendMessage(ChatColor.GREEN + "Pvp zostało wyłączone dla ciebie oraz zasada zachowania przedmiotów po śmierci włączona. Miłej gry!");
                        event.getPlayer().playerListName(Component.text("*" + event.getPlayer().getName()));
                        return;
                    }
                    event.getPlayer().sendMessage(ChatColor.GREEN + "Szanujemy twoją decyzję <3 Miłej gry!");
                });
        new BukkitRunnable() {
            @Override
            public void run() {
                FloodgateApi.getInstance().sendForm(event.getPlayer().getUniqueId(), form);
            }
        }.runTaskLater(JungleSurvivalCore.core, 30);

    }
    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent event){
        JungleSurvivalCore.core.getCache().getNonPvpBedrock().remove(event.getPlayer().getUniqueId());
    }
}
