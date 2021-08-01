package pl.wolny.junglesurvivalcore.minecraft.bedrock;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;

import java.util.List;

public class DamageEvent implements @NotNull Listener {
    @EventHandler()
    public void onPlayerDamageEvent(EntityDamageByEntityEvent event) {
        if(!JungleSurvivalCore.core.getCache().getNonPvpBedrock().contains(event.getEntity().getUniqueId())){
            return;
        }
        if(event.getDamager() instanceof Player){
            event.getDamager().sendMessage(ChatColor.RED + "Nie możesz udeżyć tego gracza :<");
            event.setCancelled(true);
            return;
        }
        if(event.getDamager() instanceof Projectile){
            Projectile projectile = (Projectile) event.getDamager();
            if(projectile.getShooter() instanceof Player){
                ((Player) projectile.getShooter()).sendMessage(ChatColor.RED + "Nie możesz udeżyć tego gracza :<");
                projectile.setBounce(true);
                event.setCancelled(true);
                return;
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(PlayerInteractEvent event) {
        if(event.getItem() == null){
            return;
        }
        ItemStack itemStack = event.getItem();
        if(!(itemStack.getType() == Material.LAVA_BUCKET || itemStack.getType() == Material.FLINT_AND_STEEL || itemStack.getType() == Material.FIRE_CHARGE)){
            return;
        }
        List<Entity> nearbyEntites = (List<Entity>) event.getPlayer().getLocation().getNearbyEntities( 3, 3, 3);
        nearbyEntites.removeIf(entity -> !(entity instanceof Player) && !JungleSurvivalCore.core.getCache().getNonPvpBedrock().contains(entity.getUniqueId()));
        nearbyEntites.remove(event.getPlayer());
        if(nearbyEntites.size() > 0){
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Robienie tego nie jest miłe!");
        }
    }
}
