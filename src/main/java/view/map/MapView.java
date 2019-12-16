package view.map;

import data.Rectangle;
import data.Vector2d;
import elements.MapElement;
import map.MapStatus;

import java.util.Map;
import java.util.Set;

public interface MapView {

    void initialize(Rectangle area);

    void updateMap(MapStatus status);
}
