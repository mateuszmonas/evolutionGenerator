package view;

import data.Rectangle;
import data.Vector2d;
import javafx.scene.layout.GridPane;
import map.MapStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MapPane extends GridPane implements MapView {
    Map<Vector2d, MapField> positions = new HashMap<>();

    @Override
    public void updateMap(MapStatus status) {
        for (Map.Entry<Vector2d, MapField> entry : positions.entrySet()) {
            Vector2d key = entry.getKey();
            MapField value = entry.getValue();
            value.update(status.getElementsToDisplay().getOrDefault(key, null),
                    status.getElements().getOrDefault(key, null));
        }
    }

    @Override
    public void initialize(Rectangle area) {
        positions = area.getVectorSpace().stream().collect(Collectors.toMap(
                e -> e,
                e -> new MapField()
        ));
        positions.forEach((key, value) -> this.add(value, key.x+1, area.getHeight()-key.y));
    }

    public MapPane() {
        super();
    }
}
