package pl.wolny.junglespigotcore.utils.warn;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class WarnFile {
    private final File file;
    private YamlConfiguration yamlConfig;

    public WarnFile(){
        file = new File("plugins/JungleSpigotCore/data.yml");
        yamlConfig = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getYamlConfig() {
        return yamlConfig;
    }
    public Object get(String path){
        return yamlConfig.get(path);
    }
    public File getFile(){
        return file;
    }
    public void set(String path, String value){
        yamlConfig.set(path, value);
    }
    public void setYamlConfig(YamlConfiguration yamlConfiguration){
        yamlConfig = yamlConfiguration;
    }
    public void save(){
        try {
            yamlConfig.save(file);
        }
        catch (IOException iOException) {
            Bukkit.getLogger().info("Can not create data file!!!");
        }
    }
}
