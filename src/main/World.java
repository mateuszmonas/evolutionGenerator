import elements.Animal;
import map.Vector2d;
import map.WorldMap;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

public class World {
    public static void main(String[] args) throws InterruptedException, IOException {

        Properties prop = new Properties();
        InputStream input = new FileInputStream("src/resources/config.properties");
        prop.load(input);
        WorldMap map = WorldMap.newMapBuilder()
                .withWidth(Integer.parseInt(prop.getProperty("map.width")))
                .withHeight(Integer.parseInt(prop.getProperty("map.height")))
                .withAnimalEnergy(Integer.parseInt(prop.getProperty("map.initialAnimalEnergy")))
                .withJungleRatio(Double.parseDouble(prop.getProperty("map.jungleRatio")))
                .withMoveEnergy(Integer.parseInt(prop.getProperty("map.moveEnergy")))
                .withPlantEnergy(Integer.parseInt(prop.getProperty("map.plantEnergy")))
                .withInitialAnimalCount(Integer.parseInt(prop.getProperty("map.initialAnimalCount")))
                .build();
        for (int i = 0; i < 100; i++) {
            map.simulate(true);
        }
    }
}
