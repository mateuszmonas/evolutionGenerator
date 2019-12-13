package data;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class Config {

    private static Config instance = null;
    private int width;
    private int height;
    private int moveEnergy;
    private int plantEnergy;
    private int initialAnimalEnergy;
    private double jungleRatio;
    private int initialAnimalCount;

    public static Config getInstance() {
        if (instance == null) {
            Gson gson = new Gson();
            String path = "parameters.json";
            try {
                File f = new File(path);
                if (!f.exists() || f.isDirectory()) {
                    PrintWriter writer = new PrintWriter(path, "UTF-8");
                    writer.print("{\n" +
                            "  \"width\": 10,\n" +
                            "  \"height\": 10,\n" +
                            "  \"moveEnergy\": 1,\n" +
                            "  \"plantEnergy\": 5,\n" +
                            "  \"initialAnimalEnergy\": 20,\n" +
                            "  \"jungleRatio\": 0.5,\n" +
                            "  \"initialAnimalCount\": 10\n" +
                            "}");
                    writer.close();
                }
                JsonReader reader = new JsonReader(new FileReader(path));
                instance = gson.fromJson(reader, Config.class);
            } catch (Exception e) {
                instance = new Config();
                e.printStackTrace();
            }
        } return instance;
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
}
