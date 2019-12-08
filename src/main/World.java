import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class World {
    public static void main(String[] args) {
        try (InputStream input = new FileInputStream("src/resources/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.out.println(Integer.parseInt(prop.getProperty("width")));
            System.out.println(Integer.parseInt(prop.getProperty("height")));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
