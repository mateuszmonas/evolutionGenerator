package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public final class FileUtil {

    public static void clearFile(String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write("");
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void appendToFile(String path, String text) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.append(text);
            writer.append('\n');
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
