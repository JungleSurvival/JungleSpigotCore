package pl.wolny.junglespigotcore.utils;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    public static void log(String text){
        try {
            FileWriter writer = new FileWriter("plugins/JungleSpigotCore/log.txt" ,true);
            writer.write(text);
            writer.write("\r\n");   // write new line
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
