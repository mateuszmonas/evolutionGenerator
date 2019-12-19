package view.mapStatus.map;

import elements.MapElement;
import javafx.scene.control.ContextMenu;

import java.util.Set;
import java.util.stream.Collectors;

public class MapFieldContextMenu extends ContextMenu {

    TrackingEventListener listener;

    public MapFieldContextMenu(TrackingEventListener listener) {
        this.listener = listener;
    }

    public void setElements(Set<MapElement> elements) {
        getItems().clear();
        if (elements != null) {
            getItems().addAll(elements.stream().map(element -> new MapFieldContextMenuItem(element, listener)).collect(Collectors.toSet()));
        }
    }
}
