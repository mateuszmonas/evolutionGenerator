package map;

import data.Vector2d;
import elements.MapElement;

import java.util.Collection;
import java.util.HashMap;

public interface WorldMap extends MapElementObserver {

    void addElement(MapElement element);

    void removeElement(MapElement element);

    HashMap<Vector2d, Collection<MapElement>> getElements();

}

