package pl.wolny.junglesurvivalcore.common.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.common.pluginobjects.PlayerServerAccessEnum;
import pl.wolny.junglesurvivalcore.common.pluginobjects.WaitingPlayer;

import java.sql.*;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

public class VerificationUtils {
    public static Component kickNotAtList (UUID uuid, String nick){
        return GeneralUtils.fixColorComponent("&9>>> &aJungle&2Survival &9<<<||&cPrzykro nam, nie znajdujesz się na liście graczy :c|" +
                "&cWejdź na dc.junglesurvival.pl i wpisz kod weryfikacji na #Weryfikacja|" +
                "&cKod weryfikacji: &a" + getPlayerCode(uuid, nick));
    }
    public static Component kickDatabaseError (){
        return GeneralUtils.fixColorComponent("&cWystąpił problem z autoryzacją twojego wejścia na serwer!|&cProsze skontatkuj się z administracją!");
    }
    public static void loadWhitelistedPlayers(){
        try {
            Connection myConn = DriverManager.getConnection(JungleSurvivalCore.core.getPluginConfig().JdbcString, JungleSurvivalCore.core.getPluginConfig().MySQLUser , JungleSurvivalCore.core.getPluginConfig().MySQLPassword);
            PreparedStatement myStmt = myConn.prepareStatement("SELECT * FROM `whitelist`");
            ResultSet resultSet = myStmt.executeQuery();
            while (resultSet.next()){
                JungleSurvivalCore.core.getCache().getWhitelistedPlayers().put(UUID.fromString(resultSet.getString("mcuuid")), true);
                JungleSurvivalCore.core.getCache().getDiscordIdToUUID().put(resultSet.getString("dcuuid"), UUID.fromString(resultSet.getString("mcuuid")));
            }
            myConn.close();
            myStmt.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public static void addToWhitelist(String mcname, String mcuuid, String dcuuid, String dcname){
        try {
            Connection myConn = DriverManager.getConnection(JungleSurvivalCore.core.getPluginConfig().JdbcString, JungleSurvivalCore.core.getPluginConfig().MySQLUser , JungleSurvivalCore.core.getPluginConfig().MySQLPassword);
            PreparedStatement myStmt = myConn.prepareStatement("INSERT INTO `whitelist` (`id`, `mcname`, `mcuuid`, `dcuuid`, `dcname`) VALUES (NULL, ?, ?, ?, ?)");
            myStmt.setString(1, mcname);
            myStmt.setString(2, mcuuid);
            myStmt.setString(3, dcuuid);
            myStmt.setString(4, dcname);
            myStmt.executeUpdate();
            JungleSurvivalCore.core.getCache().getWhitelistNumbers().remove(new WaitingPlayer(UUID.fromString(mcuuid), mcname));
            JungleSurvivalCore.core.getCache().getWhitelistedPlayers().put(UUID.fromString(mcuuid), true);
            JungleSurvivalCore.core.getCache().getDiscordIdToUUID().put(dcuuid, UUID.fromString(mcuuid));
            myConn.close();
            myStmt.close();
        } catch (SQLException throwables) {
            Bukkit.getLogger().log(Level.SEVERE, "Database is not working. Please check your login details.");
        }
    }
    public static PlayerServerAccessEnum canPlayerJoin(UUID uuid){
        boolean debug = false;
        if(debug){
            System.out.println((JungleSurvivalCore.core.getCache().getWhitelistedPlayers().toString()));
            System.out.println((JungleSurvivalCore.core.getCache().getDiscordIdToUUID().toString()));
            System.out.println((JungleSurvivalCore.core.getCache().getWhitelistNumbers().toString()));
        }
        if(JungleSurvivalCore.core.getCache().getWhitelistedPlayers().containsKey(uuid)){
            return PlayerServerAccessEnum.WHITELISTED;
        }
        //SELECT id FROM `whitelist` WHERE mcuuid=?
        try{
            Connection myConn = DriverManager.getConnection(JungleSurvivalCore.core.getPluginConfig().JdbcString, JungleSurvivalCore.core.getPluginConfig().MySQLUser , JungleSurvivalCore.core.getPluginConfig().MySQLPassword);
            PreparedStatement myStmt = myConn.prepareStatement("SELECT dcuuid, admin FROM `whitelist` WHERE mcuuid=?");
            myStmt.setString(1, uuid.toString());
            ResultSet resultSet = myStmt.executeQuery();
            boolean resoult = resultSet.next();
            if(resoult){
                JungleSurvivalCore.core.getCache().getDiscordIdToUUID().put(resultSet.getString("dcuuid"), uuid);
                JungleSurvivalCore.core.getCache().getWhitelistedPlayers().put(uuid, true);
                if(resultSet.getBoolean("admin")){
                    JungleSurvivalCore.core.getCache().getAdminList().add(uuid);
                }
            }
            myConn.close();
            myStmt.close();
            resultSet.close();
            return PlayerServerAccessEnum.getFromBoolean(resoult);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return PlayerServerAccessEnum.MYSQL_ERROR;
    }
    public static String getPlayerCode(UUID id, String nick){
        WaitingPlayer waitingPlayer = new WaitingPlayer(id, nick);
        if(JungleSurvivalCore.core.getCache().getWhitelistNumbers().containsKey(HashMapUtils.get(JungleSurvivalCore.core.getCache().getWhitelistNumbers(), waitingPlayer.id))){
            return JungleSurvivalCore.core.getCache().getWhitelistNumbers().get(HashMapUtils.get(JungleSurvivalCore.core.getCache().getWhitelistNumbers(), waitingPlayer.id));
        }
        int randomNum = ThreadLocalRandom.current().nextInt(100000, 999999 + 1);
        JungleSurvivalCore.core.getCache().getWhitelistNumbers().put(new WaitingPlayer(id, nick), String.valueOf(randomNum));
        return String.valueOf(randomNum);
    }
    public static UUID getMcUUID(String dcUUID){
        return JungleSurvivalCore.core.getCache().getDiscordIdToUUID().get(dcUUID);
    }
}
