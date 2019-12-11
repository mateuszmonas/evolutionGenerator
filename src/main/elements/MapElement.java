package elements;

import data.Vector;
import map.IMapElementObserver;

public interface MapElement {
    Vector getPosition();

    void notifyRemove();

    void notifyPositionChange(Vector oldPosition);

    void attachObserver(IMapElementObserver observer);

    void removeObserver(IMapElementObserver observer);
}
