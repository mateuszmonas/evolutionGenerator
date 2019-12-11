package elements;

import data.Vector;

public class Grass implements IMapElement {

    private int NUTRITION_VALUE = 2;
    private Vector position;

    public Grass(Vector position, int NUTRITION_VALUE) {
        this.NUTRITION_VALUE = NUTRITION_VALUE;
        this.position = position;
    }

    public Grass(Vector position) {
        this.position = position;
    }

    public Vector getPosition() {
        return position;
    }

    public int getNutritionValue() {
        return NUTRITION_VALUE;
    }

    @Override
    public String toString() {
        return "*";
    }
}
