package elements;

import data.Vector;
import map.MapElementObserver;

public interface MapElement {
    Vector getPosition();

    void notifyRemove();

    void notifyPositionChange(Vector oldPosition);

    void attachObserver(MapElementObserver observer);

    void removeObserver(MapElementObserver observer);
}
