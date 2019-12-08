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
                .withWidth((Integer) prop.get("map.width"))
                .withHeight((Integer) prop.get("map.height"))
                .withAnimalEnergy((Integer) prop.get("map.animalEnergy"))
                .withJungleRatio((Double) prop.get("map.jungleRatio"))
                .withMoveEnergy((Integer) prop.get("map.moveEnergy"))
                .withPlantEnergy((Integer) prop.get("map.plantEnergy"))
                .withInitialAnimalCount((Integer) prop.get("map.initialAnimalCount"))
                .build();
        for (int i = 0; i < 100; i++) {
            map.simulate(true);
        }
    }
}
