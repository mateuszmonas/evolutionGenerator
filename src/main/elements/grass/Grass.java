package elements.grass;

import data.Vector;
import elements.AbstractMapElement;

public class Grass extends AbstractMapElement {

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

    @Override
    public String toString() {
        return "*";
    }
}
