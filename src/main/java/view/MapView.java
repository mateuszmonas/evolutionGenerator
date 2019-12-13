package view;

import data.Vector2d;
import elements.MapElement;
import map.MapHistory;

import java.util.Map;

public interface MapView {

    void updateMap(Map<Vector2d, MapElement> elements);

    void updateStatistics(MapHistory statistics);

}
