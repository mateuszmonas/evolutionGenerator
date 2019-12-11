package elements;

import data.Vector;
import map.IMapElementObserver;

import java.util.HashSet;
import java.util.Set;

public class AbstractMapElement implements MapElement {
    private Set<IMapElementObserver> observers = new HashSet<>();
    private Vector position;

    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
    public void notifyRemove() {
        observers.forEach(o -> o.onRemoval(this));
    }

    @Override
    public void notifyPositionChange(Vector oldPosition) {
        observers.forEach(o -> o.onPositionChange(this, oldPosition));
    }

    @Override
    public void attachObserver(IMapElementObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IMapElementObserver observer) {
        observers.remove(observer);
    }


}
