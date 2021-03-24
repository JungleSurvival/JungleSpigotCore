package pl.wolny.junglespigotcore.utils.warn;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class WarnManager {
    public static void addWarn(Player player, String reason){
        WarnFile warnFile = new WarnFile();
        YamlConfiguration warnYml = warnFile.getYamlConfig();
        List<String> PlayerWarns = warnYml.getStringList(player.getName() + "-warns");
        WarnObject warn = new WarnObject(player.getName(), UUID.randomUUID().toString(), reason, new Date(System.currentTimeMillis()));
        PlayerWarns.add(warn.getId());
        warnYml.set(player.getName() + "-warns", PlayerWarns);
        warnYml.set(warn.getId() + ".date", warn.getDate().getTime());
        warnYml.set(warn.getId() + ".reason", warn.getReason());
        warnFile.setYamlConfig(warnYml);
        warnFile.save();
    }
    public static List<WarnObject> getWarns(String PlayerName){
        WarnFile warnFile = new WarnFile();
        YamlConfiguration warnYml = warnFile.getYamlConfig();
        List<String> PlayerWarns = warnYml.getStringList(PlayerName + "-warns");
        if(PlayerWarns.size() == 0){return new ArrayList<WarnObject>();}
        List WarnsToReturn = new ArrayList<WarnObject>();
        for (String WarnID: PlayerWarns) {
            WarnsToReturn.add(new WarnObject(PlayerName, WarnID, warnYml.getString(WarnID + ".reason"), new Date(warnYml.getLong(WarnID + ".date"))));
        }
        return WarnsToReturn;
    }
    public static void autoWarn(int warns, Player player){
        switch (warns){
            case 2:
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tempban " + player.getName() + " 1h Dostałeś swoje 2 upomnienie!");
                break;
            case 3:
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tempban " + player.getName() + " 24h Dostałeś swoje 3 upomnienie!");
                break;
            case 4:
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tempban " + player.getName() + " 3d Dostałeś swoje 4 upomnienie!");
                break;
            case 5:
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ban " + player.getName() + " Dostałeś swoje 5 upomnienie!");
                break;
        }
    }
}
