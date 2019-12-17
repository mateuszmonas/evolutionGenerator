package view.mapStatus;

import data.Rectangle;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import map.MapStatus;
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

        mapView.prefWidthProperty().bind(this.widthProperty());
        mapView.prefHeightProperty().bind(this.heightProperty().divide(10).multiply(9));
        mapView.minWidthProperty().bind(this.widthProperty());
        mapView.minHeightProperty().bind(this.heightProperty().divide(10).multiply(9));
        mapView.maxWidthProperty().bind(this.widthProperty());
        mapView.maxHeightProperty().bind(this.heightProperty().divide(10).multiply(9));

        statusView.prefWidthProperty().bind(this.widthProperty());
        statusView.prefHeightProperty().bind(this.heightProperty().divide(10));
        statusView.minWidthProperty().bind(this.widthProperty());
        statusView.minHeightProperty().bind(this.heightProperty().divide(10));
        statusView.maxWidthProperty().bind(this.widthProperty());
        statusView.maxHeightProperty().bind(this.heightProperty().divide(10));

        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.MEDIUM)));
    }

    @Override
    public void initialize(Rectangle area) {
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
