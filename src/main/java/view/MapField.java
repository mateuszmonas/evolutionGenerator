package view;

import elements.MapElement;
import elements.animal.Animal;
import elements.grass.Grass;
import javafx.scene.control.Button;
import javafx.scene.control.Control;

import java.util.Set;

public class MapField extends Button implements Field {

    @Override
    public void update(MapElement elementToDisplay, Set<MapElement> elements) {
        if(elementToDisplay instanceof Animal) setText("a");
        else if(elementToDisplay instanceof Grass) setText("g");
        else setText("n");
    }

}
