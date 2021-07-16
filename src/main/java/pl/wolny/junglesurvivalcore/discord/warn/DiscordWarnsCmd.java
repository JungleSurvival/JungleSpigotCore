package pl.wolny.junglesurvivalcore.discord.warn;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.common.utils.GeneralUtils;
import pl.wolny.junglesurvivalcore.common.utils.WarnUtils;

import java.awt.*;

public class DiscordWarnsCmd extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!event.getMessage().getContentRaw().startsWith("?warns")){
            return;
        }
        String[] args = event.getMessage().getContentRaw().split(" ");
        if(args.length < 2){
            if(!JungleSurvivalCore.core.getCache().getDiscordIdToUUID().containsKey(event.getMember().getId())){
                if(!WarnUtils.loadWarnsFromDiscordID(event.getMember().getId())){
                    GeneralUtils.sendErrorMsg("Wystąpił błąd!", event.getChannel());
                    return;
                }
            }
            WarnUtils.genDcWarnMsg(JungleSurvivalCore.core.getCache().getDiscordIdToUUID().get(event.getMember().getId()), event.getMember().getUser().getName() ,event.getChannel());
            return;
        }
        if(GeneralUtils.isNumeric(args[1])){
            if(event.getGuild().getMemberById(args[1]) == null){
                GeneralUtils.sendErrorMsg("Taki gracz nie jest na discordzie!", event.getChannel());
                return;
            }
            if(!JungleSurvivalCore.core.getCache().getDiscordIdToUUID().containsKey(args[1])){
                if(!WarnUtils.loadWarnsFromDiscordID(args[1])){
                    GeneralUtils.sendErrorMsg("Wystąpił błąd!", event.getChannel());
                    return;
                }
            }
            WarnUtils.genDcWarnMsg(JungleSurvivalCore.core.getCache().getDiscordIdToUUID().get(args[1]), event.getGuild().getMemberById(args[1]).getUser().getName() ,event.getChannel());
            return;
        }
        if(event.getMessage().getMentionedMembers().size() > 0){
            String userid = event.getMessage().getMentionedMembers().get(0).getId();
            if(!JungleSurvivalCore.core.getCache().getDiscordIdToUUID().containsKey(userid)){
                if(!WarnUtils.loadWarnsFromDiscordID(userid)){
                    GeneralUtils.sendErrorMsg("Wystąpił błąd!", event.getChannel());
                    return;
                }
            }
            WarnUtils.genDcWarnMsg(JungleSurvivalCore.core.getCache().getDiscordIdToUUID().get(userid), event.getMessage().getMentionedMembers().get(0).getUser().getName() ,event.getChannel());
        }
    }
}
