package view.mapStatus.map.field;

import elements.MapElement;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Set;

public class MapField extends ImageView implements Field {

    MapElement elementToDisplay;
    Tooltip tooltip;

    @Override
    public void update(MapElement elementToDisplay, Set<MapElement> elements) {
        this.elementToDisplay = elementToDisplay;
        if (elementToDisplay != null) {
            setImage(elementToDisplay.getIcon().image);
            tooltip.setText(elementToDisplay.toString());
        }
        else{
            setImage(MapElement.Icon.GROUND.image);
            tooltip.setText("ground");
        }

    }

    public MapField() {
        tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.millis(5));
        tooltip.setShowDelay(Duration.millis(5));
        Tooltip.install(this, tooltip);
    }
}
