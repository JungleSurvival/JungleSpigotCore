package pl.wolny.junglesurvivalcore.discord.whitelist;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.common.utils.VerificationUtils;

import java.awt.*;
import java.util.Objects;

public class WhitelistReactEvent extends ListenerAdapter {
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
            if (Objects.requireNonNull(event.getMember()).getId().equals(event.getJDA().getSelfUser().getId())) {
                return;
            }
            if(!(message.getEmbeds().size() > 0)){
                return;
            }
            if(!event.getChannel().getId().equals("578556682188095528")){
                return;
            }
            if(!Objects.equals(message.getEmbeds().get(0).getTitle(), "Podanie na listę graczy")){
                return;
            }
            if (!event.getReactionEmote().getName().equals("✅")) {
                event.getGuild().getMemberById(Objects.requireNonNull(message.getEmbeds().get(0).getFields().get(2).getValue())).getUser().openPrivateChannel().queue((channel) ->
                {
                    EmbedBuilder priv = new EmbedBuilder();
                    priv.setColor(new Color(43, 209, 21));
                    priv.setTitle("**Twoje podanie zostało odrzucone!**");
                    channel.sendMessageEmbeds(priv.build()).queue();
                });
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Odrzucone podanie na listę");
                eb.setColor(new Color(24, 163, 162));
                eb.addField("Nick w mc", message.getEmbeds().get(0).getFields().get(0).getValue(), false);
                eb.addField("Nick na dc", message.getEmbeds().get(0).getFields().get(1).getValue(), false);
                eb.addField("id zgłaszającego", message.getEmbeds().get(0).getFields().get(2).getValue(), false);
                eb.addField("id weryfikacji", message.getEmbeds().get(0).getFields().get(3).getValue(), false);
                eb.addField("Odrzucone przez", event.getMember().getUser().getName(), false);
                event.getChannel().editMessageEmbedsById(event.getMessageId(), eb.build()).queue();
                message.clearReactions().queue();
                return;
            }
            String mcname = message.getEmbeds().get(0).getFields().get(0).getValue();
            String mcuuid = message.getEmbeds().get(0).getFields().get(1).getValue();
            String dcuuid = message.getEmbeds().get(0).getFields().get(3).getValue();
            String dcname = message.getEmbeds().get(0).getFields().get(2).getValue();
            VerificationUtils.addToWhitelist(mcname, mcuuid, dcuuid, dcname);
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Zaakcepowane podanie na listę");
            eb.setColor(new Color(24, 163, 162));
            eb.addField("Nick w mc",mcname, false);
            eb.addField("UUID w mc", mcuuid, false);
            eb.addField("Nick na dc", dcname, false);
            eb.addField("ID zgłaszającego", dcuuid, false);
            eb.addField("Zaakceptowane przez", event.getMember().getUser().getName(), false);
            event.getChannel().editMessageEmbedsById(event.getMessageId(), eb.build()).queue();
            message.clearReactions().queue();
            Role wait = event.getGuild().getRoleById("865285757899374613");
            Role main = event.getGuild().getRoleById("865315500367282196");
            assert main != null;
            assert dcuuid != null;
            assert wait != null;
            event.getGuild().addRoleToMember(dcuuid, main).queue();
            event.getGuild().removeRoleFromMember(dcuuid, wait).queue();
            Objects.requireNonNull(event.getGuild().getMemberById(dcuuid)).getUser().openPrivateChannel().queue((channel) ->
            {
                EmbedBuilder priv = new EmbedBuilder();
                priv.setColor(new Color(43, 209, 21));
                priv.setTitle("**Zostałeś zaakceptowany na listę graczy JungleSurvival!**");
                channel.sendMessageEmbeds(priv.build()).queue();
            });
        });
    }
}
