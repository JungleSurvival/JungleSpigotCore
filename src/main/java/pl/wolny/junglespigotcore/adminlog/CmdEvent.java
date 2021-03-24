package pl.wolny.junglespigotcore.adminlog;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.wolny.junglespigotcore.utils.Logger;
import sun.rmi.runtime.Log;

public class CmdEvent implements Listener {
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){
        if(!event.getPlayer().isOp()){
            return;
        }
        Logger.log("Admin: " + event.getPlayer().getName() + " Używa komendy: " + event.getMessage());
    }
}
