import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigReader {

    private String dosyaYolu = "/Users/hasankilinc/Desktop/projeödev/Parcel-Sort-Data-Structures-Project/src/config.txt"; //C:\\Users\\Hasan\\Documents\\java_project\\ceng_java_project\\src\\config.txt"
    public String[] keys = new String[100];
    public String[] values = new String[100];
    private int count = 0;


    public ConfigReader() {
        try (BufferedReader br = new BufferedReader(new FileReader(dosyaYolu))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                satir = satir.trim();
                if (satir.isEmpty() || satir.startsWith("#")) {
                    continue;
                }

                String[] parts = satir.split("=", 2);
                if (parts.length == 2) {
                    keys[count] = parts[0].trim();
                    values[count] = parts[1].trim();
                    count++;
                } else {
                    System.err.println("Hatalı satır: " + satir);
                }
            }
        } catch (IOException e) {
            System.err.println("Dosya okuma hatası: " + e.getMessage());
        }
    }

}