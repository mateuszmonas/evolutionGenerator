package view.mapStatus;

import data.Rectangle;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import map.MapStatus;
import view.mapStatus.map.MapViewPane;
import view.mapStatus.status.StatusViewPane;

public class MapStatusViewPane extends VBox implements MapStatusView {

    StatusViewPane statusView;
    MapViewPane mapView;

    @Override
    public void initialize(Rectangle area) {
        statusView = new StatusViewPane();
        getChildren().add(statusView);

        mapView = new MapViewPane(area);
        getChildren().add(mapView);
    }

    @Override
    public void updateMap(MapStatus status) {
        if (mapView == null || statusView == null) {
            throw new IllegalStateException("MapStatusView has not been initialized");
        }
        mapView.updateMap(status.getElementsToDisplay(), status.getElements());
        statusView.update(status.getDetails());
    }
}
