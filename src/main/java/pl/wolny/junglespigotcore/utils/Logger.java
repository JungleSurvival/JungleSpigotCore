package pl.wolny.junglespigotcore.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public static void log(String text){
        try {
            FileWriter writer = new FileWriter("plugins/JungleSpigotCore/log.txt" ,true);
            writer.write("[" + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + "] " + text);
            writer.write("\r\n");   // write new line
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
