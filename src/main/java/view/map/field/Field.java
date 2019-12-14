package view.map.field;

import elements.MapElement;

import java.util.Set;

public interface Field {

    void update(MapElement elementToDisplay, Set<MapElement> elements);

}
