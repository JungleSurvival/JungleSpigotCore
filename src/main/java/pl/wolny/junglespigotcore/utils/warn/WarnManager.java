package pl.wolny.junglespigotcore.utils.warn;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WarnManager {
    public void addWarn(Player player, String reason){
        WarnFile warnFile = new WarnFile();
        YamlConfiguration warnYml = warnFile.getYamlConfig();
        List<String> PlayerWarns = warnYml.getStringList(player.getName() + "-warns");
    }
}
