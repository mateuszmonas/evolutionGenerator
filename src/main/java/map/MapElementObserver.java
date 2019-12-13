package map;

import data.Vector2d;
import elements.MapElement;

public interface MapElementObserver {

    void onPositionChange(MapElement element, Vector2d oldPosition);

    void onRemoval(MapElement element);

}
