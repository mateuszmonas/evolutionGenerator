package map;

import elements.animal.Animal;

public interface IPositionChangeObserver {

    void positionChanged(Animal animal, Vector2d oldPosition);

}
