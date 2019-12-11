package map;

import elements.MapElement;

public interface WorldMap extends MapElementObserver {

    void addElement(MapElement element);

    void removeElement(MapElement element);

}

