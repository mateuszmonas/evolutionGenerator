package elements;

import data.Vector2d;
import javafx.scene.image.Image;
import map.MapElementObserver;

public interface MapElement {
    Vector2d getPosition();

    void notifyRemove();

    void notifyPositionChange(Vector2d oldPosition);

    void attachObserver(MapElementObserver observer);

    void removeObserver(MapElementObserver observer);

    String toString();

    Icon getIcon();

    String toDetails();

    enum Icon {

        GROUND("ground.png"),
        ANIMAL_LOW_ENERGY("animal_low_energy.png"),
        ANIMAL_MEDIUM_ENERGY("animal_medium_energy.png"),
        ANIMAL_HIGH_ENERGY("animal_high_energy.png"),
        PLANT("grass.png");

        public Image image;

        Icon(String path) {
            image = new Image(path);
        }
    }

}
