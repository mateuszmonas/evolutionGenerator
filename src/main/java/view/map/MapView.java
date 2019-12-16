package view.map;

import data.Rectangle;
import map.MapStatus;

public interface MapView {

    void initialize(Rectangle area);

    void updateMap(MapStatus status);
}
