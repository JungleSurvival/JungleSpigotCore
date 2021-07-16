package pl.wolny.junglesurvivalcore.common.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.TextChannel;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GeneralUtils {
    public static String fixColor(String text){
        return ChatColor.translateAlternateColorCodes('&', text.replace(" | ", System.lineSeparator()));
    }
    public static Component fixColorComponent(String text){
        String[] StringList = text.split("\\|");
        List<Component> componentList = new ArrayList<>();
        Component toReturn = Component.text("");
        for (int i = 0; i < StringList.length; i++) {
            toReturn = toReturn.append(Component.text(ChatColor.translateAlternateColorCodes('&', StringList[i])));
             if(i != StringList.length-1){
                 toReturn = toReturn.append(Component.newline());
             }
        }
        return toReturn;
    }
    public static boolean isNumeric(String text){
        if (text == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(text);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static void sendErrorMsg(String msg, TextChannel textChannel){
        EmbedBuilder EmbedBuilder = new EmbedBuilder();
        EmbedBuilder.setColor(Color.red);
        EmbedBuilder.setTitle("**$**".replace("$", msg));
        textChannel.sendMessageEmbeds(EmbedBuilder.build()).queue();
    }

}
