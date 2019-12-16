package view.mapStatus.map.field;

import data.Config;
import elements.MapElement;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.ViewConfig;

import java.util.Set;

public class MapField extends ImageView implements Field {

    @Override
    public void update(MapElement elementToDisplay, Set<MapElement> elements) {
        if (elementToDisplay != null) setImage(elementToDisplay.getIcon().image);
        else setImage(MapElement.Icon.GROUND.image);
    }
}
