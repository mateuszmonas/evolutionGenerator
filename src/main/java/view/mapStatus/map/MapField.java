package view.mapStatus.map;

import elements.MapElement;
import javafx.geometry.Side;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;

import java.util.Set;

public class MapField extends ImageView {

    private static Effect trackingEffect = new ColorAdjust(1, 0, 0, 0);
    private static Effect genomeEffect = new SepiaTone(1);
    MapElement elementToDisplay;
    MapElement.Icon currentIcon;
    Tooltip tooltip;
    MapFieldContextMenu elementsMenu;

    public MapField(TrackingEventListener listener, double prefSideLength) {
        setFitHeight(prefSideLength);
        setFitWidth(prefSideLength);
        setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) listener.trackedMapElementSelection(elementToDisplay);
        });
        tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.millis(5));
        Tooltip.install(this, tooltip);
        elementsMenu = new MapFieldContextMenu(listener);
        setOnContextMenuRequested(contextMenuEvent -> elementsMenu.show(this, Side.RIGHT, 0, 0));
        setImage(MapElement.Icon.GROUND.image);
        tooltip.setText("ground");
    }

    public void setTrackingEffect(boolean show) {
        if (show) setEffect(trackingEffect);
        else setEffect(null);
    }

    public void setDominatingGenomeEffect(boolean show) {
        if (show && getEffect() != trackingEffect) setEffect(genomeEffect);
        else if (!show && getEffect() != trackingEffect) setEffect(null);
    }

    public void update(MapElement elementToDisplay, Set<MapElement> elements) {
        this.setEffect(null);
        this.elementToDisplay = elementToDisplay;
        elementsMenu.setElements(elements);

        MapElement.Icon iconToDisplay = elementToDisplay != null ? elementToDisplay.getIcon() : MapElement.Icon.GROUND;
        String tooltipString = elementToDisplay != null ? elementToDisplay.toString() : "ground";

        if (iconToDisplay != currentIcon) {
            setImage(iconToDisplay.image);
        }
        tooltip.setText(tooltipString);

    }
}
