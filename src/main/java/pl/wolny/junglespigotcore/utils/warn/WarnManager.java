package pl.wolny.junglespigotcore.utils.warn;

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
            WarnsToReturn.add(new WarnObject(PlayerName, WarnID, warnYml.getString(PlayerName + ".reason"), new Date(warnYml.getLong(PlayerName + ".date"))));
        }
        return WarnsToReturn;
    }
}
