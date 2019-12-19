package view.mapStatus.map;

import data.Rectangle;
import data.Vector2d;
import elements.MapElement;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import map.MapStatus;

import java.util.HashSet;
import java.util.Set;

public class MapViewPane extends GridPane {
    MapField[][] positions;

    Set<MapElementClickListener> listeners = new HashSet<>();
    MapField trackedField = null;
    Set<MapField> dominantGenomeFields = new HashSet<>();
    boolean showingDominantAnimals = false;

    public void setShowingDominantAnimals(boolean showingDominantAnimals) {
        this.showingDominantAnimals = showingDominantAnimals;
        applyDominantGenomeEffect();
    }

    private void applyDominantGenomeEffect() {
        dominantGenomeFields.forEach(mapField -> mapField.setDominatingGenomeEffect(showingDominantAnimals));
    }

    private void onFieldClick(MapField field) {
        for (MapElementClickListener listener : listeners) {
            listener.mapElementClicked(field.getDisplayedElement());
        }
    }

    public void trackedElementChange(Vector2d trackedElementPosition) {
        if (trackedField != null) {
            trackedField.setTrackingEffect(false);
        }
        if (trackedElementPosition != null) {
            trackedField = positions[trackedElementPosition.x][trackedElementPosition.y];
            trackedField.setTrackingEffect(true);
        }
    }

    public void addOnFieldClickListener(MapElementClickListener listener) {
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

    public void updateMap(MapStatus.ElementsPositions elementsPositions) {
        dominantGenomeFields.clear();
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                Vector2d position = new Vector2d(i, j);
                MapElement elementToDisplay = elementsPositions.elementsToDisplay.getOrDefault(position, null);

                positions[i][j].update(elementToDisplay,
                        elementsPositions.elements.getOrDefault(position, null));

                if (position.equals(elementsPositions.trackedElementPosition)) {
                    trackedField = positions[i][j];
                    positions[i][j].setTrackingEffect(true);
                }
                if (elementsPositions.dominatingGenomeElementsPositions.contains(position)) {
                    dominantGenomeFields.add(positions[i][j]);
                }
            }
        }
        applyDominantGenomeEffect();
    }

    public MapViewPane(double prefWidth, double prefHeight) {
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);
        setAlignment(Pos.CENTER);
    }

}
