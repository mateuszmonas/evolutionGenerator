package view.mapStatus;

import data.Config;
import data.Rectangle;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import map.MapStatus;
import view.ViewConfig;
import view.mapStatus.map.MapViewPane;
import view.mapStatus.status.StatusDetailsViewPane;

public class MapStatusViewPane extends VBox implements MapStatusView {

    StatusDetailsViewPane statusView;
    MapViewPane mapView;

    public MapStatusViewPane() {
        mapView = new MapViewPane();
        getChildren().add(mapView);

        statusView = new StatusDetailsViewPane();
        getChildren().add(statusView);

        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.MEDIUM)));
    }

    @Override
    public void initialize(Rectangle area) {
        mapView.setPrefWidth(this.getPrefWidth());
        mapView.setPrefHeight(this.getPrefHeight());
        mapView.initialize(area);
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
