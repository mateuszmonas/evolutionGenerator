package view.mapStatus;

import data.Rectangle;
import elements.MapElement;
import map.MapStatus;

public interface MapStatusView {

    void initialize(MapStatus status);

    void updateMap(MapStatus status);

    void trackedElementChange(MapElement element);

}
