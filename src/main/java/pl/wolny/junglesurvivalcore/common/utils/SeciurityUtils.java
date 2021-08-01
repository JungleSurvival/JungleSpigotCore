package pl.wolny.junglesurvivalcore.common.utils;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SeciurityUtils {
    public static String getSecret(UUID uuid){
        File file = new File("2fa-auth.yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        return configuration.getString("code." + uuid.toString());
    }
    public static void setSecret(UUID uuid, String secret){
        File file = new File("2fa-auth.yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("code." + uuid.toString(), secret);
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean playerInputCode(UUID uuid, int code)
    {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        return gAuth.authorize(getSecret(uuid), code);
    }
}
