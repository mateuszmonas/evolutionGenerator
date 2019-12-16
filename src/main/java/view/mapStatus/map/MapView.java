package view.mapStatus.map;

import data.Rectangle;
import data.Vector2d;
import elements.MapElement;
import map.MapStatus;

import java.util.Map;
import java.util.Set;

public interface MapView {

    void updateMap(Map<Vector2d, MapElement> elementsToDisplay, Map<Vector2d, Set<MapElement>> elements);

}
