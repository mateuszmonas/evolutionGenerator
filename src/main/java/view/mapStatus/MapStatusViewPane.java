package view.mapStatus;

import data.Rectangle;
import javafx.scene.layout.VBox;
import map.MapStatus;
import view.mapStatus.map.MapViewPane;
import view.mapStatus.status.StatusDetailsViewPane;

public class MapStatusViewPane extends VBox implements MapStatusView {

    StatusDetailsViewPane statusView;
    MapViewPane mapView;

    @Override
    public void initialize(Rectangle area) {
        statusView = new StatusDetailsViewPane();
        getChildren().add(statusView);

        mapView = new MapViewPane(area);
        getChildren().add(mapView);

        this.widthProperty().addListener((observableValue, number, t1) -> {
            statusView.setPrefWidth(t1.doubleValue());
            mapView.setPrefWidth(t1.doubleValue());
        });
        this.heightProperty().addListener((observableValue, number, t1) -> {
            statusView.setPrefHeight(t1.doubleValue()/10);
            mapView.setPrefHeight((t1.doubleValue() / 10)*9);
        });
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
