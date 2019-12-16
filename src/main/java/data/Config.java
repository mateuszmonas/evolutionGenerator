package data;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Config {

    private static Config instance = null;
    private int width;
    private int height;
    private int moveEnergy;
    private int plantEnergy;
    private int initialAnimalEnergy;
    private double jungleRatio;
    private int initialAnimalCount;
    private int simulationCount;

    public static Config getInstance() {
        if (instance == null) {
            Gson gson = new Gson();
            String path = "parameters.json";
            try {
                JsonReader reader = new JsonReader(new FileReader(path));
                instance = gson.fromJson(reader, Config.class);
            } catch (FileNotFoundException e) {
                instance = new Config();
                e.printStackTrace();
            }
        }
        return instance;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public int getInitialAnimalEnergy() {
        return initialAnimalEnergy;
    }

    public double getJungleRatio() {
        return jungleRatio;
    }

    public int getInitialAnimalCount() {
        return initialAnimalCount;
    }

    public int getSimulationCount() {
        return simulationCount;
    }
}
