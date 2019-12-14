package view;

import data.Rectangle;
import map.MapStatus;

public interface MapView {

    void updateMap(MapStatus status);

    void initialize(Rectangle area);

}
