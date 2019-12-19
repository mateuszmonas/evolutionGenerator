package view.mapStatus.map;

import elements.MapElement;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.util.Duration;

import java.util.Set;

public class MapField extends ImageView {

    MapElement elementToDisplay;
    Tooltip tooltip;
    MapFieldContextMenu elementsMenu = new MapFieldContextMenu();
    private static Effect trackingEffect = new ColorAdjust(1, 0, 0, 0);
    private static Effect genomeEffect = new SepiaTone(1);

    public MapField() {
        tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.millis(5));
        Tooltip.install(this, tooltip);
        setOnContextMenuRequested(contextMenuEvent -> elementsMenu.show(this, Side.RIGHT, 0, 0));
    }

    public void setTrackingEffect(boolean show) {
        if (show) setEffect(trackingEffect);
        else setEffect(null);
    }

    public void setDominatingGenomeEffect(boolean show) {
        if(show && getEffect()!=trackingEffect) setEffect(genomeEffect);
        else if(!show && getEffect()!=trackingEffect) setEffect(null);
    }

    public MapElement getDisplayedElement() {
        return elementToDisplay;
    }

    public void update(MapElement elementToDisplay, Set<MapElement> elements) {
        this.setEffect(null);

        elementsMenu.setElements(elements);

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
