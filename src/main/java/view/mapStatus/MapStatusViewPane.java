package view.mapStatus;

import data.Rectangle;
import data.Vector2d;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import map.MapStatus;
import view.mapStatus.map.MapView;
import view.mapStatus.map.MapViewPane;
import view.mapStatus.map.field.MapField;

import java.util.Map;
import java.util.stream.Collectors;

public class MapStatusViewPane extends Pane implements MapStatusView {
    MapViewPane mapView;

    @Override
    public void initialize(Rectangle area) {
        mapView = new MapViewPane(area);
        getChildren().add(mapView);
    }

    @Override
    public void updateMap(MapStatus status) {
        if (mapView == null) {
            throw new IllegalStateException("MapStatusView has not been initialized");
        }
        mapView.updateMap(status.getElementsToDisplay(), status.getElements());
    }
}
