package elements.grass;

import data.Vector2d;
import elements.AbstractMapElement;

public class Grass extends AbstractMapElement {

    private int NUTRITION_VALUE = 2;

    public Grass(Vector2d position, int NUTRITION_VALUE) {
        this.NUTRITION_VALUE = NUTRITION_VALUE;
        this.position = position;
    }

    public Grass(Vector2d position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "*";
    }
}
