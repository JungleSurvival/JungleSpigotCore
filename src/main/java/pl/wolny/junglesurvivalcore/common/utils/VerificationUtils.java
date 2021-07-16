package pl.wolny.junglesurvivalcore.common.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import pl.wolny.junglesurvivalcore.JungleSurvivalCore;
import pl.wolny.junglesurvivalcore.common.pluginobjects.WaitingPlayer;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

public class VerificationUtils {
    private static Boolean debug = true;
    public static Component kickNotAtList (UUID uuid, String nick){
        return GeneralUtils.fixColorComponent("&9>>> &aJungle&2Survival &9<<<||&cPrzykro nam, nie znajdujesz się na liście graczy :c|" +
                "&cWejdź na dc.junglesurvival.pl i wpisz kod weryfikacji na #Weryfikacja|" +
                "&cKod weryfikacji: &a" + getPlayerCode(uuid, nick));
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
            JungleSurvivalCore.core.getCache().getWhitelistNumbers().removeValue(new WaitingPlayer(UUID.fromString(mcuuid), mcname));
            JungleSurvivalCore.core.getCache().getWhitelistedPlayers().put(UUID.fromString(mcuuid), true);
            JungleSurvivalCore.core.getCache().getDiscordIdToUUID().put(dcuuid, UUID.fromString(mcuuid));
            myConn.close();
            myStmt.close();
        } catch (SQLException throwables) {
            Bukkit.getLogger().log(Level.SEVERE, "Database is not working. Please check your login details.");
        }
    }
    public static boolean canPlayerJoin(UUID uuid){
        if(debug){
            System.out.println((JungleSurvivalCore.core.getCache().getWhitelistedPlayers().toString()));
            System.out.println((JungleSurvivalCore.core.getCache().getDiscordIdToUUID().toString()));
            System.out.println((JungleSurvivalCore.core.getCache().getWhitelistNumbers().toString()));
        }
        if(JungleSurvivalCore.core.getCache().getWhitelistedPlayers().containsKey(uuid)){
            return JungleSurvivalCore.core.getCache().getWhitelistedPlayers().get(uuid);
        }
        //SELECT id FROM `whitelist` WHERE mcuuid=?
        try{
            Connection myConn = DriverManager.getConnection(JungleSurvivalCore.core.getPluginConfig().JdbcString, JungleSurvivalCore.core.getPluginConfig().MySQLUser , JungleSurvivalCore.core.getPluginConfig().MySQLPassword);
            PreparedStatement myStmt = myConn.prepareStatement("SELECT dcuuid FROM `whitelist` WHERE mcuuid=?");
            myStmt.setString(1, uuid.toString());
            ResultSet resultSet = myStmt.executeQuery();
            boolean resoult = resultSet.next();
            JungleSurvivalCore.core.getCache().getWhitelistedPlayers().put(uuid, resoult);
            if(resoult){
                JungleSurvivalCore.core.getCache().getDiscordIdToUUID().put(resultSet.getString("dcuuid"), uuid);
            }
            myConn.close();
            myStmt.close();
            resultSet.close();
            return resoult;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public static String getPlayerCode(UUID id, String nick){
        if(JungleSurvivalCore.core.getCache().getWhitelistNumbers().containsValue(new WaitingPlayer(id, nick))){
            return JungleSurvivalCore.core.getCache().getWhitelistNumbers().getKey(new WaitingPlayer(id, nick));
        }
        int randomNum = ThreadLocalRandom.current().nextInt(100000, 999999 + 1);
        JungleSurvivalCore.core.getCache().getWhitelistNumbers().put(String.valueOf(randomNum), new WaitingPlayer(id, nick));
        return String.valueOf(randomNum);
    }
    public static UUID getMcUUID(String dcUUID){
        return JungleSurvivalCore.core.getCache().getDiscordIdToUUID().get(dcUUID);
    }
}
