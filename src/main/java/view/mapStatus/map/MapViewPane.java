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

    MapField trackedField = null;
    Set<MapField> dominantGenomeFields = new HashSet<>();
    boolean showingDominantAnimals = false;

    public MapViewPane(double prefWidth, double prefHeight) {
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);
        setAlignment(Pos.CENTER);
    }

    public void setShowingDominantAnimals(boolean showingDominantAnimals) {
        this.showingDominantAnimals = showingDominantAnimals;
        applyDominantGenomeEffect();
    }

    private void applyDominantGenomeEffect() {
        dominantGenomeFields.forEach(mapField -> mapField.setDominatingGenomeEffect(showingDominantAnimals));
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

    public void initialize(TrackingEventListener listener, Rectangle area) {
        positions = new MapField[area.getWidth()][area.getHeight()];
        area.getVectorSpace().forEach(vector2d -> {
            positions[vector2d.x][vector2d.y] = new MapField(listener, Math.min(this.getPrefWidth() / area.getWidth(), this.getPrefHeight() / area.getHeight()));
            add(positions[vector2d.x][vector2d.y], vector2d.x + 1, vector2d.y + 1);
        });
    }

    public void updateMap(MapStatus.ElementsPositions elementsPositions) {
        dominantGenomeFields.clear();

        for (Vector2d position : elementsPositions.changedPositions) {
            MapElement elementToDisplay = elementsPositions.elementsToDisplay.getOrDefault(position, null);

            positions[position.x][position.y].update(elementToDisplay, elementsPositions.elements.getOrDefault(position, null));

            if (position.equals(elementsPositions.trackedElementPosition)) {
                trackedField = positions[position.x][position.y];
                positions[position.x][position.y].setTrackingEffect(true);
            }
            if (elementsPositions.dominatingGenomeElementsPositions.contains(position)) {
                dominantGenomeFields.add(positions[position.x][position.y]);
            }
        }
        applyDominantGenomeEffect();
    }

}
