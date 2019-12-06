package map;

import elements.Animal;

public interface IPositionChangeObserver {

    void positionChanged(Animal animal, Vector2d oldPosition);

}
