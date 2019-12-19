package elements.plant;

import util.Config;
import data.Vector2d;
import elements.AbstractMapElement;

public class Plant extends AbstractMapElement {

    private int nutritionValue = Config.getInstance().getPlantEnergy();

    public Plant(Vector2d position) {
        this.position = position;
    }

    public int getNutritionValue() {
        return nutritionValue;
    }

    @Override
    public Icon getIcon() {
        return Icon.PLANT;
    }

    @Override
    public String toString() {
        return "grass";
    }

    @Override
    public String toDetails() {
        return toString();
    }
}
