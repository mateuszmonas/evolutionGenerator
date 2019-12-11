package map;

import elements.animal.Animal;
import util.Vector;

public interface IPositionChangeObserver {

    void positionChanged(Animal animal, Vector oldPosition);

}
