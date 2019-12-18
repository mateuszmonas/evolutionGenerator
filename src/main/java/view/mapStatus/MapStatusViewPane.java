package view.mapStatus;

import data.Rectangle;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import map.MapStatus;
import view.mapStatus.map.MapViewPane;
import view.mapStatus.status.StatusDetailsViewPane;

public class MapStatusViewPane extends BorderPane implements MapStatusView {

    StatusDetailsViewPane statusView;
    MapViewPane mapView;

    public MapStatusViewPane() {
        mapView = new MapViewPane();
        setTop(mapView);

        statusView = new StatusDetailsViewPane();
        setBottom(statusView);

        mapView.setPadding(new Insets(10));
        statusView.setPadding(new Insets(10));

        mapView.prefWidthProperty().bind(this.widthProperty().subtract(mapView.getPadding().getLeft()+mapView.getPadding().getRight()));
        mapView.prefHeightProperty().bind(this.heightProperty().subtract(mapView.getPadding().getTop()+mapView.getPadding().getLeft()).divide(10).multiply(9));
        mapView.minWidthProperty().bind(this.widthProperty().subtract(mapView.getPadding().getLeft()+mapView.getPadding().getRight()));
        mapView.minHeightProperty().bind(this.heightProperty().subtract(mapView.getPadding().getTop()+mapView.getPadding().getLeft()).divide(10).multiply(9));
        mapView.maxWidthProperty().bind(this.widthProperty().subtract(mapView.getPadding().getLeft()+mapView.getPadding().getRight()));
        mapView.maxHeightProperty().bind(this.heightProperty().subtract(mapView.getPadding().getTop()+mapView.getPadding().getLeft()).divide(10).multiply(9));

        statusView.prefWidthProperty().bind(this.widthProperty());
        statusView.prefHeightProperty().bind(this.heightProperty().divide(10));
        statusView.minWidthProperty().bind(this.widthProperty());
        statusView.minHeightProperty().bind(this.heightProperty().divide(10));
        statusView.maxWidthProperty().bind(this.widthProperty());
        statusView.maxHeightProperty().bind(this.heightProperty().divide(10));

        setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderStroke.THIN, Insets.EMPTY)));
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
