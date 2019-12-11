package map;

import elements.MapElement;
import data.Vector;

public interface IMapElementObserver {

    void onPositionChange(MapElement element, Vector oldPosition);

    void onRemoval(MapElement element);

}
