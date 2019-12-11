import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class World {
    public static void main(String[] args) throws InterruptedException, IOException {

        Properties prop = new Properties();
        InputStream input = World.class.getResourceAsStream("config.properties");
        prop.load(input);
    }
}
