package view.mapStatus.map;

import data.Rectangle;
import data.Vector2d;
import elements.MapElement;
import javafx.scene.layout.GridPane;
import view.mapStatus.map.field.MapField;

import java.util.Map;
import java.util.Set;

public class MapViewPane extends GridPane {
    MapField[][] positions;

    public void initialize(Rectangle area) {
        positions = new MapField[area.getWidth()][area.getHeight()];
        area.getVectorSpace().forEach(vector2d -> positions[vector2d.x][vector2d.y] = new MapField());
        for (int i = 0; i < area.getWidth(); i++) {
            for (int j = 0; j < area.getHeight(); j++) {
                this.add(positions[i][j], i + 1, j + 1);
                positions[i][j].fitWidthProperty().bind(this.widthProperty().subtract(this.getPadding().getLeft()+this.getPadding().getRight()).divide(area.getWidth()));
                positions[i][j].fitHeightProperty().bind(this.heightProperty().subtract(this.getPadding().getTop()+this.getPadding().getBottom()).divide(area.getHeight()));
            }
        }
    }

    public void updateMap(Map<Vector2d, MapElement> elementsToDisplay, Map<Vector2d, Set<MapElement>> elements) {
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                Vector2d position = new Vector2d(i, j);
                positions[i][j].update(elementsToDisplay.getOrDefault(position, null),
                        elements.getOrDefault(position, null));
            }
        }
    }
}
