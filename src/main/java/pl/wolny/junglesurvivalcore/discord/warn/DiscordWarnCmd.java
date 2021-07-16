package pl.wolny.junglesurvivalcore.discord.warn;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.common.utils.GeneralUtils;
import pl.wolny.junglesurvivalcore.common.utils.WarnUtils;

import java.awt.*;

public class DiscordWarnCmd extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!event.getMessage().getContentRaw().startsWith("?warn") || event.getMessage().getContentRaw().startsWith("?warns")){
            return;
        }
        String[] args = event.getMessage().getContentRaw().split(" ");
        if(args.length < 3){
            GeneralUtils.sendErrorMsg("Złe użycie komendy!", event.getChannel());
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String reason = sb.toString().trim();
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
            WarnUtils.warnPlayer(JungleSurvivalCore.core.getCache().getDiscordIdToUUID().get(args[1]), reason, event.getMember().getUser().getName(), false);
            EmbedBuilder success = new EmbedBuilder();
            success.setTitle("Pomyślnie dodano ostrzeżenie");
            success.setColor(Color.green);
            success.addField("Osoba ostrzeżona:", event.getGuild().getMemberById(args[1]).getUser().getName(), false);
            success.addField("Admin:", event.getAuthor().getName(), false);
            success.addField("Powód:", reason, false);
            event.getChannel().sendMessageEmbeds(success.build()).queue();
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
            WarnUtils.warnPlayer(JungleSurvivalCore.core.getCache().getDiscordIdToUUID().get(userid), reason, event.getMember().getUser().getName(), false);
            EmbedBuilder success = new EmbedBuilder();
            success.setTitle("Pomyślnie dodano ostrzeżenie");
            success.setColor(Color.green);
            success.addField("Osoba ostrzeżona:", event.getMessage().getMentionedMembers().get(0).getUser().getName(), false);
            success.addField("Admin:", event.getAuthor().getName(), false);
            success.addField("Powód:", reason, false);
            event.getChannel().sendMessageEmbeds(success.build()).queue();
        }
    }
}
