package view.mapStatus.map;

import data.Rectangle;
import data.Vector2d;
import elements.MapElement;
import javafx.scene.layout.GridPane;
import map.MapStatus;
import view.mapStatus.map.field.MapField;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapViewPane extends GridPane {
    Map<Vector2d, MapField> positions;


    public MapViewPane(Rectangle area) {
        positions = area.getVectorSpace().stream().collect(Collectors.toMap(
                e -> e,
                e -> new MapField()
        ));
        positions.forEach((key, value) -> this.add(value, key.x + 1, area.getHeight() - key.y));
    }

    public void updateMap(Map<Vector2d, MapElement> elementsToDisplay, Map<Vector2d, Set<MapElement>> elements) {
        if (positions == null) {
            throw new IllegalStateException("MapStatusView has not been initialized");
        }
        for (Map.Entry<Vector2d, MapField> entry : positions.entrySet()) {
            Vector2d key = entry.getKey();
            MapField value = entry.getValue();
            value.update(elementsToDisplay.getOrDefault(key, null),
                    elements.getOrDefault(key, null));
        }
    }

}
