package view.mapStatus.map;

import elements.MapElement;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Set;

public class MapField extends ImageView {

    MapElement elementToDisplay;
    Set<MapElement> elements;
    Tooltip tooltip;

    public MapField() {
        tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.millis(5));
        Tooltip.install(this, tooltip);
    }

    public void setTrackingEffect() {
        setEffect(new ColorAdjust(1, 0, 0, 0));
    }

    public void setDominatingGenomeEffect() {
        setEffect(new SepiaTone(1));
    }

    public void removeTrackingEffect() {
        setEffect(null);
    }

    public MapElement getDisplayedElement() {
        return elementToDisplay;
    }

    public Set<MapElement> getElements() {
        return elements;
    }

    public void update(MapElement elementToDisplay, Set<MapElement> elements) {
        this.setEffect(null);
        this.elements = elements;
        this.elementToDisplay = elementToDisplay;
        if (elementToDisplay != null) {
            setImage(elementToDisplay.getIcon().image);
            tooltip.setText(elementToDisplay.toString());
        } else {
            setImage(MapElement.Icon.GROUND.image);
            tooltip.setText("ground");
        }

    }
}
