package pl.wolny.junglespigotcore.adminlog;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.wolny.junglespigotcore.utils.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TeleportEvent implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void TeleportEvent(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Location loc = event.getTo();
        if(player.hasPermission("JungleSurvival.admin")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("§4[§aADMINLOG§4] §6Administrator §c").append(player.getName()).append(" §6przeteleportował się. Kordy: X:§6").append(loc.getX()).append(" §cY: §6").append(loc.getY()).append("§c Z: §6").append(loc.getZ());
            List<Entity> nearbyEntities = (List<Entity>) player.getWorld().getNearbyEntities(loc, 10, 10, 10);
            nearbyEntities.removeIf(entity -> !(entity instanceof Player));
            nearbyEntities.remove(player);
            if(nearbyEntities.size() > 0){
                stringBuilder.append(" §cJest to blisko: ");
                for (Entity ent: nearbyEntities) {
                        stringBuilder.append("§6" + ent.getName() + " ");
                }
                stringBuilder.append("§c.");
            }
            Bukkit.getServer().broadcast(stringBuilder.toString(), "junglesurvival.adminlog.tp");
            Logger.log(stringBuilder.toString());
            return;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("§4[§aADMINLOG§4] §cGracz §c").append(player.getName()).append(" §6przeteleportował się. Kordy: X:§6").append(Math.round(loc.getX())).append(" §cY: §6").append(Math.round(loc.getY())).append("§c Z: §6").append(Math.round(loc.getZ()));
            List<Entity> nearbyEntities = (List<Entity>) player.getWorld().getNearbyEntities(loc, 10, 10, 10);
            nearbyEntities.removeIf(entity -> !(entity instanceof Player));
            nearbyEntities.remove(player);
            if(nearbyEntities.size() > 0){
                stringBuilder.append(" §cJest to blisko: ");
                for (Entity ent: nearbyEntities) {
                    stringBuilder.append("§6" + ent.getName() + "§c, ");
                }
                stringBuilder.append("§c.");
            }
            Bukkit.getServer().broadcast(stringBuilder.toString(), "junglesurvival.adminlog.tp");
            Logger.log(stringBuilder.toString());
            return;
        }
    }
}
