package view.mapStatus.map;

import data.Rectangle;
import data.Vector2d;
import elements.MapElement;
import javafx.geometry.Pos;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapViewPane extends GridPane {
    MapField[][] positions;

    Set<TrackElementListener> listeners = new HashSet<>();
    MapField highlightedField = null;

    private void onFieldClick(MapField field) {
        for (TrackElementListener listener : listeners) {
            listener.setTrackedElement(field.getDisplayedElement());
        }
    }

    public void trackedElementChange(MapElement trackedElement) {
        if (highlightedField != null) {
            highlightedField.removeTrackingEffect();
        }
        if (trackedElement != null) {
            highlightedField = Arrays.stream(positions)
                    .flatMap(Arrays::stream)
                    .filter(mapField -> mapField.elements!=null && mapField.elements.stream().anyMatch(element -> element==trackedElement))
                    .findAny()
                    .orElse(null);
            if (highlightedField != null) {
                highlightedField.setTrackingEffect();
            }
        }
    }

    public void addOnFieldClickListener(TrackElementListener listener) {
        listeners.add(listener);
    }

    public void initialize(Rectangle area) {
        positions = new MapField[area.getWidth()][area.getHeight()];
        area.getVectorSpace().forEach(vector2d -> positions[vector2d.x][vector2d.y] = new MapField());
        for (int i = 0; i < area.getWidth(); i++) {
            for (int j = 0; j < area.getHeight(); j++) {
                this.add(positions[i][j], i + 1, j + 1);
                positions[i][j].setFitHeight(Math.min(this.getPrefWidth() / area.getWidth(), this.getPrefHeight() / area.getHeight()));
                positions[i][j].setFitWidth(Math.min(this.getPrefWidth() / area.getWidth(), this.getPrefHeight() / area.getHeight()));
                positions[i][j].setOnMouseClicked(mouseEvent -> onFieldClick((MapField) mouseEvent.getSource()));
            }
        }
    }

    public void updateMap(Map<Vector2d, MapElement> elementsToDisplay, Map<Vector2d, Set<MapElement>> elements, MapElement trackedElement, Set<Vector2d> dominatingGenomeElementsPositions) {
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                Vector2d position = new Vector2d(i, j);
                MapElement elementToDisplay = elementsToDisplay.getOrDefault(position, null);
                positions[i][j].update(elementToDisplay,
                        elements.getOrDefault(position, null));
                if (trackedElement != null && elements.containsKey(position) && elements.get(position).stream().anyMatch(element -> element == trackedElement)) {
                    positions[i][j].setTrackingEffect();
                }else if (dominatingGenomeElementsPositions.contains(position)) {
                    positions[i][j].setDominatingGenomeEffect();
                }
            }
        }
    }

    public MapViewPane(double prefWidth, double prefHeight) {
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);
        setAlignment(Pos.CENTER);
    }

}
