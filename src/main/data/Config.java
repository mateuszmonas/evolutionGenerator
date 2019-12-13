package data;

public class Config {

    private static Config instance = null;
    int width;
    int height;
    int moveEnergy;
    int plantEnergy;
    int initialAnimalEnergy;
    double jungleRatio;
    int initialAnimalCount;

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        } return instance;
    }
}
