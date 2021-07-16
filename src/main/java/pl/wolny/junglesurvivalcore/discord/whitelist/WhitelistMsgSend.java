package pl.wolny.junglesurvivalcore.discord.whitelist;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.common.pluginobjects.WaitingPlayer;
import pl.wolny.junglesurvivalcore.common.utils.GeneralUtils;

import java.awt.*;
import java.util.Objects;

public class WhitelistMsgSend extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!GeneralUtils.isNumeric(event.getMessage().getContentRaw())){
            return;
        }
        if(!event.getChannel().getId().equals("578556243229016067")){
            return;
        }
        if(!JungleSurvivalCore.core.getCache().getWhitelistNumbers().containsKey(event.getMessage().getContentRaw())){
            EmbedBuilder EmbedBuilder = new EmbedBuilder();
            EmbedBuilder.setColor(Color.red);
            EmbedBuilder.setTitle("**Niepoprawny kod weryfikacji**");
            event.getAuthor().openPrivateChannel().queue((channel) ->
            {
                channel.sendMessageEmbeds(EmbedBuilder.build()).queue();
            });
            event.getMessage().delete().queue();
            return;
        }
        EmbedBuilder EmbedBuilder = new EmbedBuilder();
        EmbedBuilder.setColor(Color.GREEN);
        EmbedBuilder.setTitle("**Poprawny kod weryfikacji**");
        EmbedBuilder EmbReg = new EmbedBuilder();
        EmbReg.setColor(Color.GREEN);
        EmbReg.setTitle("**Zgłoszono do administracji**");
        event.getAuthor().openPrivateChannel().queue((channel) ->
        {
            channel.sendMessageEmbeds(EmbedBuilder.build()).queue();
            channel.sendMessageEmbeds(EmbReg.build()).queue();
        });
        event.getGuild().addRoleToMember(event.getAuthor().getId(), Objects.requireNonNull(event.getGuild().getRoleById("865285757899374613"))).queue();
        event.getMessage().delete().queue();
        WaitingPlayer waitingPlayer = JungleSurvivalCore.core.getCache().getWhitelistNumbers().get(event.getMessage().getContentRaw());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Podanie na listę graczy");
        eb.setColor(new Color(24, 163, 162));
        eb.addField("Nick w mc", waitingPlayer.name, false);
        eb.addField("UUID w mc", waitingPlayer.id.toString(), false);
        eb.addField("Nick na dc", event.getAuthor().getName(), false);
        eb.addField("ID zgłaszającego", event.getAuthor().getId(), false);
        Objects.requireNonNull(event.getGuild().getTextChannelById("578556682188095528")).sendMessageEmbeds(eb.build()).queue(msg -> {
            msg.addReaction("\u2705").queue();
            msg.addReaction("\u274C").queue();
        });
    }
}
