import map.JungleMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class World {
    public static void main(String[] args) throws InterruptedException, IOException {

        Properties prop = new Properties();
        InputStream input = World.class.getResourceAsStream("config.properties");
        prop.load(input);
        JungleMap map = JungleMap.newMapBuilder()
                .withWidth(Integer.parseInt(prop.getProperty("map.width")))
                .withHeight(Integer.parseInt(prop.getProperty("map.height")))
                .withAnimalEnergy(Integer.parseInt(prop.getProperty("map.initialAnimalEnergy")))
                .withJungleRatio(Double.parseDouble(prop.getProperty("map.jungleRatio")))
                .withMoveEnergy(Integer.parseInt(prop.getProperty("map.moveEnergy")))
                .withPlantEnergy(Integer.parseInt(prop.getProperty("map.plantEnergy")))
                .withInitialAnimalCount(Integer.parseInt(prop.getProperty("map.initialAnimalCount")))
                .build();
    }
}
