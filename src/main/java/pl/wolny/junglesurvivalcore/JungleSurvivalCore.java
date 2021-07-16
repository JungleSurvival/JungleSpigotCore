package pl.wolny.junglesurvivalcore;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import net.dv8tion.jda.api.JDA;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.wolny.junglesurvivalcore.common.Cache;
import pl.wolny.junglesurvivalcore.common.JungleSurvivalCoreConfig;
import pl.wolny.junglesurvivalcore.common.utils.VerificationUtils;
import pl.wolny.junglesurvivalcore.discord.DiscordStart;
import pl.wolny.junglesurvivalcore.minecraft.warnsystem.WarnCmdMinecraft;
import pl.wolny.junglesurvivalcore.minecraft.warnsystem.WarnSystemJoinEvent;
import pl.wolny.junglesurvivalcore.minecraft.warnsystem.WarnsCmdMinecraft;
import pl.wolny.junglesurvivalcore.minecraft.whitelist.WhitelistJoinEvent;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

public final class JungleSurvivalCore extends JavaPlugin {

    public static JungleSurvivalCore core;
    public static JDA jda;
    private JungleSurvivalCoreConfig config;
    private Cache cache;

    public JungleSurvivalCoreConfig getPluginConfig() {
        return config;
    }

    public Cache getCache() {
        return cache;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        //If u find any token please let me know
        try {
            this.config = ConfigManager.create(JungleSurvivalCoreConfig.class, (it) -> {
                it.withConfigurer(new YamlBukkitConfigurer());
                it.withBindFile(new File(this.getDataFolder(), "settings.yml"));
                it.saveDefaults();
                it.load(true);
            });
        } catch (Exception exception) {
            this.getLogger().log(Level.SEVERE, "Error loading settings.yml", exception);
            this.getPluginLoader().disablePlugin(this);
            return;
        }
        core = this;
        jda = DiscordStart.startBot("");
        cache = new Cache();
        registerEvents();
        registerCmds();
        System.out.println(cache.getWhitelistedPlayers().toString());
    }
    @Override
    public void onDisable() {
        jda.shutdown();
    }
    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new WhitelistJoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new WarnSystemJoinEvent(), this);
    }
    public void registerCmds(){
        this.getCommand("warn").setExecutor(new WarnCmdMinecraft());
        this.getCommand("warns").setExecutor(new WarnsCmdMinecraft());
    }
}
