package view.mapStatus;

import data.Rectangle;
import elements.MapElement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import map.MapStatus;
import view.mapStatus.map.MapViewPane;
import view.mapStatus.map.TrackElementListener;
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
        mapView.initialize(mapStatus.getArea());
        mapView.addOnFieldClickListener(mapStatus::setTrackedElement);
    }

    @Override
    public void trackedElementChange(MapElement element) {
        statusView.updateTrackedElementDetails(element);
        mapView.trackedElementChange(element);
    }

    @Override
    public void updateMap(MapStatus status) {
        if (mapView == null || statusView == null) {
            throw new IllegalStateException("MapStatusView has not been initialized");
        }
        mapView.updateMap(status.getElementsToDisplay(), status.getElements(), status.getTrackedElement(), status.getDominatingGenomeElementsPositions());
        statusView.update(status.getDetails(), status.getTrackedElement());
    }
}
