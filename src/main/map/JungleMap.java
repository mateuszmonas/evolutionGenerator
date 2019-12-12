package map;

import data.Rectangle;
import data.Vector2d;
import elements.MapElement;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class JungleMap implements WorldMap {

    Map<Vector2d, Set<MapElement>> elements = new HashMap<>();
    Rectangle area;

    public JungleMap(Rectangle area) {
        this.area = area;
    }

    public void addElement(MapElement element) {
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
            throw new IllegalArgumentException("no elements at position " + position.toString());
        }
        if (!elements.get(position).contains(element)) {
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

    @Override
    public Vector2d getUnoccupiedPosition() {
        List<Vector2d> unoccupiedPositions = new ArrayList<>();
        for (Vector2d position : area.getVectorSpace()) {
            if(!elements.containsKey(position)) unoccupiedPositions.add(position);
        }
        return unoccupiedPositions.get(ThreadLocalRandom.current().nextInt(unoccupiedPositions.size()));
    }
}
