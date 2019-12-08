package elements;

import map.Vector2d;

public class Grass implements IMapElement {

    private int NUTRITION_VALUE = 2;
    private Vector2d position;

    public Grass(Vector2d position, int NUTRITION_VALUE) {
        this.NUTRITION_VALUE = NUTRITION_VALUE;
        this.position = position;
    }

    public Grass(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
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
