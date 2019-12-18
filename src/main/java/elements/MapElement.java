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
        ANIMAL("animal.png"),
        PLANT("grass.png");

        public Image image;

        Icon(String path) {
            image = new Image(path);
        }
    }

}
