package view.mapStatus;

import data.Vector2d;
import elements.MapElement;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import map.MapStatus;
import view.mapStatus.map.MapViewPane;
import view.mapStatus.status.StatusDetailsViewPane;

public class MapStatusViewPane extends VBox implements MapStatusView {

    StatusDetailsViewPane statusView;
    MapViewPane mapView;

    public MapStatusViewPane(double prefWidth, double prefHeight) {
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);

        mapView = new MapViewPane(prefWidth, prefHeight/5*4);
        statusView = new StatusDetailsViewPane(prefWidth, prefHeight/5);
        getChildren().add(mapView);
        getChildren().add(statusView);

        setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderStroke.THIN, Insets.EMPTY)));
    }

    @Override
    public void initialize(MapStatus mapStatus) {
        mapView.initialize(mapStatus::setTrackedElement, mapStatus.getArea());
    }

    @Override
    public void trackedElementChange(MapElement trackedElement, Vector2d trackedElementPosition) {
        statusView.updateTrackedElementDetails(trackedElement);
        mapView.trackedElementChange(trackedElementPosition);
    }

    public void showDominantGenome(boolean show) {
        mapView.setShowingDominantAnimals(show);
    }

    @Override
    public void updateMap(MapStatus status) {
        if (mapView == null || statusView == null) {
            throw new IllegalStateException("MapStatusView has not been initialized");
        }
        mapView.updateMap(status.getElementsPositions());
        statusView.update(status.getDetails(), status.getElementsPositions().trackedElement);
    }
}
