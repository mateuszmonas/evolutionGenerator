package map;

import data.Rectangle;
import data.Vector2d;
import elements.MapElement;

import java.util.*;

public class JungleMap implements WorldMap {

    protected Map<Vector2d, Set<MapElement>> elements = new HashMap<>();
    private Rectangle area;

    public JungleMap(Rectangle area) {
        this.area = area;
    }

    public void addElement(MapElement element) throws IllegalArgumentException {
        if (!area.contains(element.getPosition())) {
            throw new IllegalArgumentException(element.getPosition().toString() + " is out of map bounds");
        }
        if (!elements.containsKey(element.getPosition())) {
            elements.put(element.getPosition(), new HashSet<>());
        }
        elements.get(element.getPosition()).add(element);
        element.attachObserver(this);
    }

    @Override
    public void onPositionChange(MapElement element, Vector2d oldPosition) {
        if (!elements.containsKey(oldPosition)) {
            throw new IllegalArgumentException("no element at position " + oldPosition.toString());
        }
        oldPosition = area.normalisePosition(oldPosition);
        Vector2d newPosition = area.normalisePosition(element.getPosition());
        elements.get(oldPosition).remove(element);
        if (elements.get(oldPosition).isEmpty()) {
            elements.remove(oldPosition);
        }
        if (!elements.containsKey(newPosition)) {
            elements.put(newPosition, new HashSet<>());
        }
        elements.get(newPosition).add(element);
    }

    public Set<MapElement> objectsAt(Vector2d position) {
        position = area.normalisePosition(position);
        return elements.getOrDefault(position, null);
    }

    public void removeElement(MapElement element) {
        Vector2d position = area.normalisePosition(element.getPosition());
        if (!elements.containsKey(position)) {
            throw new IllegalArgumentException("element " + element.toString() + " is not on map");
        }
        elements.get(position).remove(element);
        if (elements.get(position).isEmpty())
            elements.remove(position);
        element.removeObserver(this);
    }

    @Override
    public Map<Vector2d, Set<MapElement>> getElements() {
        return elements;
    }

    @Override
    public void onRemoval(MapElement element) {
        removeElement(element);
    }
}
