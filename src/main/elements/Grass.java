package elements;

import data.Vector;
import map.IMapElementObserver;

import java.util.HashSet;
import java.util.Set;

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
