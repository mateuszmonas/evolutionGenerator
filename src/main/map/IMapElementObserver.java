package map;

import elements.IMapElement;
import data.Vector;

public interface IMapElementObserver {

    void onPositionChange(IMapElement element, Vector oldPosition);

    void onRemoval(IMapElement element);

}
