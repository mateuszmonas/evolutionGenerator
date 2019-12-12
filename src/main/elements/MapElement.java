package elements;

import data.Vector2d;
import map.MapElementObserver;

public interface MapElement {
    Vector2d getPosition();

    void notifyRemove();

    void notifyPositionChange(Vector2d oldPosition);

    void attachObserver(MapElementObserver observer);

    void removeObserver(MapElementObserver observer);

    String toString();
}
