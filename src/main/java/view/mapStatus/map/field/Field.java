package view.mapStatus.map.field;

import elements.MapElement;

import java.util.Set;

public interface Field {

    void update(MapElement elementToDisplay, Set<MapElement> elements);

    MapElement getDisplayedElement();

}
