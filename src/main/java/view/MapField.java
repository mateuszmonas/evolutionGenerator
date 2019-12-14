package view;

import elements.MapElement;
import elements.animal.Animal;
import elements.grass.Grass;
import javafx.scene.control.Button;

import java.util.Set;

public class MapField extends Button implements Field {

    @Override
    public void update(MapElement elementToDisplay, Set<MapElement> elements) {
        if(elementToDisplay instanceof Animal) setStyle("-fx-background-color: #ff0000; ");
        else if(elementToDisplay instanceof Grass) setStyle("-fx-background-color: #00ff00; ");
        else setStyle("-fx-background-color: #000000; ");;
    }

}
