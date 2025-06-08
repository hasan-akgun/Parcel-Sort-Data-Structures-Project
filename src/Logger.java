import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static FileWriter writer;

    public static void initialize(String filename) {
        try {
            writer = new FileWriter(filename, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String msg) {
        System.out.println(msg); // Konsola yazıyorum
        if (writer != null) {
            try {
                writer.write(msg + "\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close() {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
