package pl.wolny.junglespigotcore.adminlog;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.jetbrains.annotations.NotNull;
import pl.wolny.junglespigotcore.utils.Logger;

public class GamemodeChange implements @NotNull Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onGamemodeChange(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();
        if(e.getNewGameMode().equals(GameMode.SURVIVAL)){
            if(p.hasPermission("JungleSurvival.admin")) {
                Logger.log("§4[§aADMINLOG§4] §6Administrator §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cPrzetrwanie");
                Bukkit.getServer().broadcast("§4[§aADMINLOG§4] §6Administrator §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cPrzetrwanie", "junglesurvival.adminlog.gm");
                return;
            } else {
                Logger.log("§4[§aADMINLOG§4] §cGracz §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cPrzetrwanie");
                Bukkit.getServer().broadcast("§4[§aADMINLOG§4] §cGracz §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cPrzetrwanie", "junglesurvival.adminlog.gm");
            }

        }
        if(e.getNewGameMode().equals(GameMode.ADVENTURE)){
            if(p.hasPermission("JungleSurvival.admin")) {
                Logger.log("§4[§aADMINLOG§4] §6Administrator §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cPrzygodowy");
                Bukkit.getServer().broadcast("§4[§aADMINLOG§4] §6Administrator §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cPrzygodowy", "junglesurvival.adminlog.gm");
                return;
            } else {
                Logger.log("§4[§aADMINLOG§4] §6Gracz §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cPrzygodowy");
                Bukkit.getServer().broadcast("§4[§aADMINLOG§4] §cGracz §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cPrzygodowy", "junglesurvival.adminlog.gm");
            }
        }
        if(e.getNewGameMode().equals(GameMode.CREATIVE)){
            if(p.hasPermission("JungleSurvival.admin")) {
                Logger.log("§4[§aADMINLOG§4] §6Administrator §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cKreatywny");
                Bukkit.getServer().broadcast("§4[§aADMINLOG§4] §6Administrator §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cKreatywny", "junglesurvival.adminlog.gm");
                return;
            } else {
                Logger.log("§4[§aADMINLOG§4] §6Gracz §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cKreatywny");
                Bukkit.getServer().broadcast("§4[§aADMINLOG§4] §cGracz §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cKreatywny", "junglesurvival.adminlog.gm");
            }
        }
        if(e.getNewGameMode().equals(GameMode.SPECTATOR)){
            if(p.hasPermission("JungleSurvival.admin")) {
                Logger.log("§4[§aADMINLOG§4] §6Administrator §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cObserwator");
                Bukkit.getServer().broadcast("§4[§aADMINLOG§4] §6Administrator §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cObserwator", "junglesurvival.adminlog.gm");
                return;
            } else {
                Logger.log("§4[§aADMINLOG§4] §6Gracz §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cObserator");
                Bukkit.getServer().broadcast("§4[§aADMINLOG§4] §cGracz §c" + e.getPlayer().getName() + " §6zmienił swój tryb gry na §cObserwator", "junglesurvival.adminlog.gm");
            }
        }

    }
}
