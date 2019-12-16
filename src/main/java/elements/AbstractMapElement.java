package elements;

import data.Vector2d;
import map.MapElementObserver;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractMapElement implements MapElement {
    protected Set<MapElementObserver> observers = new HashSet<>();
    protected Vector2d position;

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public void notifyRemove() {
        observers.forEach(o -> o.onRemoval(this));
    }

    @Override
    public void notifyPositionChange(Vector2d oldPosition) {
        observers.forEach(o -> o.onPositionChange(this, oldPosition));
    }

    @Override
    public void attachObserver(MapElementObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(MapElementObserver observer) {
        observers.remove(observer);
    }

    abstract public Icon getIcon();
}
