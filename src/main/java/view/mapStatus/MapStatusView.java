package view.mapStatus;

import data.Rectangle;
import map.MapStatus;

public interface MapStatusView {

    void initialize(Rectangle area);

    void updateMap(MapStatus status);

}
