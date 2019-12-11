package map;

import data.Vector;
import elements.MapElement;

public interface MapElementObserver {

    void onPositionChange(MapElement element, Vector oldPosition);

    void onRemoval(MapElement element);

}
