package view;

import data.Rectangle;
import map.MapStatus;

public interface SimulationView {

    void updateMap(MapStatus status);

    void initialize(Rectangle area);

}
