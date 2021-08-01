package pl.wolny.junglesurvivalcore.minecraft.keepinventory;

import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PlayerDeathEvent implements Listener {
    private static Random random = new Random();
    @EventHandler(ignoreCancelled = true)
    public void deathEvent(org.bukkit.event.entity.PlayerDeathEvent event){
        Player player = event.getEntity();
        if(JungleSurvivalCore.core.getCache().getNonPvpBedrock().contains(player.getUniqueId())){
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            event.getDrops().clear();
            event.setShouldDropExperience(false);
            event.getEntity().sendMessage(ChatColor.RED + "Umarłeś! Jako że jesteś graczem bedrock nie tracisz przedmiotów :)");
            return;
        }
        int allItems = event.getDrops().size();
        for (Iterator<ItemStack> iterator = event.getDrops().iterator(); iterator.hasNext(); ) {
            ItemStack drop = iterator.next();
            if(shoudlkeep()){
                iterator.remove();
                event.getItemsToKeep().add(drop);
            }
        }
        int xp = event.getDroppedExp();
        event.setDroppedExp(xp/2);
        event.setNewExp(xp/2);
        event.getEntity().sendMessage(ChatColor.RED + "Umarłeś! Straciłeś " + event.getDrops().size() + "/" + allItems + " przedmiotów.");
    }
    public boolean shoudlkeep(){
        return random.nextBoolean();
    }
}
