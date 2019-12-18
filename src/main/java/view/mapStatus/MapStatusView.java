package view.mapStatus;

import data.Rectangle;
import data.Vector2d;
import elements.MapElement;
import map.MapStatus;

public interface MapStatusView {

    void initialize(MapStatus status);

    void updateMap(MapStatus status);

    void trackedElementChange(MapElement trackedElement, Vector2d trackedElementPosition);

}
