package map;

import data.Vector2d;
import elements.MapElement;

import java.util.Map;
import java.util.Set;

public interface WorldMap extends MapElementObserver {

    void addElement(MapElement element);

    void removeElement(MapElement element);

    Map<Vector2d, Set<MapElement>> getElements();

    Vector2d getUnoccupiedPosition();

}

