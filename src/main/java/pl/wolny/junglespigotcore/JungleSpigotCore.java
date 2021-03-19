package pl.wolny.junglespigotcore;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import pl.wolny.junglespigotcore.utils.config.ConfigFile;
import pl.wolny.junglespigotcore.utils.warn.WarnFile;
import sun.rmi.runtime.Log;

import java.util.logging.Logger;
import pl.wolny.junglespigotcore.adminlog.*;

public final class JungleSpigotCore extends JavaPlugin {

    public ConfigFile configFile;
    @Override
    public void onEnable() {
        // Plugin startup logic
        genConfig();
        if(configFile.getYamlConfig().getBoolean("AdminLogModule")){
            Bukkit.getPluginManager().registerEvents(new TeleportEvent(), this);
            Bukkit.getPluginManager().registerEvents(new GamemodeChange(), this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void genConfig(){
        Logger logger = Bukkit.getLogger();
        logger.info(">>> " + this.getDescription().getName() + " <<<");
        logger.info("Initializing the configuration file");
        logger.info("Performing tests");
        ConfigFile config = new ConfigFile(this);
        WarnFile warnFile = new WarnFile();
        configFile = config;
        if(!(config.getFile().exists())) {
            YamlConfiguration yamlConfiguration = config.getYamlConfig();
            yamlConfiguration.set("AdminLogModule", true);
            config.setYamlConfig(yamlConfiguration);
            config.save();
            logger.info("Test 1 failed!");
            return;
        }
        if(!(config.check(new String[]{"AdminLogModule"}))){
            config.getFile().delete();
            YamlConfiguration yamlConfiguration = config.getYamlConfig();
            yamlConfiguration.set("AdminLogModule", true);
            config.setYamlConfig(yamlConfiguration);
            config.save();
            logger.info("Test 2 failed!");
            return;
        }
        if(!(warnFile.getFile().exists())){
            warnFile.save();
            logger.info("Test 3 failed!");
        }
        logger.info("Each test was successful!");
        logger.info(">>> " + this.getDescription().getName() + " <<<");
    }
}
