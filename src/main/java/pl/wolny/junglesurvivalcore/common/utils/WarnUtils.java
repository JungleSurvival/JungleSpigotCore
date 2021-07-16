package pl.wolny.junglesurvivalcore.common.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.common.pluginobjects.PlayerWarn;
import pl.wolny.junglesurvivalcore.common.pluginobjects.WarnList;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class WarnUtils {
    public static void warnPlayer(UUID uuid, String reason, String admin, Boolean mcwarn){
        try {
            Connection myConn = DriverManager.getConnection(JungleSurvivalCore.core.getPluginConfig().JdbcString, JungleSurvivalCore.core.getPluginConfig().MySQLUser , JungleSurvivalCore.core.getPluginConfig().MySQLPassword);
            PreparedStatement myStmt = myConn.prepareStatement("INSERT INTO `warns` (`id`, `mcuuid`, `reason`, `admin`, `mcwarn`, `date`) VALUES (NULL, ?, ?, ?, ?, ?)");
            myStmt.setString(1, uuid.toString());
            myStmt.setString(2, reason);
            myStmt.setString(3, admin);
            myStmt.setLong(5, System.currentTimeMillis());
            myStmt.setBoolean(4, mcwarn);
            myStmt.executeUpdate();
            JungleSurvivalCore.core.getCache().getWarnListFromUUID().get(uuid).getPlayerWarnList().add(new PlayerWarn(new Date(), reason, admin, mcwarn));
            myConn.close();
            myStmt.close();
        } catch (SQLException throwables) {
            Bukkit.getLogger().log(Level.SEVERE, "Database is not working. Please check your login details.");
        }
    }

    public static List<PlayerWarn> getWarns(String uuid){
        if(JungleSurvivalCore.core.getCache().getWarnListFromUUID().get(UUID.fromString(uuid)) != null){
            return JungleSurvivalCore.core.getCache().getWarnListFromUUID().get(UUID.fromString(uuid)).getPlayerWarnList();
        }
        return null;
    }
    public static boolean loadWarnsFromDiscordID(String id){
        try{
            Connection myConn = DriverManager.getConnection(JungleSurvivalCore.core.getPluginConfig().JdbcString, JungleSurvivalCore.core.getPluginConfig().MySQLUser , JungleSurvivalCore.core.getPluginConfig().MySQLPassword);
            PreparedStatement myStmt = myConn.prepareStatement("SELECT mcuuid, mcname FROM `whitelist` WHERE dcuuid=?");
            myStmt.setString(1, id);
            ResultSet resultSet = myStmt.executeQuery();
            if(!resultSet.next()){
                return false;
            }
            String mcuuid = resultSet.getString("mcuuid");
            String mcname = resultSet.getString("mcname");
            WarnList warnList = new WarnList(mcname);
            PreparedStatement prepareStatement = myConn.prepareStatement("SELECT reason, date, admin, mcwarn FROM `warns` WHERE mcuuid=?");
            prepareStatement.setString(1, mcuuid);
            ResultSet resultSet1 = prepareStatement.executeQuery();
            while (resultSet1.next()){
                warnList.getPlayerWarnList().add(new PlayerWarn(new Date(resultSet1.getLong("date")), resultSet1.getString("reason"), resultSet1.getString("admin"), resultSet1.getBoolean("mcwarn")));
            }
            JungleSurvivalCore.core.getCache().getDiscordIdToUUID().put(id, UUID.fromString(mcuuid));
            JungleSurvivalCore.core.getCache().getWarnListFromUUID().put(UUID.fromString(mcuuid), warnList);
            return true;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static void genMcWarnMsg(UUID uuid, CommandSender sender, String nick){
        List<PlayerWarn> warns = getWarns(uuid.toString());
        assert warns != null;
        if(warns.size() == 0){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('+', "+aGracz +f" + nick + " +anie ma warnów!"));
            return;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('^', "^9>>> ^aOstrzezenia gracza " + nick + "^a:"));
        for (int i = 0; i < warns.size(); i++) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('^', "^9>>> ^aWarn o id " + i + "^a:"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('^', "^aPowód:^f " + warns.get(i).getReason() + "^a."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('^', "^aAdmin:^f " + warns.get(i).getAdmin() + "^a."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('^', "^aTyp warna:^f " + getWarnType(warns.get(i).isMcWarn()) + "^a."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('^', "^aData:^f " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(warns.get(i).getDate()) + "^a."));
        }
        //I did not use void from GeneralUtils bc i am copping that code from my old plugin
    }
    public static void genDcWarnMsg(UUID uuid, String name, TextChannel textChannel){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Warny " + name);
        builder.setColor(Color.green);
        List<PlayerWarn> warns = getWarns(uuid.toString());
        assert warns != null;
        if(warns.size() == 0){
            builder.setDescription("Taki gracz nie ma warnów!");
            textChannel.sendMessageEmbeds(builder.build()).queue();
            return;
        }
        for (int i = 0; i < warns.size(); i++) {
            builder.addField("Warn o id " + i, "Powód: " + warns.get(0).getReason() + "\n" + "Admin: " + warns.get(0).getAdmin() + "\n" + "Typ warna: " + getWarnType(warns.get(0).isMcWarn()) + "\n" + "Data: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(warns.get(i).getDate()), false);
        }
        textChannel.sendMessageEmbeds(builder.build()).queue();
    }
    public static String getWarnType(Boolean mcwarn){
        return (mcwarn) ?  ("Minecraft") : ("Discord");
    }
    public static void loadWarns(UUID uuid, String playername){
        try{
            Connection myConn = DriverManager.getConnection(JungleSurvivalCore.core.getPluginConfig().JdbcString, JungleSurvivalCore.core.getPluginConfig().MySQLUser , JungleSurvivalCore.core.getPluginConfig().MySQLPassword);
            PreparedStatement myStmt = myConn.prepareStatement("SELECT * FROM `warns` WHERE mcuuid=? ORDER BY date ASC");
            myStmt.setString(1, uuid.toString());
            ResultSet resultSet = myStmt.executeQuery();
            WarnList warnList = new WarnList(playername);
            while (resultSet.next()){
                warnList.getPlayerWarnList().add(new PlayerWarn(new Date(resultSet.getLong("date")), resultSet.getString("reason"), resultSet.getString("admin"), resultSet.getBoolean("mcwarn")));
            }
            JungleSurvivalCore.core.getCache().getWarnListFromUUID().put(uuid, warnList);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
