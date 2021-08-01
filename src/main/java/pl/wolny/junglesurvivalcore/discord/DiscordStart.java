package pl.wolny.junglesurvivalcore.discord;

import jdk.jfr.internal.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.Bukkit;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.discord.warn.DiscordWarnCmd;
import pl.wolny.junglesurvivalcore.discord.warn.DiscordWarnsCmd;
import pl.wolny.junglesurvivalcore.discord.whitelist.WhitelistMsgSend;
import pl.wolny.junglesurvivalcore.discord.whitelist.WhitelistReactEvent;

import javax.security.auth.login.LoginException;
import java.util.logging.Level;

public class DiscordStart {
    public static JDA startBot(String token){
        try {
            JDA jda = JDABuilder.createDefault(token)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES)
                    .enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS)
                    .enableIntents(GatewayIntent.GUILD_PRESENCES)
                    .build();
            jda.addEventListener(new WhitelistMsgSend());
            jda.addEventListener(new WhitelistReactEvent());
            jda.addEventListener(new DiscordWarnCmd());
            jda.addEventListener(new DiscordWarnsCmd());
            return jda;
        } catch (LoginException e) {
            JungleSurvivalCore.core.getLogger().log(Level.SEVERE, "Starting discord bot was not possible. Check your token.");
            JungleSurvivalCore.core.getLogger().log(Level.SEVERE, "Your token: " + JungleSurvivalCore.core.getPluginConfig().DiscordToken);
            Bukkit.getServer().shutdown();
        }
        return null;
    }
}
