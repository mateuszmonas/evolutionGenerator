package view.mapStatus.map.field;

import elements.MapElement;
import javafx.scene.image.ImageView;

import java.util.Set;

public class MapField extends ImageView implements Field {

    @Override
    public void update(MapElement elementToDisplay, Set<MapElement> elements) {
        if (elementToDisplay != null) setImage(elementToDisplay.getIcon().image);
        else setImage(MapElement.Icon.GROUND.image);
    }
}
