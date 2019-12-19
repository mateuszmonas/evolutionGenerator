package view.mapStatus.map;

import elements.MapElement;
import javafx.scene.control.MenuItem;


public class MapFieldContextMenuItem extends MenuItem {

    public MapFieldContextMenuItem(MapElement element, TrackingEventListener listener) {
        setOnAction(actionEvent -> listener.trackedMapElementSelection(element));
        setText(element.toString());
    }
}
