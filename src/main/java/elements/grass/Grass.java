package elements.grass;

import data.Config;
import data.Vector2d;
import elements.AbstractMapElement;

public class Grass extends AbstractMapElement {

    private int nutritionValue = Config.getInstance().getPlantEnergy();

    public Grass(Vector2d position) {
        this.position = position;
    }

    public int getNutritionValue() {
        return nutritionValue;
    }

    @Override
    public String toString() {
        return "*";
    }
}
