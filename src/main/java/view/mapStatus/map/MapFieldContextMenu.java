package view.mapStatus.map;

import elements.MapElement;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.Set;
import java.util.stream.Collectors;

public class MapFieldContextMenu extends ContextMenu {

    public MapFieldContextMenu() {

    }

    public void setElements(Set<MapElement> elements) {
        getItems().clear();
        if (elements != null) {
            getItems().addAll(elements.stream().map(element -> new MenuItem(element.toString())).collect(Collectors.toSet()));
        }
    }
}
