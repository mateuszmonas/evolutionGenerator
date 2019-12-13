package view;

import data.Vector2d;
import elements.MapElement;
import map.MapStatus;

import java.util.Map;

public interface MapView {

    void updateMap(Map<Vector2d, MapElement> elements);

    void updateStatistics(MapStatus statistics);

}
